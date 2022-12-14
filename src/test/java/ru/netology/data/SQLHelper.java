package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");
    private static Connection conn;

    public static void clearDB() {
        var cleanCreditRequest = "DELETE FROM credit_request_entity;";
        var cleanOrder = "DELETE FROM order_entity;";
        var cleanPayment = "DELETE FROM payment_entity;";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, cleanCreditRequest);
            runner.update(conn, cleanOrder);
            runner.update(conn, cleanPayment);
        } catch (Exception e) {
            System.out.println("SQL exception in clearDB");
        }
    }

    public static String getPaymentStatus() {
        var codesSQL = "SELECT status FROM payment_entity;";
        return getData(codesSQL);
    }

    public static String getCreditRequestStatus() {
        var codesSQL = "SELECT status FROM credit_request_entity;";
        return getData(codesSQL);
    }

    public static String getOrderCount() {
        Long count = null;
        var codesSQL = " SELECT COUNT(*) FROM order_entity;";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(url, user, password)) {
            count = runner.query(conn, codesSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Long.toString(count);
    }

    private static String getData(String query) {
        String data = "";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(url, user, password)) {
            data = runner.query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}