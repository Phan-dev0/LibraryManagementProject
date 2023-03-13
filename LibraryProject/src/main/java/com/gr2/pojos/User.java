/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.pojos;

import java.util.UUID;

/**
 *
 * @author Cuong0311
 */
public class User {
    private String id;
    private String username;
    private String password;
    private String cardId;
    
    {
        setId(UUID.randomUUID().toString());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the card_id
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param card_id the card_id to set
     */
    public void setCardId(String card_id) {
        this.cardId = card_id;
    }
    
    
}
