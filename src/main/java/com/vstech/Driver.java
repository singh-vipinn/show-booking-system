package com.vstech;

import com.vstech.service.BookingService;
import com.vstech.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Driver implements CommandLineRunner {

    @Autowired private ShowService showService;
    @Autowired
    private BookingService bookingService;

    public static void main(String[] args) {
        SpringApplication.run(Driver.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("\n******************************************");
        System.out.println("      SHOW BOOKING SYSTEM DEMO START      ");
        System.out.println("******************************************\n");

        // 1. Register Show
        System.out.println("i: registerShow: TMKOC -> Comedy");
        showService.registerShow("TMKOC", "Comedy");

        // 2. Onboard Slots (Testing 1-hour constraint)
        System.out.println("\ni: onboardShowSlots: TMKOC 9:00-11:00 3");
        showService.onboardShowSlots("TMKOC", Arrays.asList("9:00-11:00 3"));

        // 3. Onboard valid slots
        System.out.println("\ni: onboardShowSlots: TMKOC 9:00-10:00 3, 12:00-13:00 2, 15:00-16:00 5");
        showService.onboardShowSlots("TMKOC", Arrays.asList("9:00-10:00 3", "12:00-13:00 2", "15:00-16:00 5"));

        // 4. Register another show
        System.out.println("\ni: registerShow: The Sonu Nigam Live Event -> Singing");
        showService.registerShow("The Sonu Nigam Live Event", "Singing");
        showService.onboardShowSlots("The Sonu Nigam Live Event", Arrays.asList("10:00-11:00 3", "13:00-14:00 2", "17:00-18:00 1"));

        // 5. Search for Comedy
        System.out.println("\ni: showAvailByGenre: Comedy");
        showService.showAvailByGenre("Comedy");

        // 6. Book Ticket (Normal Case)
        System.out.println("\ni: bookTicket: (UserA, TMKOC, 12:00, 2)");
        String bookingId = bookingService.bookTicket("UserA", "TMKOC", 12, 2);

        // 7. Search again (Verify capacity reduced - 12:00 slot should be gone or 0)
        System.out.println("\ni: showAvailByGenre: Comedy");
        showService.showAvailByGenre("Comedy");

        // 8. Cancel Booking
        if (bookingId != null) {
            System.out.println("\ni: cancelBookingId: " + bookingId);
            bookingService.cancelBooking(bookingId);
        }

        // 9. Search again (Verify 12:00 slot is back)
        System.out.println("\ni: showAvailByGenre: Comedy");
        showService.showAvailByGenre("Comedy");

        // 10. Waitlist Demo (Booking more than capacity)
        System.out.println("\ni: bookTicket: (UserB, TMKOC, 12:00, 3) -- Note: Capacity is 2");
        bookingService.bookTicket("UserB", "TMKOC", 12, 3);

        // 11. Trending Show Demo
        System.out.println("\n--- Trending Show Feature ---");
        bookingService.printTrending();

        System.out.println("\n******************************************");
        System.out.println("       SHOW BOOKING SYSTEM DEMO END       ");
        System.out.println("******************************************");
    }
}
