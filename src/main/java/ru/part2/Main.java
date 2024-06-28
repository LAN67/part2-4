package ru.part2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        new AnnotationConfigApplicationContext("ru.part2")
                .getBean("loading", Loading.class)
                .loading();
    }
}
