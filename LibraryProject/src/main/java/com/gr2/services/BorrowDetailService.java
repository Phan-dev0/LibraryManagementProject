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
                Date borrowDate = rs.getDate("borrow_date");
                Date returnDate = rs.getDate("return_date");
                
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
    
    public void lendBookBaseOnBookId(int bookId){
        
    }
}
