/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author jackc
 */
public class MessageBox {
    public static Alert getMessageBox(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert;
    }
    
}
