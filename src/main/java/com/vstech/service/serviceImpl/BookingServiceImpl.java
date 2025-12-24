package com.vstech.service.serviceImpl;

import com.vstech.constant.AppConstants;
import com.vstech.model.Booking;
import com.vstech.model.Slot;
import com.vstech.pojo.BookingRequest;
import com.vstech.repository.ShowRepository;
import com.vstech.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private ShowRepository repo;

    @Override
    public String bookTicket(String user, String show, int time, int seats) {
        // Conflict Check
        boolean conflict = repo.findBookingsByUser(user).stream().anyMatch(b -> b.getStartTime() == time);
        if (conflict) {
            System.out.println(AppConstants.CONFLICT);
            return null;
        }

        Slot slot = repo.findSlotsByShow(show).stream().filter(s -> s.getStartTime() == time).findFirst().orElse(null);
        if (slot == null) return null;

        if (slot.getCapacity() - slot.getBookedCount() >= seats) {
            String id = UUID.randomUUID().toString().substring(0, 4);
            repo.saveBooking(new Booking(id, user, show, time, seats));
            slot.setBookedCount(slot.getBookedCount() + seats);
            System.out.println("Booked. Booking id: " + id);
            return id;
        } else {
            slot.getWaitlist().add(new BookingRequest(user, seats));
            System.out.println(AppConstants.SLOT_FULL_WAITLIST);
            return null;
        }
    }

    @Override
    public void cancelBooking(String id) {
        Booking b = repo.findBooking(id);
        if (b == null) return;

        Slot slot = repo.findSlotsByShow(b.getShowName()).stream().filter(s -> s.getStartTime() == b.getStartTime()).findFirst().get();
        repo.deleteBooking(id);
        slot.setBookedCount(slot.getBookedCount() - b.getSeats());
        System.out.println(AppConstants.BOOKING_CANCELED);

        // Process Waitlist
        while (!slot.getWaitlist().isEmpty() && (slot.getCapacity() - slot.getBookedCount() >= slot.getWaitlist().peek().getSeats())) {
            BookingRequest req = slot.getWaitlist().poll();
            bookTicket(req.getUserName(), slot.getShowName(), slot.getStartTime(), req.getSeats());
        }
    }

    @Override
    public void printTrending() {
        Map<String, Integer> counts = new HashMap<>();
        repo.getAllBookings().forEach(b -> counts.put(b.getShowName(), counts.getOrDefault(b.getShowName(), 0) + 1));
        counts.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(e -> System.out.println("Trending: " + e.getKey()));
    }
}
