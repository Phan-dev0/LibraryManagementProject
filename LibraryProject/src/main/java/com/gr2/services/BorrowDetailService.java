/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.BorrowDetail;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vegar
 */
public class BorrowDetailService {
    
   
    
    public void lendBookBaseOnBookId(BorrowDetail borrowBook, String userId, int bookId) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            
            String sql = "update book set state=? where id=?";
            PreparedStatement stm1 = conn.prepareCall(sql);
            stm1.setString(1, "BORROWED");
            stm1.setInt(2, bookId);
            stm1.executeUpdate();
            
            sql = "insert into borrow_detail(borrow_date, return_date, user_id, book_id) values(?,?,?,?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setDate(1, new java.sql.Date(borrowBook.getBorrowDate().getTime())); 
            stm.setDate(2, new java.sql.Date(borrowBook.getReturnDate().getTime()));
            stm.setString(3, userId);
            stm.setInt(4, bookId);
            stm.executeUpdate();
            
        }
    }
}
