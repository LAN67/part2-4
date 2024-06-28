package ru.part2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
//            Loading lg = new Loading();
//            lg.readFiles = new ReadFiles();
//            lg.convert = new Convert();
//            lg.saveData = new SaveData();
//
//            lg.loading();

        new AnnotationConfigApplicationContext("ru.part2")
                .getBean("loading", Loading.class)
                .loading();

    }
}
