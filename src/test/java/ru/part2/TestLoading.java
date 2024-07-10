package ru.part2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.sql.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@PropertySource("classpath:application.properties")
public class TestLoading {

    @Test
    public void TestReadFiles() {
        Model model;
        AtomicInteger count = new AtomicInteger();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.part2");
        model = context.getBean("readFiles", ReadFiles.class).get();
        count.set(0);
        model.data.forEach(x -> count.getAndIncrement());

        Assertions.assertEquals(7, count.get());
    }

    @Test
    public void TestConvert() {
        Model model;
        AtomicInteger count = new AtomicInteger();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.part2");
        model = context.getBean("readFiles", ReadFiles.class).get();
        model = context.getBean("convert", Convert.class).apply(model);

        count.set(0);
        model.data.forEach(x -> count.getAndIncrement());

        Assertions.assertEquals(7, count.get());
    }

    @Test
    public void TestConvertProg() {
        Model model;
        AtomicInteger count = new AtomicInteger();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.part2");
        model = context.getBean("readFiles", ReadFiles.class).get();
        model = context.getBean("convert", Convert.class).apply(model);
        model = context.getBean("convertProg", ConvertProg.class).apply(model);

        count.set(0);
        model.data.forEach(x -> count.getAndIncrement());

        Assertions.assertEquals(7, count.get());

        for (OneLine one : model.data) {
            if (Objects.equals(one.inProg, "web") || Objects.equals(one.inProg, "mobile")) {
                Assertions.assertEquals(one.inProg, one.outProg);
            }else{
                Assertions.assertEquals("other:" + one.inProg, one.outProg);
            }
        }
    }

    @Test
    void TestSaveDate() {
        Model model;
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int countLogins = 0;
        int countUsers2 = 0;
        int countLogins2 = 0;
        Environment environment;

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.part2");
        environment = context.getEnvironment();

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new AssertionFailedError();

        }
        try {
            connection = DriverManager.getConnection(
                    Objects.requireNonNull(environment.getProperty("connectString")),
                    environment.getProperty("userName2"),
                    environment.getProperty("userPassword2"));
        } catch (SQLException e) {
            throw new AssertionFailedError();
        }
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) count FROM logins");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countLogins = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new AssertionFailedError();
        }

        model = context.getBean("readFiles", ReadFiles.class).get();
        model = context.getBean("convert", Convert.class).apply(model);
        model = context.getBean("convertProg", ConvertProg.class).apply(model);
        context.getBean("saveData", SaveData.class).accept(model);

        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) count FROM users");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countUsers2 = resultSet.getInt("count");
            }
            preparedStatement = connection.prepareStatement("SELECT count(*) count FROM logins");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                countLogins2 = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new AssertionFailedError();
        }

        Assertions.assertEquals(countUsers2, 4);
        Assertions.assertEquals(countLogins2 - countLogins, 6);
    }
}
