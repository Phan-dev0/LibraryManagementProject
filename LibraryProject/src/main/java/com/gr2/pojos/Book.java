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
public class Book {
    private int id;
    private String title;
    private String authors;
    private String description;
    private int publishedYear;
    private String publishedPlace;
    private Date importDate;
    private String location;
    private String state;
    private int categoryId;
    
    
    public Book() {
        
    }
    
    public Book(int id, String title, String authors, String description, int publishedYear, String publishedPlace, Date importDate, String location, int cateogoryId, String state) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.publishedYear = publishedYear;
        this.publishedPlace = publishedPlace;
        this.importDate = importDate;
        this.location = location;
        this.categoryId = cateogoryId;
        this.state = state;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the publishedYear
     */
    public int getPublishedYear() {
        return publishedYear;
    }

    /**
     * @param publishedYear the publishedYear to set
     */
    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    /**
     * @return the publishedPlace
     */
    public String getPublishedPlace() {
        return publishedPlace;
    }

    /**
     * @param publishedPlace the publishedPlace to set
     */
    public void setPublishedPlace(String publishedPlace) {
        this.publishedPlace = publishedPlace;
    }

    /**
     * @return the importDate
     */
    public Date getImportDate() {
        return importDate;
    }

    /**
     * @param importDate the importDate to set
     */
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
}
