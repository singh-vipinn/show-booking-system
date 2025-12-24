package com.vstech.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Booking {

    private String id;
    private String userName;
    private String showName;
    private int startTime;
    private int seats;
}
