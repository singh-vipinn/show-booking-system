package com.vstech.repository;

import com.vstech.model.Booking;
import com.vstech.model.Show;
import com.vstech.model.Slot;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ShowRepository {

    private final Map<String, Show> shows = new HashMap<>();
    private final Map<String, List<Slot>> showSlots = new HashMap<>();
    private final Map<String, Booking> bookings = new HashMap<>();
    private final Map<String, List<Booking>> userBookings = new HashMap<>();

    public void saveShow(Show show) { shows.put(show.getName(), show);
    }
    public Show findShowByName(String name) { return shows.get(name);
    }

    public void addSlot(String showName, Slot slot)
    {
        showSlots.computeIfAbsent(showName, k -> new ArrayList<>()).add(slot);
    }

    public List<Slot> findSlotsByShow(String showName)
    {
        return showSlots.getOrDefault(showName, new ArrayList<>());
    }

    public void saveBooking(Booking b)
    {
        bookings.put(b.getId(), b);
        userBookings.computeIfAbsent(b.getUserName(), k -> new ArrayList<>()).add(b);
    }

    public Booking findBooking(String id) { return bookings.get(id);
    }

    public void deleteBooking(String id) {
        Booking b = bookings.remove(id);
        if (b != null) userBookings.get(b.getUserName()).remove(b);
    }

    public List<Booking> findBookingsByUser(String user) {
        return userBookings.getOrDefault(user, new ArrayList<>());
    }

    public Collection<Show> getAllShows() { return shows.values();
    }
    public Collection<Booking> getAllBookings() { return bookings.values();
    }
}
