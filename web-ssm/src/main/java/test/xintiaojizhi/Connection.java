package test.xintiaojizhi;

/**
 * @version 1.0
 * @类名: Connection.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-5
 */
public class Connection {
    private Long lastTime;

    public void refresh() {
        lastTime = System.currentTimeMillis();
    }

    public long getLastTime() {
        return lastTime;
    }
}
