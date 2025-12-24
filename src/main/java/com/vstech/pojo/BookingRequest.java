package com.vstech.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingRequest {

    private String userName;
    private int seats;
}
