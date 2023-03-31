/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.LibraryCard;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author vegar
 */
public class LibraryCardService {
    public void getYourname() throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            
            String sql = "Select name from library_card";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            
            
            
        }
    }
    
    public LibraryCard getLibraryCard(String userId) {
        return null;
    }
    
}
