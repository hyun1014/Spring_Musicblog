package com;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.*;

@Repository
public class UserDao {
    Dotenv dotenv;
    private String driver = "com.mysql.jdbc.Driver";
    private String url;
    private String user;
    private String pw;

    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public UserDao(){
        try{
            dotenv = Dotenv.configure().directory("./").filename(".env").load();
            url = dotenv.get("url");
            user = dotenv.get("user");
            pw = dotenv.get("pw");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pw);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserInfo(String id, String pw){
        User user = new User();
        try{
            String sql = "SELECT * FROM user WHERE id=? AND pw=?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, id);
            psmt.setString(2, pw);
            rs = psmt.executeQuery();
            if(rs.next()){
                user.setId(rs.getString("id"));
                user.setPw(rs.getString("pw"));
                user.setNickname(rs.getString("nickname"));
            }
            if(user.getId()!=null)
                return user;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertUser(User user){
        try{
            String sql = "INSERT INTO user (id, pw, nickname) VALUES(?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, user.getId());
            psmt.setString(2, user.getPw());
            psmt.setString(3, user.getNickname());
            psmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            if(e instanceof MySQLIntegrityConstraintViolationException)
                return "Duplicate PK";
            return "DB Error";
        }
        return "Success";
    }
}
