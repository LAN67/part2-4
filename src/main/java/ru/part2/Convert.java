package ru.part2;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

@Component
public class Convert implements Function<Model<OneIn>, Model<OneOut>> {

    @LogTransformation("src\\main\\resources\\log\\log2_file.txt")
    @Override
    public Model<OneOut> apply(Model<OneIn> model) {
        Model<OneOut> modelOut = new Model<>();
        OneOut oneOut;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        for (OneIn oneIn : model.data) {
            oneOut = new OneOut();
            oneOut.login = oneIn.login;
            oneOut.fio = normalName(oneIn.f) + " " + normalName(oneIn.i) + " " + normalName(oneIn.o);
            if (!oneIn.date.isEmpty()) {
                try {
                    oneOut.date = df.parse(oneIn.date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }else{
                oneOut.date = null;
            }
            if (oneIn.prog.equals("web") || oneIn.prog.equals("mobile")) {
                oneOut.prog = oneIn.prog;
            } else {
                oneOut.prog = "other:" + oneIn.prog;
            }
            oneOut.fileName = oneIn.fileName;
            modelOut.data.add(oneOut);
        }
        return modelOut;
    }

    private String normalName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
