package utils;

import models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBReader {
    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";

    private final static String USER_NAME = "postgres";
    private final static String USER_PASSWORD = "postgres";
    private final static String QUERY_SELECT_ALL = "select * from personsdata";
    private final static String QUERY_INSERT = "insert into personsdata values(?,?,?,?,?)";
    private final static String QUERY_UPDATE = "update personsdata set lastName=? where id=?";
    private final static String QUERY_DELETE = "delete from personsdata where id=?";
    public static List getPersonsDataFromDB() {
        List<Person> personsData = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {
            Statement sqlStatement =  connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(QUERY_SELECT_ALL);


            while (resultSet.next()){
                Person person = new Person(resultSet.getInt("id"), resultSet.getInt("lastname"), resultSet.getInt("firstname"), resultSet.getInt("city"), resultSet.getInt("age"));
                personsData.add(person);
            }

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection to the Postgresql DB" +
                    ". URL [%s], name [%s], password [%s]", URL, USER_NAME, USER_PASSWORD));
        }
        return personsData;
    }
    public static void insert(int id, String firstName, String lastName, String city, int age) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT);
            preparedStatement.setInt(1, id);
            preparedStatement.setNString(2, lastName);
            preparedStatement.setNString(3, firstName);
            preparedStatement.setNString(4, city);
            preparedStatement.setInt(5, age);

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection to the Postgresql DB" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
    }
    public static void update(int id, String lastName) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE);
            preparedStatement.setInt(1, id);
            preparedStatement.setNString(2, lastName);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection to the Postgresql DB" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
    }

    public static void delete(int id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {

            PreparedStatement preparedStatement = connection.prepareStatement(QUERY_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException(String.format("Please check connection to the Postgresql DB" +
                    ". URL [%s], name [%s], pass [%s]", URL, USER_NAME, USER_PASSWORD));
        }
    }
    public static void main(String[] args) {
        getPersonsDataFromDB();
    }
}
