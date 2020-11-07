package org.zbking;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LogInvocationHandler implements ZbkingInvocationHandler{
    Object target; //目标对象
    public LogInvocationHandler(Object target){
        this.target = target;
    }

    @Override
    public Object zbkingInvoke(Method method) {

        //doing println "log"
        System.out.println("log");
        try {
            return  method.invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
