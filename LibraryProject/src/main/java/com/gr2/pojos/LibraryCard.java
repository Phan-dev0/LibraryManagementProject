/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.pojos;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Cuong0311
 */
public class LibraryCard {
    private String id;
    private String name;
    private int gender;
    private LocalDate birthDate;
    private String email;
    private LocalDate expireDate;
    private String address;
    private String phoneNumber;
    private String subject;
    private int faculty_id;
    {
        setId(UUID.randomUUID().toString());
    }
    
//    public LibraryCard(String id, String name, int gender, Date birthDate = null) {
        
//    }
    /**
     * @return the id
     */
    
    public LibraryCard(String id, String name, int gender, LocalDate birthDate, String email, LocalDate expireDate, String address, String phoneNumber, String subject, int faculty_id) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.expireDate = expireDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.faculty_id = faculty_id;
    }
    public LibraryCard(String name, int gender, LocalDate birthDate, int faculty_id) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.faculty_id = faculty_id;
    }
    public LibraryCard() {
    }

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gender
     */
    public int getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * @return the birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the expireDate
     */
    public LocalDate getExpireDate() {
        return expireDate;
    }

    /**
     * @param expireDate the expireDate to set
     */
    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the faculty_id
     */
    public int getFaculty_id() {
        return faculty_id;
    }

    /**
     * @param faculty_id the faculty_id to set
     */
    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }
    
    
}
