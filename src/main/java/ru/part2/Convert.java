package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

@Component
@PropertySource("classpath:application.properties")
public class Convert implements Function<Model, Model> {
    Log log = new Log();
    @Autowired
    Environment environment;

    @LogTransformation("d:\\logs\\log2_file.txt")
    @Override
    public Model apply(Model model) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        log.setNameFileLog(environment.getProperty("logFileName"));

        for (OneLine one : model.data) {
            one.outLogin = one.inLogin;
            one.outFIO = normalName(one.inF) + " " + normalName(one.inI) + " " + normalName(one.inO);
            if (!one.inDate.isEmpty()) {
                try {
                    one.outDate = df.parse(one.inDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }else{
                one.outDate = null;
                log.accept("Не задана дата входа в систему: " + one.inLogin + " файл: " + one.fileName);
            }
        }
        return model;
    }

    private String normalName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}


