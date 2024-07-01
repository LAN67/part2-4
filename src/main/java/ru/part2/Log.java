package ru.part2;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

@Component
public class Log implements Consumer<String> {
    private String nameFileLog;

    public void setNameFileLog(String nameFileLog) {
        this.nameFileLog = nameFileLog;
    }

    @Override
    public void accept(String str) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFileLog, true))) {
            writer.append(str + '\n');
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
