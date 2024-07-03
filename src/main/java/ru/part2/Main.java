package ru.part2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        System.out.println("start");
        new AnnotationConfigApplicationContext("ru.part2")
                .getBean("loading", Loading.class)
                .loading();
        System.out.println("end");
    }
}
