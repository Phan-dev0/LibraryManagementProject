/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.Category;
import com.gr2.pojos.Faculty;
import java.sql.Connection;
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
public class CategoryService {
    
    public List<String> getCategoryName() throws SQLException{
        List<String> categoryNames = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from category");
            while (rs.next()) {
                String categoryName = rs.getString("name");
                categoryNames.add(categoryName);
            }
        }
        return categoryNames;
    }
    
    public int getCategoryIdFromItsName(String name) throws SQLException{
        int categoryId = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select id from category where name=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                categoryId = rs.getInt("id");
            }
        }
        return categoryId;
    }
}
