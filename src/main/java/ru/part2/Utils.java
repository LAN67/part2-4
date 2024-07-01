package ru.part2;

import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

public class Utils {
    public static <T> T log(T objectIncome) {
        return (T) Proxy.newProxyInstance(
                objectIncome.getClass().getClassLoader(),
                objectIncome.getClass().getInterfaces(),
                new LogHandler(objectIncome));
    }
}
