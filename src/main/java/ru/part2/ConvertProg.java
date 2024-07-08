package ru.part2;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ConvertProg implements Function<Model, Model> {
    @LogTransformation("d:\\logs\\log2_file.txt")
    @Override
    public Model apply(Model model) {

        for (OneLine one : model.data) {
            if (one.inProg.equals("web") || one.inProg.equals("mobile")) {
                one.outProg = one.inProg;
            } else {
                one.outProg = "other:" + one.inProg;
            }
        }
        return model;
    }
}
