/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.pojos;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Cuong0311
 */
public class Reservation {
    private int id;
    private LocalDateTime createdDate;
    private int bookId;
    private String userId;
    private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);
    
    public Reservation() {};
    public Reservation(LocalDateTime createdDate, int bookId, String userId) {
        this.createdDate = createdDate;
        this.bookId = bookId;
        this.userId = userId;
    }
    
    public Reservation(int id, LocalDateTime createdDate, int bookId, String userId) {
        this.id = id;
        this.createdDate = createdDate;
        this.bookId = bookId;
        this.userId = userId;
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
     * @return the createdDate
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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
    
     public boolean isSelected() {
        return selected.get();
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
