/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.LibraryCard;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author vegar
 */
public class LibraryCardService {
    public String getYourNameByUsername(String username) throws SQLException{
        String cardId = "";
        String yourName = "";
        try(Connection conn = JdbcUtils.getConn()){    
            String sql = "select card_id from users where username=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                cardId = rs.getString("card_id");
            }
            
            sql = "select name from library_card where id=?";
            PreparedStatement stm1 = conn.prepareCall(sql);
            stm1.setString(1, cardId);
            ResultSet rs1 = stm1.executeQuery();
            if(rs1.next()){
                yourName = rs1.getString("name");
            }
        }
        
        return yourName; 
    }
    
     public LibraryCard getLibraryCard(String id) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM library_card where id=? LIMIT 1";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                LibraryCard libCard = new LibraryCard(rs.getString("id"), rs.getString("name"),
                        rs.getInt("gender"), rs.getDate("birth_date").toLocalDate(), 
                        rs.getString("email"), rs.getDate("expire").toLocalDate(), 
                        rs.getString("address"), rs.getString("phone_number"),
                        rs.getString("subject"), rs.getInt("faculty_id"));
                return libCard;
                        
            }
            return null;
            
            
                    
        }
    }
    
}
