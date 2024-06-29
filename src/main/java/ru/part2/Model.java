package ru.part2;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

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