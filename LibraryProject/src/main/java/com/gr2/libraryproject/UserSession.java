 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.libraryproject;

import com.gr2.pojos.User;

/**
 *
 * @author vegar
 */
public class UserSession {

    
    
    private static UserSession instance;
    
    private User user;
    private String userRole;
    
    
    private UserSession(User user, String userRole){
        this.user = user;
        this.userRole = userRole;
    }
    
    public static void createSession(User user, String userRole) {
        if (instance == null) {
            instance = new UserSession(user, userRole);
        }
    }
    
    public static UserSession getSession() {
        return instance;
    }
    
    public void cleanSession() {
        user = null;
        userRole = "";
    }

    @Override
    public String toString() {
        return "Current User Session: \n" +
                "username= " + this.user + "\n" +
                "userRole=" + this.userRole;
    }
    
    
    
    
    /**
     * @return the username
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

   
    
   
}
