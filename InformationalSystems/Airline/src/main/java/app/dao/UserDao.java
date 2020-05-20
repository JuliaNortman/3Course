package app.dao;

import app.entities.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    public User findOneById(int id) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select * from users where user_id=" + id;

        ResultSet rs = statement.executeQuery(sql);

        User user = null;
        if (rs.next()) {
            String login = rs.getString("username");
            String password = rs.getString("pswrd");
            double account = rs.getDouble("user_account");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            String email = rs.getString("email");
            user = new User(id, login, password, account, phone, address, email);
        }
        rs.close();
        conn.close();

        return user;
    }

    public User findOne(String login, String password) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "select user_id from users where username='" + login + "' and pswrd='" + password + "'";

        ResultSet rs = statement.executeQuery(sql);

        User user = null;
        if (rs.next()) {
            int id = rs.getInt("user_id");
            user = new User(id);
        }
        rs.close();
        conn.close();

        return user;
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "insert into users (username, pswrd, phone, address, email) values('" + user.getLogin() + "', '" +
                user.getPassword() + "', '" + user.getPhone() + "', '" + user.getAddress() + "', '" + user.getEmail() + "')";

        statement.executeUpdate(sql);
        conn.close();
    }

    public void updateAccount(int id, double newAccount) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDB();
        Statement statement = conn.createStatement();

        String sql = "update users set user_account=" + newAccount + " where user_id=" + id;

        statement.executeUpdate(sql);
        conn.close();
    }
}
