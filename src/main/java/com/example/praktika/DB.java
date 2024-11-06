package com.example.praktika;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DB {
    // Данные для подключения к локальной базе данных
    private final String HOST = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB_NAME = "curcah";
    private final String LOGIN = "root"; // Если OpenServer, то здесь mysql напишите
    private final String PASS = "Vi281205ka"; // Если OpenServer, то здесь mysql напишите

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

    public int getUsers(String log, String pas) throws SQLException, ClassNotFoundException {
        String sql = "SELECT count(*) as n FROM clients where login_clients=? and password_clients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pas);

        login = log;
        password = pas;

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }

    public int getIdUsers()throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_clients FROM clients where login_clients=? and password_clients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, password);

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }

    public ArrayList<String> getCountry() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT name_country FROM country";
        Statement statement = getDbConnection().createStatement();
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery(sql);
        ArrayList<String> country = new ArrayList<>();
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            country.add(res.getString("name_country"));
        // Возврат списка товаров с фотографиями
        return country;
    }

    public int getMaxTour() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id_tour) FROM tour";
        Statement statement = getDbConnection().createStatement();
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery(sql);
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }

    public String getName(Integer n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name_tour FROM tour where id_tour =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, n);

        ResultSet res = statement.executeQuery();
        String col = "";
        while (res.next()) {
            col = res.getString(1);
        }
        return col;
    }

    public String getDescription(Integer n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT description_tour FROM tour where id_tour =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, n);

        ResultSet res = statement.executeQuery();
        String col = "";
        while (res.next()) {
            col = res.getString(1);
        }
        return col;
    }

    public Integer getCount(Integer n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_hotel FROM tour where id_tour =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, n);

        ResultSet res = statement.executeQuery();
        Integer col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }
    public String getNamaCount(Integer n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name_country FROM country where id_country =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, n);

        ResultSet res = statement.executeQuery();
        String col = "";
        while (res.next()) {
            col = res.getString(1);
        }
        return col;
    }

    public Integer getPrice(Integer n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT price_tour FROM tour where id_tour =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, n);

        ResultSet res = statement.executeQuery();
        Integer col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }
    public ArrayList<Photo> getTour() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tour ORDER BY `id_tour` DESC";

        Statement statement = getDbConnection().createStatement();
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery(sql);

        // Создание списка объектов Photo для хранения результата
        ArrayList<Photo> stud = new ArrayList<>();
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(new Photo(res.getString("name_tour"), res.getString("description_tour"), res.getInt("price_tour"), res.getString("photo")));
        // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Integer> getCountry(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_country FROM country where name_country like ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, "%" + name + "%");
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Integer> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getInt("id_country"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Integer> getHotel(int name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_hotel FROM hotel where id_country = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, name);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Integer> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getInt("id_hotel"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Photo> getTour(int name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tour where id_hotel = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  name);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Photo> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(new Photo(res.getString("name_tour"), res.getString("description_tour"), res.getInt("price_tour"), res.getString("photo")));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<String> getClimate() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT climate_country FROM hotel";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<String> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getString("climate_country"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Integer> getClimate(String climate_country) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_hotel FROM hotel where climate_country =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1,  climate_country);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Integer> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getInt("id_hotel"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Integer> getSea() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_hotel FROM hotel where proximity_sea >0 ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Integer> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getInt("id_hotel"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Integer> getSeaNo() throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_hotel FROM hotel where proximity_sea = 0 ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Integer> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getInt("id_hotel"));    // Возврат списка товаров с фотографиями
        return stud;
    }
    public ArrayList<Photo> getTourClimate(int n) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tour where id_hotel = ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  n);

        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();

        // Создание списка объектов Photo для хранения результата
        ArrayList<Photo> stud = new ArrayList<>();
        // Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(new Photo(res.getString("name_tour"), res.getString("description_tour"), res.getInt("price_tour"), res.getString("photo")));
        // Возврат списка товаров с фотографиями
        return stud;
    }

    public int getIdTour(String name_tour) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_tour FROM tour where name_tour = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1,  name_tour);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        int stud = 0;
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud = res.getInt("id_tour");    // Возврат списка товаров с фотографиями
        return stud;
    }

    public double getPriceTour(int id_tour) throws SQLException, ClassNotFoundException {
        String sql = "SELECT price_tour FROM tour where id_tour = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  id_tour);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        double stud =  0.0;
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud = res.getDouble("price_tour");    // Возврат списка товаров с фотографиями
        return stud;
    }

    public void  insertProduct(int id_client, int id_tour, int count_people, BigDecimal final_price, Date start_date) throws SQLException, ClassNotFoundException  {
        String sql = "insert into booking values (null, ?,?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id_client);
        prSt.setInt(2, id_tour);
        prSt.setInt(3, count_people);
        prSt.setBigDecimal(5, final_price);
        prSt.setDate(4, (java.sql.Date) start_date);

        prSt.executeUpdate();
    }

    public Date getData(int n) throws SQLException, ClassNotFoundException, ParseException {
        String sql = "SELECT start_data FROM booking where id_booking = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  n);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date_f = null;
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            date_f =res.getDate("start_data");    // Возврат списка товаров с фотографиями
        return date_f;
    }

    public void  insertTour(String name_tour, String description_tour, int price_tour, int id_hotel) throws SQLException, ClassNotFoundException  {
        String sql = "insert into tour values (null, ?,?,?,null, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name_tour);
        prSt.setString(2, description_tour);
        prSt.setInt(3, price_tour);
        prSt.setInt(4, id_hotel);

        prSt.executeUpdate();
    }

    public ArrayList<String> getHotel() throws SQLException, ClassNotFoundException {
        String sql = "SELECT name_hotel FROM hotel";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<String> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getString("name_hotel"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public int getIdHotel(String name_hotel) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_hotel FROM hotel where name_hotel = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1,  name_hotel);
        ResultSet res = statement.executeQuery();
        int stud = 0;
        while(res.next())
            stud = res.getInt("id_hotel");    // Возврат списка товаров с фотографиями
        return stud;
    }

    public void  deleteTour(int id) throws SQLException, ClassNotFoundException  {
        String sql = "delete from tour where id_tour =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id);

        prSt.executeUpdate();
    }

    public void  updateTour(String name_tour, String description_tour, int price_tour, int id_hotel, int id_tour) throws SQLException, ClassNotFoundException  {
        String sql = "update tour set name_tour =?, description_tour=?, price_tour =?, id_hotel =? where id_tour=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name_tour);
        prSt.setString(2, description_tour);
        prSt.setInt(3, price_tour);
        prSt.setInt(4, id_hotel);
        prSt.setInt(5, id_tour);

        prSt.executeUpdate();
    }
    public ArrayList<Integer> getIdTourU(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_tour FROM booking where id_client = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  id);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Integer> stud = new ArrayList<Integer>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getInt("id_tour"));    // Возврат списка товаров с фотографиями
        return stud;
    }

    public ArrayList<Photo> getTourId(int name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM tour where id_tour = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1,  name);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<Photo> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(new Photo(res.getString("name_tour"), res.getString("description_tour"), res.getInt("price_tour"), res.getString("photo")));    // Возврат списка товаров с фотографиями
        return stud;
    }
    public ArrayList<String> getReviews() throws SQLException, ClassNotFoundException {
        String sql = "SELECT review FROM reviews";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        // Выполнение SQL запроса и получение результата в виде объекта ResultSet
        ResultSet res = statement.executeQuery();
// Создание списка объектов Photo для хранения результата
        ArrayList<String> stud = new ArrayList<>();
// Перебор результатов ResultSet и добавление объектов Photo в список
        while(res.next())
            stud.add(res.getString("review"));    // Возврат списка товаров с фотографиями
        return stud;
    }
    public void  insertReviews(int id_client, String review, int star) throws SQLException, ClassNotFoundException  {
        String sql = "insert into reviews values (?,?,null, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, id_client);
        prSt.setString(2, review);
        prSt.setInt(3, star);

        prSt.executeUpdate();
    }
    public ArrayList<Integer> getStar() throws SQLException, ClassNotFoundException {
        String sql = "SELECT star FROM reviews";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> stud = new ArrayList<>();
        while(res.next())
            stud.add(res.getInt("star"));    // Возврат списка товаров с фотографиями
        return stud;
    }
}
