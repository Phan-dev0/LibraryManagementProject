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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jackc
 */
public class BookService {

    public int getBookIdByBookTitle(String bookTitle) throws SQLException {
        int bookId = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select id from book where title=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, bookTitle);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                bookId = rs.getInt("id");
            }
        }
        return bookId;
    }

    public List<BorrowDetail> getBorrowBook(String kw) throws SQLException {
        List<BorrowDetail> borrowBooks = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from borrow_detail";
            if (kw != null && !kw.isEmpty()) {
                    sql += " where user_id like concat ('%', ?, '%')";
            }
            
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isEmpty()) {
                   stm.setString(1, kw);
            }
            
            ResultSet rs = stm.executeQuery();
            
            
                    
            while(rs.next()){
                int bookId = rs.getInt("book_id");
                
                sql = "select state from book where id=?";
                
                PreparedStatement stm1 = conn.prepareCall(sql);
                stm1.setInt(1, bookId);
                
                ResultSet rs1 = stm1.executeQuery();
                if(rs1.next()){
                    if(rs1.getString("state").equals("BORROWED") && rs.getBoolean("is_return") == false){
                        int book_id = rs.getInt("book_id");
                        String user_id = rs.getString("user_id");
                        LocalDate borrow_date = rs.getDate("borrow_date").toLocalDate();
                        LocalDate return_date  = rs.getDate("return_date").toLocalDate();
                        boolean is_return = rs.getBoolean("is_return");
                                
                        BorrowDetail borrowBook = new BorrowDetail(bookId, user_id, borrow_date, return_date, is_return);
                        borrowBooks.add(borrowBook);
                    }
                }
                
            }
        }
        return borrowBooks;
    }

    public boolean returnBook(int bookId) throws SQLException {

        try (Connection conn = JdbcUtils.getConn()) {

            String sql = "update book set state=? where id=?";
            PreparedStatement stm1 = conn.prepareCall(sql);
            stm1.setString(1, "FREE");
            stm1.setInt(2, bookId);
            stm1.executeUpdate();
            
            sql = "update borrow_detail set is_return=? where book_id=?";
            
            PreparedStatement stm2 = conn.prepareCall(sql);
            stm2.setString(1, "1");
            stm2.setInt(2, bookId);
            stm2.executeUpdate();

            return stm1.executeUpdate() > 0;

        }
    }

    public List<Book> getBooks(String kw, String criteria) throws SQLException {
        List<Book> books = new ArrayList<>();
        if (criteria != null) {
            criteria = criteria.toLowerCase();

            try (Connection conn = JdbcUtils.getConn()) {
                String sql = "SELECT * FROM book where state=?";
                if (kw != null && !kw.isEmpty()) {
                    sql += " AND ";
                    if (criteria.equals("title") || criteria.equals("authors")) {

                        sql += criteria + " like concat('%', ?, '%')";

                    } else if (criteria.equals("publish_year") || criteria.equals("category_id")) {
                        sql += criteria + " = ?";
                    }
                    else {
                        return books;
                    }
                }

                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, "FREE");
                if (kw != null && !kw.isEmpty()) {
                    if (criteria.equals("title") || criteria.equals("authors")) {

                        stm.setString(2, kw);

                    } else if (criteria.equals("publish_year") || criteria.equals("category_id")) {
                        int n = Integer.parseInt(kw);
                        stm.setInt(2, n);
                    }
                }
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    Book b = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("authors"), rs.getString("description"), rs.getInt("publish_year"), rs.getString("publish_place"), rs.getDate("import_date").toLocalDate(), rs.getString("location"), rs.getInt("category_id"), rs.getString("state"));
                    books.add(b);

                }
            }

        }
        return books;
    }

    public Book getBookById(int id) throws SQLException {
        Book book = new Book();

        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "select * from book where id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                book.setId(rs.getInt("id"));
                book.setAuthors(rs.getString("authors"));
                book.setDescription(rs.getString("description"));
                book.setImportDate(rs.getDate("import_date").toLocalDate());
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

    public boolean addBook(Book book) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            String sql;
            sql = "insert into book (title, authors, description, publish_year, publish_place, import_date, location, category_id, state) "
                    + "values (?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, book.getTitle());
            stm.setString(2, book.getAuthors());
            stm.setString(3, book.getDescription());
            stm.setInt(4, book.getPublishedYear());
            stm.setString(5, book.getPublishedPlace());
            stm.setDate(6, Date.valueOf(book.getImportDate()) );
            stm.setString(7, book.getLocation());
            stm.setInt(8, book.getCategoryId());
            stm.setString(9, book.getState());
            stm.executeUpdate();
            
            return false;
        }
    }
    public void deleteBook(int id) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "delete from book where id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            
            stm.setInt(1, id);
            stm.executeUpdate();
            
        }
    }
    public boolean updateBook(Book book) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "update book "
                    + "set id = ?, title = ?, authors = ?, description = ?, publish_year = ?, publish_place = ?, import_date = ?, location = ?, category_id = ?, state = ? "
                    + "where id = ?";

            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, book.getId());
            stm.setString(2, book.getTitle());
            stm.setString(3, book.getAuthors());
            stm.setString(4, book.getDescription());
            stm.setInt(5, book.getPublishedYear());
            stm.setString(6, book.getPublishedPlace());
            stm.setDate(7, Date.valueOf(book.getImportDate()));
            stm.setString(8, book.getLocation());
            stm.setInt(9, book.getCategoryId());
            stm.setString(10, book.getState());
            stm.setInt(11, book.getId());
            
            stm.executeUpdate();
            
            int result = stm.executeUpdate();
            return result > 0;

        }
    }
    
    public boolean isLendMoreFiveBook(String userID) throws SQLException{
        int lendBooksQuantity = 0;
        
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "select * from borrow_detail where user_id=? and is_return=?";
            
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, userID);
            stm.setBoolean(2, false);
            
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                int bookId = rs.getInt("book_id");
                sql = "select state from book where id=?";
                
                PreparedStatement stm1 = conn.prepareCall(sql);
                stm1.setInt(1, bookId);
                
                ResultSet rs1 = stm1.executeQuery();
                if(rs1.next()){
                    if(rs1.getString("state").equals("BORROWED")){
                        lendBooksQuantity++;
                    }
                }
                
            }
            
        }
        System.out.println(lendBooksQuantity);
        if(lendBooksQuantity >= 5)
            return true;
        return false;
    }
}
