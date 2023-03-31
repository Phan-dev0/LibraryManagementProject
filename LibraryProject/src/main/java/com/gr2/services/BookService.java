/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.Book;
import java.sql.Connection;
import java.sql.Date;
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
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book b = new Book(rs.getString("title"), rs.getString("authors"), rs.getString("description"), rs.getInt("publish_year"), rs.getString("publish_place"), rs.getDate("import_date"), rs.getString("location"), rs.getInt("category_id"), rs.getString("state"));
                books.add(b);
            }

            return books;

        }
    }

    public Book getBookById(int id) throws SQLException {
        Book book = new Book();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from book where id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            book.setId(rs.getInt("id"));
            book.setAuthors(rs.getString("authors"));
            book.setDescription(rs.getString("description"));
            book.setImportDate(rs.getDate("import_date"));
            book.setLocation(rs.getString("location"));
            book.setPublishedPlace(rs.getString("publish_place"));
            book.setPublishedYear(rs.getInt("publish_year"));
            book.setTitle(rs.getString("title"));
            book.setState(rs.getString("state"));
            book.setCategoryId(rs.getInt("category_id"));
            
            return book;
        }
    }
    
    public boolean updateBook(Book book) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "update book " +
                    "set id = ?, title = ?, authors = ?, description = ?, publish_year = ?, publish_place = ?, import_date = ?, location = ?, category_id = ?, state = ? " +
                    "where id = ?";
            
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, book.getId());
            stm.setString(2, book.getTitle());
            stm.setString(3, book.getAuthors());
            stm.setString(4, book.getDescription());
            stm.setInt(5, book.getPublishedYear());
            stm.setString(6, book.getPublishedPlace());
            stm.setDate(7, (Date) book.getImportDate());
            stm.setString(8, book.getLocation());
            stm.setInt(9, book.getCategoryId());
            stm.setString(10, book.getState());
            stm.setInt(11, book.getId());
            
            int result = stm.executeUpdate();
            return result > 0;
            
        }
    }
}
