package ru.part2;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

@Component
public class ReadFiles implements Supplier<Model> {
    private String pathIn;
    private Model<OneIn> model;

    @Override
    public Model<OneIn> get() {
        File file = new File(pathIn);

        File[] files = file.listFiles();
        if (files != null) {
            model = new Model<>();
            for (File file1 : files) {
                readFile(file1);
            }
            return model;
        } else {
            return null;
        }
    }

    void readFile(File file) {
        String str;
        String[] array;
        OneIn one;

        try (FileReader fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            while ((str = reader.readLine()) != null) {
                array = str.split(";");
                one = new OneIn();
                one.login = array[0];
                one.f = array[1];
                one.i = array[2];
                one.o = array[3];
                one.date = array[4];
                one.prog = array[5];
                one.fileName = file.getCanonicalPath();
                model.data.add(one);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setPathIn(String pathIn) {
        this.pathIn = pathIn;
    }
}
