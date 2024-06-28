package ru.part2;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

@Component
public class SaveData implements Consumer<Model<OneOut>> {
    String nameFileLog = "src\\main\\resources\\log\\log_file.txt";

    @Override
    public void accept(Model<OneOut> model) {

        for (OneOut one : model.data) {
            System.out.println(one.login + "|" + one.fio + "|" + one.date + "|" + one.prog);
            addLog(one.login + "|" + one.fio + "|" + one.date + "|" + one.prog);

        }
    }

    public void setNameFileLog(String nameFileLog) {
        this.nameFileLog = nameFileLog;
    }

    void addLog(String str) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(nameFileLog, true))) {
            writer.append(str + '\n');
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
