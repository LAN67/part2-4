package ru.part2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestLoading {

    @Test
    public void TestReadFiles() {
        Model model;
        ReadFiles readFiles = new ReadFiles();
        AtomicInteger count = new AtomicInteger();

        readFiles.setPathIn("src\\main\\resources\\in");
        model = readFiles.get();
        count.set(0);
        model.data.forEach(x -> count.getAndIncrement());

        Assertions.assertEquals(7, count.get());
    }

    @Test
    public void TestConvert() {
        Model model;
        ReadFiles readFiles = new ReadFiles();
        Convert convert = new Convert();
        AtomicInteger count = new AtomicInteger();

        readFiles.setPathIn("src\\main\\resources\\in");
        model = convert.apply(readFiles.get());

        count.set(0);
        model.data.forEach(x -> count.getAndIncrement());

        Assertions.assertEquals(7, count.get());
    }

    @Test
    public void TestSaveData() {
        String connectString = "jdbc:postgresql://10.0.0.10:5432/innotech";
        String USERNAME = "postgres";
        String PASSWORD = "password";
        ReadFiles readFiles = new ReadFiles();
        Convert convert = new Convert();
        SaveData saveData = new SaveData();
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int countLogins = 0;
        int countUsers2 = 0;
        int countLogins2 = 0;

        readFiles.setPathIn("src\\main\\resources\\in");
        saveData.setNameFileLog("d:\\logs\\log_file.txt");
        saveData.setConnectString(connectString);
        saveData.setUSERNAME(USERNAME);
        saveData.setPASSWORD(PASSWORD);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new AssertionFailedError();

        }
        try {
            connection = DriverManager.getConnection(connectString, USERNAME, PASSWORD);
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

        saveData.accept(convert.apply(readFiles.get()));

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
