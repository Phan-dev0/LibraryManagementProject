/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.services;

import com.gr2.pojos.Book;
import com.gr2.pojos.Reservation;
import com.gr2.pojos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author jackc
 */
public class ReservationService {

    public boolean createReservation(String userId, int bookId) throws SQLException {
        UserService userService = new UserService();
        BookService bookService = new BookService();

        try (Connection conn = JdbcUtils.getConn()) {
            User user = userService.getUserById(userId);
            Book book = bookService.getBookById(bookId);

            if (!book.getState().equals("FREE")) {
                return false;
            }

            book.setState("RESERVED");
            if (!bookService.updateBook(book))
                return false;
            Reservation reservation = new Reservation(LocalDateTime.now(), book.getId(), user.getId());
            String sql = "insert into reservation(created_date, book_id, user_id) values(?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setTimestamp(1, Timestamp.valueOf(reservation.getCreatedDate()));
            stm.setInt(2, reservation.getBookId());
            stm.setString(3, reservation.getUserId());
            
            int result = stm.executeUpdate();
            return result > 0;
        }
        
        

    }
    
    public boolean deleteReservation(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn())  {
            String sql = "delete from reservation where id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }

}
