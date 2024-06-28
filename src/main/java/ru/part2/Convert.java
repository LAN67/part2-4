package ru.part2;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Convert implements Function<Model, Model> {

    @Override
    public Model apply(Model model) {




        return model;
    }
}
