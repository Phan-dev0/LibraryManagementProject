/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

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
 * @author Cuong0311
 */
public class FacultyService {
    public List<Faculty> getFaculties() throws SQLException {
        List<Faculty> facs = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from faculty");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Faculty faculty = new Faculty(id, name);
                facs.add(faculty);
            }
        }
//        facs.forEach(System.out::println);
        return facs;
    }
    public int getFacultyIdFromFacultyName(String facultyName) throws SQLException{
        int facultyId = 0;
        try (Connection conn = JdbcUtils.getConn()){
            String sql = "select id from faculty where name=?";
            PreparedStatement stm = conn.prepareCall(sql); // truy van den csdl
            stm.setString(1, facultyName);
            ResultSet rs = stm.executeQuery(); 
            if(rs.next()){
                facultyId = rs.getInt("id");
            }
        }
        return facultyId;
    }
    
    public Faculty getFaculty(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM faculty WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Faculty fac = new Faculty(rs.getInt("id"), rs.getString("name"));
                return fac;
            }
            return null;
        }
    }
}
