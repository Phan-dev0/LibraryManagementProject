/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.BorrowDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

/**
 *
 * @author vegar
 */
public class BorrowDetailService {
    
   
    
    public void lendBookBaseOnBookId(BorrowDetail borrowBook, String userId, int bookId) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            conn.setAutoCommit(false);
            String sql = "update book set state=? where id=?";
            PreparedStatement stm1 = conn.prepareCall(sql);
            stm1.setString(1, "BORROWED");
            stm1.setInt(2, bookId);
            stm1.executeUpdate();
            
            sql = "insert into borrow_detail(borrow_date, return_date, user_id, book_id) values(?,?,?,?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setDate(1, Date.valueOf(borrowBook.getBorrowDate())); 
            stm.setDate(2, Date.valueOf(borrowBook.getReturnDate()));
            stm.setString(3, userId);
            stm.setInt(4, bookId);
            stm.executeUpdate();
            conn.commit();
            
        }
    }
}
