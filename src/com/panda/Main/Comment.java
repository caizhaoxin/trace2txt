package com.panda.Main;

import org.apache.commons.cli.*;

import java.io.IOException;

public class Comment {
    public static String sourceFile = "./trace/aiqiyi-20211107-1438.trace";
    public static String targetFile = "./aiqiyi_not_filt.txt";

    public static void main(String[] args) throws ParseException {
        try {
            Options JDUL = new Options();

            JDUL.addOption("h", "help", false, "查找帮助");
            JDUL.addOption("s", "sourceFile", true, "目标trace文件");
            JDUL.addOption("t", "targetFile", true, "生成trace文件，注意如果源文件会覆盖掉");
            JDUL.addOption("f", "filter", false, "加上-f就会过滤掉系统包！");

            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(JDUL, args);

            if (cl.hasOption("h")) {
                HelpFormatter f = new HelpFormatter();
                f.printHelp("OptionsTip", JDUL);
                return;
            }
            if (!cl.hasOption("s") || !cl.hasOption("t")) {
                System.out.println("请补全参数！详情查看帮助！");
                return;
            } else {
                String sourceFile = cl.getOptionValue("s");
                String targetFile = cl.getOptionValue("t");
                Write.write(sourceFile, targetFile, cl.hasOption("f"));
            }
        } catch (ParseException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
