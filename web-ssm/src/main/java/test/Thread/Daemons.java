package test.Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @version 1.0
 * @类名: Daemons.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-26
 */
public class Daemons {
    public static void main(String[] args) {
        Thread d = new Daemon();
        System.out.println("d.isDaemon() = " + d.isDaemon());
// Allow the daemon threads to finish
// their startup processes:
        BufferedReader stdin =
                new BufferedReader(
                        new InputStreamReader(System.in));
        System.out.println("Waiting for CR");
        try {
            stdin.readLine();
        } catch (IOException e) {
        }
    }
}
