package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class Loading {
    @Autowired
    ReadFiles readFiles;
    @Autowired
    Convert convert;
    @Autowired
    ConvertProg convertProg;
    @Autowired
    SaveData saveData;
    @Autowired
    Model model;

    void loading() {
        readFiles.setPathIn("src\\main\\resources\\in");
        convert.setNameFileLog("d:\\logs\\log_file.txt");
        saveData.setNameFileLog("d:\\logs\\log_file.txt");
        saveData.setConnectString("jdbc:postgresql://10.0.0.10:5432/innotech");
        saveData.setUSERNAME("postgres");
        saveData.setPASSWORD("password");

        model = readFiles.get();
        model = ((Function<Model,Model>)(Utils.log(convert))).apply(model);
        model = ((Function<Model,Model>)(Utils.log(convertProg))).apply(model);
        saveData.accept(model);
    }
}
