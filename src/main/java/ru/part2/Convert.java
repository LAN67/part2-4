package ru.part2;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;

@Component
public class Convert implements Function<Model<OneIn>, Model<OneOut>> {

    @Override
    public Model<OneOut> apply(Model<OneIn> model) {
        Model<OneOut> modelOut = new Model<>();
        OneOut oneOut;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        for (OneIn oneIn : model.data) {
            oneOut = new OneOut();
            oneOut.login = oneIn.login;
            oneOut.fio = normalFIO(oneIn.f) + " " + normalFIO(oneIn.i) + " " + normalFIO(oneIn.o);
            try {
                oneOut.date = df.parse(oneIn.date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(oneIn.prog.equals("web") || oneIn.prog.equals("mobile")) {
                oneOut.prog = oneIn.prog;
            }else{
                oneOut.prog = "other:" + oneIn.prog;
            }
            modelOut.data.add(oneOut);
        }
        return modelOut;
    }

    private String normalFIO(String name){

        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
