package ru.part2;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.function.Supplier;

@Component
public class ReadFiles implements Supplier<Model> {
    String pathIn = "src\\main\\resources\\in";

    @Override
    public Model get() {
        File file = new File(pathIn);

        File[] files = file.listFiles();
        if(files != null) {
            Model model = new Model();
            for (File file1 : files) {
                System.out.println(file1);
            }
            return model;
        } else {
            return null;
        }
    }

    public void setPathIn(String pathIn) {
        this.pathIn = pathIn;
    }
}
