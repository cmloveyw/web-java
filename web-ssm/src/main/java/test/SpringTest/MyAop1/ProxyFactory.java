package test.SpringTest.MyAop1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @version 1.0
 * @类名: ProxyFactory.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class ProxyFactory {
    //目标对象
    private static Object target;
    private static MyAop aop;

    //代理对象
    public static Object getProxyFactory(Object _target, MyAop _aop) {
        target = _target;
        aop = _aop;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        aop.beginTrans();
                        Object reaultValue = method.invoke(target, args);
                        aop.commitTrans();
                        return reaultValue;
                    }
                });
    }
}
