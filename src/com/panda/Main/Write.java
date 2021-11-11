package com.panda.Main;

import com.panda.trace.*;

import java.io.*;
import java.util.*;

public class Write {

    public static void write(String sourceFile, String targetFile, boolean filter) throws IOException {
        clearInfoForFile(targetFile);
        byte[] bytes = BytesHelper.toByteArray(sourceFile);
        Trace trace = new Trace(bytes);
        ThreadList threadList = trace.getThreadList();
        System.out.println("生成中，喝杯茶耐心等待下........");
        for (Map.Entry<String, TraceThread> entry : threadList.getThreads().entrySet()) {
            TraceThread traceThread = entry.getValue();
            // 写线程名字
            String underScore = String.format("-----------------%s-----------------", traceThread.getName());
            writeStr(underScore, targetFile, true, filter);
            // 写方法栈
            List<MethodLog> methods = entry.getValue().getMethods();
            List<String> res = new ArrayList<>();
            dfs(res, methods);
            writeList(res, targetFile, true, filter);
            // 结束画线
            writeStr("------------------------------------", targetFile, true, filter);
        }
    }

    private static void clearInfoForFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) return;
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeStr(String str, String fileName, boolean add, boolean filter) {
        List<String> list = new ArrayList<>(Arrays.asList(str));
        writeList(list, fileName, true, filter);
    }

    private static void writeList(List<String> res, String fileName, boolean add, boolean filter) {
        try {
            // com.networkbench.agent.impl.crash.b.b.a (Lcom/networkbench/agent/impl/crash/b/a;) V
            FileOutputStream fos = new FileOutputStream(fileName, add);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            Set<String> firstSystemWords = new HashSet<>(new HashSet<>(Arrays.asList("android", "java", "sun", "dalvik", "libcore")));
            Set<String> sensitiveWords = new HashSet<>(new HashSet<>(Arrays.asList("http", "database")));
            String preMethod = "";
            for (int i = 0; i < res.size(); i++) {
                String firstWordOfpackageName = res.get(i).split(" ")[0].split("\\.")[0].toLowerCase();
                String packageName = res.get(i).split(" ")[0].toLowerCase();
                boolean existsystemWord = false;
                boolean existSensitiveWord = false;
                for (String systemWord : firstSystemWords)
                    if (firstWordOfpackageName.contains(systemWord)) {
                        existsystemWord = true;
                        break;
                    }
                if (existsystemWord) {
                    for (String sensitiveWord : sensitiveWords)
                        if (packageName.contains(sensitiveWord)) {
                            existSensitiveWord = true;
                            break;
                        }
                }
                if (existsystemWord && !existSensitiveWord && filter) continue;
                if (i != 0 && res.get(i - 1).equals(res.get(i)) || res.get(i).equals(preMethod)) continue;
                preMethod = res.get(i);
                String str = res.get(i) + "\n";
                bos.write(str.getBytes(), 0, str.getBytes().length);
                bos.flush();
            }
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dfs(List<String> res, List<MethodLog> methods) {
        if (methods == null) return;
        for (MethodLog methodLog : methods) {
            res.add(methodLog.getMyFullName());
            dfs(res, methodLog.getChild());
        }
    }
}
