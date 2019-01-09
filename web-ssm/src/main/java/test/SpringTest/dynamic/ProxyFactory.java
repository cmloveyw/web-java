package test.SpringTest.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @version 1.0
 * @类名: ProxyFactory.java
 * @描述: 代理工厂
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class ProxyFactory {
    //目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始事务");
                        Object result = method.invoke(target, args);
                        System.out.println("结束事务");
                        return result;
                    }
                }
        );
    }
}
