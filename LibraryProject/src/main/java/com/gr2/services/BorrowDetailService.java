/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.Book;
import com.gr2.pojos.BorrowDetail;
import com.gr2.utils.DateUtils;
import com.gr2.utils.MessageBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author vegar
 */
public class BorrowDetailService {
    
    public boolean lendMultiBooksBaseOnBookId(String userId, ArrayList<Integer> bookIds) throws SQLException{
        BookService bookService = new BookService();
        try(Connection conn = JdbcUtils.getConn()){
            DateUtils dateUtils = new DateUtils();
            BorrowDetail borrowBook = new BorrowDetail(LocalDate.now(), dateUtils.getReturnDate(LocalDate.now()));
            for(int bookId: bookIds){
//                System.out.println(bookId);
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
            }
            
            return false;
        }
    }
    public boolean lendBookBaseOnBookId(String userId, int bookId) throws SQLException {
        ReservationService reservationService = new ReservationService();
        
        BookService bookService = new BookService();
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            DateUtils dateUtils = new DateUtils();
            BorrowDetail borrowBook = new BorrowDetail(LocalDate.now(), dateUtils.getReturnDate(LocalDate.now()));
            Book b = bookService.getBookById(bookId);
            if (b.getState().equals("RESERVED")) {
                if (!reservationService.deleteReservation(bookId, userId)) {
                    MessageBox.getMessageBox("ERROR", "Remove Reservation Failed", Alert.AlertType.WARNING).show();
                }
            }
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
            boolean success = stm.executeUpdate() > 0;
            
            conn.commit();
            return success;

        }
    }
}

