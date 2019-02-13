package test.SpringTest.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @version 1.0
 * @类名: ProxyFactory.java
 * @描述: jdk动态代理
 * @创建人: CM
 * @创建时间: 2019-2-13
 */
public class ProxyFactory implements InvocationHandler {
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行----LogUtil.log(传入参数)" + args.toString());
        Object result = method.invoke(target, args);
        System.out.println("得到的结果为：" + result);
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

}
