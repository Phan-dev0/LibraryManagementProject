/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.Book;
import com.gr2.pojos.BorrowDetail;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jackc
 */
public class BookService {

     public int getBookIdByBookTitle(String bookTitle) throws SQLException{
         int bookId = 0;
         try(Connection conn = JdbcUtils.getConn()){
             String sql = "select id from book where title=?";
             PreparedStatement stm = conn.prepareCall(sql);
             stm.setString(1, bookTitle);
             ResultSet rs = stm.executeQuery();
             if(rs.next()){
                 bookId = rs.getInt("id");
             }
         }
         return bookId;
     }
     
     public List<BorrowDetail> getBorrowBook(String kw) throws SQLException{
        List<BorrowDetail> borrowBooks = new ArrayList<>();
        try(Connection conn =  JdbcUtils.getConn()){
            String sql = "select * From borrow_detail";
            if(kw != null && !kw.isEmpty())
                sql += " where user_id like concat ('%', ?, '%')";
            
            PreparedStatement stm = conn.prepareCall(sql);
            if(kw != null && !kw.isEmpty())
                stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                int bookId = rs.getInt("book_id");
                String userId = rs.getString("user_id");
                LocalDate borrowDate = rs.getDate("borrow_date").toLocalDate();
                LocalDate returnDate = rs.getDate("return_date").toLocalDate();
                
                BorrowDetail borrowBook = new BorrowDetail(bookId, userId, borrowDate, returnDate);
                borrowBooks.add(borrowBook);
            }
            
        }
        return borrowBooks;
    }
    
    public boolean returnBook(int bookId) throws SQLException{
        
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "delete from borrow_detail where book_id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, bookId);
            
            sql = "Update book set state=? where id=?";
            PreparedStatement stm1 = conn.prepareCall(sql);
            stm1.setString(1, "FREE");
            stm1.setInt(2, bookId);
            stm1.executeUpdate();
            
            return stm.executeUpdate() > 0;
            
        }
    }
    
    public List<Book> getBooks(String kw, String criteria) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM book where state=?";
            if (kw != null && !kw.isEmpty()) {
                sql += " AND";
                if (criteria.equals("Title") || criteria.equals("Authors")) {

                    sql += criteria.toLowerCase() + " like concat('%', ?, '%')";

                } else if (criteria.equals("Publish_Year") || criteria.equals("Category_Id")) {
                    sql += criteria.toLowerCase() + " = ?";
                }
            }

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "FREE");
            if (kw != null && !kw.isEmpty()) {
                if (criteria.equals("Title") || criteria.equals("Authors")) {

                    stm.setString(2, kw);

                } else if (criteria.equals("Publish_Year") || criteria.equals("Category_Id")) {
                    int n = Integer.parseInt(kw);
                    stm.setInt(2, n);
                }
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book b = new Book(rs.getInt("id") ,rs.getString("title"), rs.getString("authors"), rs.getString("description"), rs.getInt("publish_year"), rs.getString("publish_place"), rs.getDate("import_date"), rs.getString("location"), rs.getInt("category_id"), rs.getString("state"));
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
            while(rs.next()) {
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
            }
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
