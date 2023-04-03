/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.pojos;

import java.util.Date;

/**
 *
 * @author Cuong0311
 */
public class BorrowDetail {
    private int id;
    private Date borrowDate;
    private Date returnDate;
    private String userId;
    private int bookId;

    public BorrowDetail(int bookId, String userId, Date borrowDate, Date returnDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public BorrowDetail( Date borrowDate, Date returnDate) {
       this.borrowDate = borrowDate;
       this.returnDate = returnDate;
    }

    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the borrowDate
     */
    public Date getBorrowDate() {
        return borrowDate;
    }

    /**
     * @param borrowDate the borrowDate to set
     */
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the bookId
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
