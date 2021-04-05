package com.company;

import com.my.Constants;
import com.my.TestThread;
import javafx.concurrent.Task;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here
        Constants.SetString("Started");
        TestThread task = new TestThread();
        Thread tdd = new Thread(task);
        tdd.start();
        Thread.currentThread().sleep(1000l);
        task.cancel();
        Thread.currentThread().sleep(1000l);
//        Constants.SetString("Restarted");
//        new Thread(task).start();

    }
}
