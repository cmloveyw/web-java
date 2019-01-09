package test.SpringTest.Cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @version 1.0
 * @类名: ProxyFactory.java
 * @描述: Cglib子类代理工厂（对UserDao在内存中创建一个代理）
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class ProxyFactory implements MethodInterceptor {
    //目标对象
    private Object targert;

    public ProxyFactory(Object targert) {
        this.targert = targert;
    }

    //代理对象
    public Object getProxyInstance() {
        //1。工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(targert.getClass());
        //3.设置回调
        en.setCallback(this);
        //4.创建代理对象
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始事务。。。");
        //执行目标对象的方法
        Object resultValue = method.invoke(targert, objects);
        System.out.println("结束事务。。。");
        return resultValue;
    }
}
