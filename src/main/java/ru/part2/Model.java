package ru.part2;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

class Model<T> {
    List<T> data = new ArrayList<>();

    @Override
    public String toString() {
        String str = "";

        for(T one : data){
            str += one.toString() + '\n';
        }
        return str;
    }
}

class OneIn {
    String login, f, i, o, date, prog, fileName;

    @Override
    public String toString() {
        return "In {" +
                "login='" + login + '\'' +
                ", f='" + f + '\'' +
                ", i='" + i + '\'' +
                ", o='" + o + '\'' +
                ", date='" + date + '\'' +
                ", prog='" + prog + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}

class OneOut {
    String login, fio, prog, fileName;
    Date date;

    @Override
    public String toString() {
        return "Out {" +
                "login='" + login + '\'' +
                ", fio='" + fio + '\'' +
                ", prog='" + prog + '\'' +
                ", date=" + date +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}