package ru.part2;

import java.util.*;

class Model<T> {
    List<T> data = new ArrayList<>();
}

class OneIn {
    String login, f, i, o, date, prog, fileName;
}

class OneOut {
    String login, fio, prog, fileName;
    Date date;
}