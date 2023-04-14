/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.test.dev;

import com.gr2.pojos.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.gr2.services.BookService;
import com.gr2.services.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jackc
 */
public class BookTester {

    private static Connection conn;
    private static BookService bookService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        bookService = new BookService();
    }

    @AfterAll
    public static void afterAll() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetBookSuccess() {
        List<Book> books;
        try {
            books = bookService.getBooks("Dung", "Authors");
            Assertions.assertNotNull(books);
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetBookFailed() throws SQLException {
        try {
            List<Book> books = bookService.getBooks("Dung", "author");
            Assertions.assertEquals(books, new ArrayList());
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testNotEqualBookList() {
        try {
            List<Book> books = bookService.getBooks("", null);
            List<Book> booksTest = bookService.getBooks("Dung", "authors");
            Assertions.assertNotEquals(booksTest, books);
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    @Test
//    public void testEqualBookList() {
//        try {
//            List<Book> books = bookService.getBooks("", null);
//            List<Book> booksTest = bookService.getBooks("Dung", "author");
//            System.out.println(books);
//            System.out.println(booksTest);
//            Assertions.assertEquals(booksTest, books);
//        } catch (SQLException ex) {
//            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
  
}
