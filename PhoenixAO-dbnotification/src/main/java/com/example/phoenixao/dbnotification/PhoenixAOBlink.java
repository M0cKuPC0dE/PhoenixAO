/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.dbnotification;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhoenixAOBlink {

    private List<String> threadNames = new ArrayList<String>();

    public static void main(String[] args) {
        PhoenixAOBlink test = new PhoenixAOBlink();
        test.threadTest(10);
        System.out.println(test.threadNames);
    }

    private void threadTest(int numOfThreads) {
        Thread[] threads = new Thread[numOfThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new PhoenixAOBlink.MyThread();
            threads[i].start();
            if (i == 9) {
                try {
                    threads[9].join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PhoenixAOBlink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            threadNames.add(getName());
            for (int i = 0; i < 1000000; i++) {
                i = i + 0;
            }
            threadNames.add(getName() + " Comp");
        }
    }
}
