/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.test;

import com.gr2.pojos.Book;
import com.gr2.pojos.Faculty;
import com.gr2.services.BookService;
import com.gr2.services.FacultyService;
import com.gr2.services.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author jackc
 */
public class FacultyTester {
    private static Connection conn;
    private static FacultyService facultyService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        facultyService = new FacultyService();
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
    public void testFacultiesNotNull() {

        try {
            List<Faculty> f = facultyService.getFaculties();
            long r = f.stream().filter(faculty -> faculty.getName() == null).count();
            Assertions.assertTrue(r == 0);
        } catch (SQLException ex) {
            Logger.getLogger(BookTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
