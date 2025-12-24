package com.vstech.model;

import com.vstech.pojo.BookingRequest;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class Slot {

    private String showName;
    private int startTime;
    private int capacity;
    private int bookedCount;
    private Queue<BookingRequest> waitlist = new LinkedList<>();

    public Slot(String showName, int startTime, int capacity) {
        this.showName = showName;
        this.startTime = startTime;
        this.capacity = capacity;
        this.bookedCount = 0;
    }
}
