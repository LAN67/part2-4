package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Component
public class Loading {
    @Autowired
    ReadFiles readFiles;
    @Autowired
    SaveData saveData;
    @Autowired
    Model model;

    @Autowired
    List<Function<Model, Model>> convert = new ArrayList<>();

    void loading() {

        model = readFiles.get();
        for (Function<Model, Model> conv : convert) {
            model = ((Function<Model, Model>) (Utils.log(conv))).apply(model);
        }
        saveData.accept(model);
    }
}
