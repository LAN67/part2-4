package ru.part2;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class SaveData implements Consumer<Model> {

    @Override
    public void accept(Model model) {

    }
}
