package test.FutureTest;

import java.util.concurrent.Callable;

/**
 * @version 1.0
 * @类名: RealData.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-12
 */
public class RealData implements Callable<String> {
    private String result;

    public RealData(String result) {
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(result);
        Thread.sleep(5000);
        return stringBuffer.toString();
    }
}
