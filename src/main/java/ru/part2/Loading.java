package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class Loading {
    @Autowired
    Supplier<Model> readFiles;
    @Autowired
    Function<Model, Model> convert;
    @Autowired
    Consumer<Model> saveData;

    void loading() {
        //saveData.accept(Utils.log(convert.apply(readFiles.get())));
        saveData.accept(convert.apply(readFiles.get()));
    }
}
