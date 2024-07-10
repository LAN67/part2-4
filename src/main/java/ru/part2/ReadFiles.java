package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@PropertySource("classpath:application.properties")
public class ReadFiles implements Supplier {
    private Model model;
    @Autowired
    Environment environment;

    @Override
    public Model get() {
        File file = new File(Objects.requireNonNull(environment.getProperty("pathIn")));

        File[] files = file.listFiles();
        if (files != null) {
            model = new Model();
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
        OneLine one;

        try (FileReader fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            while ((str = reader.readLine()) != null) {
                array = str.split(";");
                one = new OneLine();
                one.inLogin = array[0];
                one.inF = array[1];
                one.inI = array[2];
                one.inO = array[3];
                one.inDate = array[4];
                one.inProg = array[5];
                one.fileName = file.getCanonicalPath();
                model.data.add(one);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
