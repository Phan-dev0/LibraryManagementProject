/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.test;

import com.gr2.pojos.Category;
import com.gr2.services.BookService;
import com.gr2.services.CategoryService;
import com.gr2.services.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author jackc
 */
public class CategoryTester {

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

    public void testNotNullName() {
        CategoryService categoryService = new CategoryService();
        try {
            List<String> cates = categoryService.getCategoryName();
            long r = cates.stream().filter(cate -> cate == null).count();
            Assertions.assertTrue(r == 0);
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testNotNullCategory() {
        CategoryService categoryService = new CategoryService();

        int id = 3;
        try {
            Category cate = categoryService.getCategoryById(id);
            Assertions.assertFalse(cate.getName() == null);
            
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
