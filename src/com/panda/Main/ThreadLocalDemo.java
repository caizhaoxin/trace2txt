package com.panda.Main;

import com.panda.trace.ThreadList;

class Name{
    public ThreadLocal<String> names = new ThreadLocal<>();
}
class User extends Thread{
    private final Name name;
    private String str;
    User(Name name, String str){
        this.name = name;
        this.str = str;
    }
    @Override
    public void run() {
        this.name.names.set(str);
        for(;;){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.name.names.get());
        }
    }
}
public class ThreadLocalDemo {
    public static void main(String[] args) {
        Name name = new Name();
        User u1 = new User(name, "babadawo");
        User u2 = new User(name, "mamadawo");

        u1.start();
        u2.start();
    }
}
