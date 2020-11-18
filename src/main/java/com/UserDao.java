package com;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserInfo(String id, String pw){
        User user = new User();
        try{
            String sql = "SELECT * FROM user WHERE id=? AND pw=?";
            user = jdbcTemplate.queryForObject(sql, new Object[]{id, pw}, new RowMapper<User>(){
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setPw(rs.getString("pw"));
                    user.setNickname(rs.getString("nickname"));
                    return user;
                }
            });
            if(user.getId()!=null)
                return user;
        }
        catch (DataAccessException e){
            e.printStackTrace();
        }
        return null;
    }
    public String insertUser(User user){
        try{
            String sql = "INSERT INTO user (id, pw, nickname) values (?, ?, ?)";
            int sqlResult = jdbcTemplate.update(sql, user.getId(), user.getPw(), user.getNickname());
        }
        catch (DataAccessException e){
            e.printStackTrace();
            return "DB Error";
        }
        return "Success";
    }
}
