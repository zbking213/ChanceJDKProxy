package org.zbking;

public class App {

    public static void main(String[] args) throws Exception {
        //TODO 1，没有参数添加。2，方法返回值类型不支持void，3，多个参数截取问题；
        AService o = (AService) Proxy.newInstance(AService.class, new LogInvocationHandler(new AServiceImpl()));
            o.aMethod();


    }
}
