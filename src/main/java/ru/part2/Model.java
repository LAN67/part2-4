package ru.part2;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

@Component
class Model {
    List<OneLine> data = new ArrayList<>();

    @Override
    public String toString() {
        String str = "";

        for(OneLine one : data){
            str += one.toString() + '\n';
        }
        return str;
    }
}

class OneLine {
    String inLogin, inF, inI, inO, inDate, inProg;
    String outLogin, outFIO, outProg;
    Date outDate;
    String fileName;

    @Override
    public String toString() {
        return "OneLine{" +
                "inLogin='" + inLogin + '\'' +
                ", inF='" + inF + '\'' +
                ", inI='" + inI + '\'' +
                ", inO='" + inO + '\'' +
                ", inDate='" + inDate + '\'' +
                ", inProg='" + inProg + '\'' +
                ", outLogin='" + outLogin + '\'' +
                ", outFIO='" + outFIO + '\'' +
                ", outProg='" + outProg + '\'' +
                ", outDate=" + outDate +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
