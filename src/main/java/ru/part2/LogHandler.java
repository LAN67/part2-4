package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class LogHandler<T> implements InvocationHandler {
    private T currentObject;
    //@Autowired
    //private Log log;
    private Log log = new Log();

    public LogHandler(T currentObject) {
        this.currentObject = currentObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method currentMethod;

        currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (currentMethod.isAnnotationPresent(LogTransformation.class)) {
            log.setNameFileLog(currentMethod.getAnnotation(LogTransformation.class).value());
            log.accept(new Date().toString());
            log.accept(currentObject.getClass().toString());
            log.accept(((Model<OneIn>)args[0]).toString());
            Model<OneOut> modelOut;
            modelOut = (Model)method.invoke(currentObject, args);
            log.accept(modelOut.toString());
            return modelOut;
        } else {
            return method.invoke(currentObject, args);
        }
    }
}
