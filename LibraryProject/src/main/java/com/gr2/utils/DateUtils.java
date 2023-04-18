/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gr2.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author jackc
 */
public class DateUtils {
    // Return period calculated by days
    private final int DEFAULT_RETURN_PERIOD = 30;
    
    public LocalDate getReturnDate(LocalDate borrowDate) {
        LocalDate returnDate = borrowDate.plusDays(DEFAULT_RETURN_PERIOD);
        return returnDate;
    }
    
}
