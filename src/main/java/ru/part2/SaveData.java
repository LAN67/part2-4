package ru.part2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.sql.*;

@Component
public class SaveData implements Consumer<Model<OneOut>> {
    private String nameFileLog;
    private String connectString;
    private String USERNAME;
    private String PASSWORD;
    private Log log = new Log();

    private Connection connection;

    /*
      create table users (
      id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
      username varchar,
      fio varchar
      );
      create table logins (
      id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
      access_date date,
      user_id int,
      application varchar
      );
     */
    @Override
    public void accept(Model<OneOut> model) {
        String sql;
        int id;
        PreparedStatement preparedStatement;
        ResultSet rs;

        log.setNameFileLog(nameFileLog);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(connectString, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (OneOut one : model.data) {
            if (one.date == null) {
                log.accept("Не задана дата входа в систему: " + one.login + " файл: " + one.fileName);
            } else {
                id = -1;
                try {
                    // users
                    sql = "SELECT id FROM users WHERE username=?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, one.login);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        id = resultSet.getInt("id");
                    }
                    if (id == -1) {
                        try {
                            sql = "insert into users (username, fio) values(?,?) RETURNING id";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, one.login);
                            preparedStatement.setString(2, one.fio);
                            preparedStatement.execute();
                            rs = preparedStatement.getResultSet();
                            if (rs.next()) {
                                id = rs.getInt(1);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // logins
                try {
                    sql = "insert into logins (access_date, user_id, application) values(?,?,?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setDate(1, new java.sql.Date(one.date.getTime()));
                    preparedStatement.setInt(2, id);
                    preparedStatement.setString(3, one.prog);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setNameFileLog(String nameFileLog) {
        this.nameFileLog = nameFileLog;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
