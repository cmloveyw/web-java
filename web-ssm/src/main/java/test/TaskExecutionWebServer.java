package test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @类名: TaskExecutionWebServer.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-4-26
 */
public class TaskExecutionWebServer {
    private static final ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        /*ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket socket = serverSocket.accpet();
            executor -> () {

            }
            ;
            executor.execute(task);
        }*/
    }
}
