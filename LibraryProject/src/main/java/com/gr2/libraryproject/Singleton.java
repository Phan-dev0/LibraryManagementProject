/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

/**
 *
 * @author vegar
 */
public class Singleton {
    
    private static final Singleton instance = new Singleton();
    
    private String yourname;
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        return instance;
    }
    
    public String getUsername(){
        return yourname;
    }
    
    public void setUsername(String username){
        this.yourname = username; 
    }
    
   
}
