/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.LibraryCard;
import com.gr2.pojos.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vegar
 */
public class UserService {

    
    public User getLoginAccount(String username, String password) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from users where username =? and password =? limit 1";
            PreparedStatement stm = conn.prepareCall(sql); // truy van den csdl
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery(); // ket qua khi truy suat
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCardId(rs.getString("card_id"));
            }
            return user;
        }
    }

    public String getUserIdByUsername(String username) throws SQLException {
        String userId = null;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select id from users where username=?";
            PreparedStatement stm = conn.prepareCall(sql); // truy van den csdl
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery(); // ket qua khi truy suat
            if (rs.next()) {
                userId = rs.getString("id");
            }
        }
        return userId;
    }

    public boolean addUser(LibraryCard card, User user) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);

            String sql = "insert into library_card(id, name, gender, birth_date, faculty_id) values(?,?,?,?,?)";
            PreparedStatement stm = conn.prepareCall(sql);

            stm.setString(1, card.getId());
            stm.setString(2, card.getName());
            stm.setInt(3, card.getGender());
            stm.setDate(4, Date.valueOf(card.getBirthDate()));
            stm.setInt(5, card.getFaculty_id());
            int r = stm.executeUpdate();

            sql = "insert into users(id, username, password, card_id) values(?,?,?,?)";
            PreparedStatement stm1 = conn.prepareCall(sql);

            stm1.setString(1, user.getId());
            stm1.setString(2, user.getUsername());
            stm1.setString(3, user.getPassword());
            stm1.setString(4, card.getId());

            stm1.executeUpdate();

            conn.commit();

            return r > 0;
        }

    }
    
    public String getUserIdByCardId(String cardId) throws SQLException{
        String userId = "";
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "Select id from users where card_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, cardId);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                userId = rs.getString("id");
            }
        }
        return userId;
    }
    public User getUserById(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            User user = new User();
            String sql = "select * from users where id like ? ";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCardId(rs.getString("card_id"));
            }
            return user;
        }
    }
    
   

}
