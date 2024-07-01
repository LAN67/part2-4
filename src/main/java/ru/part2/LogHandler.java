package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler<T> implements InvocationHandler {
    private T currentObject;
    @Autowired
    Log log;

    public LogHandler(T currentObject) {
        this.currentObject = currentObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method currentMethod;
        String logName;

        currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (currentMethod.isAnnotationPresent(LogTransformation.class)) {
            logName = currentMethod.getAnnotation(LogTransformation.class).value();
            log.setNameFileLog(logName);
            //log.accept(((Model<OneIn>)args[0]).toString());
            //Model<OneOut> modelOut;
            //modelOut = (Model)method.invoke(currentObject, args);
            //log.accept(modelOut.toString());
            //return modelOut;
            return method.invoke(currentObject, args);
        } else {
            return method.invoke(currentObject, args);
        }
    }
}
