/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jackc
 */
public class BookService {

    public List<Book> getBooks(String kw, String criteria) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            
            String sql = "SELECT * FROM book";
            if (kw != null && !kw.isEmpty()) {
                if (criteria.equals("Title") || criteria.equals("Authors")) {

                    sql += " WHERE " + criteria.toLowerCase() + " like concat('%', ?, '%')";

                } else if (criteria.equals("Publish_Year") || criteria.equals("Category_Id")) {
                    sql += " WHERE " + criteria.toLowerCase() + " = ?";
                }
            }
            
            PreparedStatement stm = conn.prepareStatement(sql);
            if (kw != null && !kw.isEmpty()) {
                if (criteria.equals("Title") || criteria.equals("Authors")) {

                    stm.setString(1, kw);

                } else if (criteria.equals("Publish_Year") || criteria.equals("Category_Id")) {
                    int n = Integer.parseInt(kw);
                    stm.setInt(1, n);
                }
                
                System.out.println(stm.toString());
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book b = new Book(rs.getString("title"), rs.getString("authors"), rs.getString("description") , rs.getInt("publish_year"), rs.getString("publish_place"), rs.getDate("import_date"), rs.getString("location"), rs.getInt("category_id"), rs.getString("state"));
                books.add(b);
            }

            return books;

        }
    }
}
