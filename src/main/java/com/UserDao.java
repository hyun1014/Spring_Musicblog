package com;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Repository
public class UserDao {
    @Autowired
    DriverManagerDataSource dataSource;
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public User getUserInfo(String id, String pw){
        User user = new User();
        try{
            conn = dataSource.getConnection();
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
            conn.close();
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
            conn = dataSource.getConnection();
            String sql = "INSERT INTO user (id, pw, nickname) VALUES(?, ?, ?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, user.getId());
            psmt.setString(2, user.getPw());
            psmt.setString(3, user.getNickname());
            psmt.executeUpdate();
            conn.close();
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
