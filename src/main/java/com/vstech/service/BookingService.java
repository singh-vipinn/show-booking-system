package com.vstech.service;

public interface BookingService {

    String bookTicket(String user, String show, int time, int seats);
    void cancelBooking(String id);
    void printTrending();
}
