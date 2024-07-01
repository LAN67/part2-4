package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class Loading {
    @Autowired
    ReadFiles readFiles;
    @Autowired
    Convert convert;
    @Autowired
    SaveData saveData;

    void loading() {
        readFiles.setPathIn("src\\main\\resources\\in");
        saveData.setNameFileLog("src\\main\\resources\\log\\log_file.txt");
        saveData.setConnectString("jdbc:postgresql://10.0.0.12:5432/innotech");
        saveData.setUSERNAME("postgres");
        saveData.setPASSWORD("password");

        Function<Model<OneIn>, Model<OneOut>> conv = Utils.log(convert);
        saveData.accept(conv.apply(readFiles.get()));
    }
}
