package com.example.praktika;

import java.sql.*;
import java.util.ArrayList;

public class DBuser {
    // Данные для подключения к локальной базе данных
    private final String HOST = "127.0.0.1";
    private final String PORT = "13306";
    private final String DB_NAME = "kursach";
    private final String LOGIN = "root"; // Если OpenServer, то здесь mysql напишите
    private final String PASS = "changeme"; // Если OpenServer, то здесь mysql напишите

    private Connection dbConn = null;

    private static String login;
    private static String password;

    private static int max;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8"; //если нужна кодировка UTF8
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public Integer getName(String n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_role FROM role where name_role =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, n);

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }

    public ArrayList<String> getRole() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT name_role FROM role";
        Statement statement = getDbConnection().createStatement();
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery(sql);
        ArrayList<String> country = new ArrayList<>();
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country.add(res.getString("name_role"));
        // Возврат списка товаров с фотографиями
        return country;
    }

    public int getIdRole(String log, String pas) throws SQLException, ClassNotFoundException {
        String sql = "SELECT role_idrole FROM clients where login_clients=? and password_clients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pas);

        ResultSet res = statement.executeQuery();
        int id = 0;
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            id = (res.getInt("role_idrole"));
        // Возврат списка товаров с фотографиями
        return id;
    }

    public ArrayList<String> getUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT fio_clients FROM clients where role_idrole= 1";
        Statement statement = getDbConnection().createStatement();
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery(sql);
        ArrayList<String> country = new ArrayList<>();
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country.add(res.getString("fio_clients"));
        // Возврат списка товаров с фотографиями
        return country;
    }

    public Integer getIdName(String n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_clients FROM clients where fio_clients =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, n);

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }

    public  String insertClient(String p_username,String p_login,String p_password,int p_role,String p_email) throws  SQLException, ClassNotFoundException{
        String vivod ="";
        CallableStatement proc = getDbConnection().prepareCall("CALL AddCustomer(?, ?, ?, ?,?)");
        proc.setString(1, p_username);
        proc.setString(2, p_login);
        proc.setString(3, p_password);
        proc.setInt(4, p_role);
        proc.setString(5, p_email);

        ResultSet res =proc.executeQuery();
        while (res.next()){
            vivod = res.getString("Success") ;
        }
        return  vivod;
    }

    public ArrayList<String> getBookingUsers(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_tour FROM booking where id_client= ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet res = statement.executeQuery();
        ArrayList<String> country = new ArrayList<>();
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country.add(res.getString("id_tour"));
        // Возврат списка товаров с фотографиями
        return country;
    }
    public String getDescUsers(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT description_tour FROM tour where id_tour= ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet res = statement.executeQuery();
        String country ="";
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country=(res.getString("description_tour"));
        // Возврат списка товаров с фотографиями
        return country;
    }
    public int getIdDescUsers(String desc) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_tour FROM tour where description_tour= ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, desc);

        ResultSet res = statement.executeQuery();
        int country =0;
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country=(res.getInt("id_tour"));
        // Возврат списка товаров с фотографиями
        return country;
    }

    public void  deleteProduct(int id, int tour) throws SQLException, ClassNotFoundException  {
        String sql = "delete from booking where id_client =? and id_tour=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);
        prSt.setInt(2, tour);

        prSt.executeUpdate();
    }
    public int  getIdBooking(int id) throws SQLException, ClassNotFoundException  {
        String sql = "SELECT id_booking FROM booking where id_client= ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);
        ResultSet res = prSt.executeQuery();
        int country =0;
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country=(res.getInt("id_booking"));
        // Возврат списка товаров с фотографиями
        return country;
    }
    public int  getIdUser(String log, String pas) throws SQLException, ClassNotFoundException  {
        String sql = "SELECT id_clients FROM clients where login_clients=? and password_clients=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, log);
        prSt.setString(2, pas);
        ResultSet res = prSt.executeQuery();
        int country =0;
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country=(res.getInt("id_clients"));
        // Возврат списка товаров с фотографиями
        return country;
    }
}