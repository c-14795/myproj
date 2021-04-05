package com.my;


import com.sun.jmx.snmp.tasks.Task;

public class TestThread implements Task{



    @Override
    public void cancel() {
        Constants.SetString("Cancelled");
        System.out.println(Constants.myString);
    }

    @Override
    public void run() {
        for (; ;) {
            System.out.println(Constants.myString);
        }
    }
}
