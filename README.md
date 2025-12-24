Show Booking System (Spring Boot):
----------------------------------
A modular, in-memory Live Show Booking Application built with Java 17 and Spring Boot. This system allows organizers to manage shows and slots while allowing users to search, book, and cancel tickets with waitlist support.

🚀 Features:
--------------
Organizer Module: Register shows by genre and onboard 1-hour time slots with specific capacities.
Search Engine: Search shows by genre with an extensible ranking strategy (Default: Start Time).

Booking Engine:
  -Prevents double-booking for the same user in the same time slot.
  -Handles "all-or-nothing" capacity booking (No partial bookings).

Waitlist Management: Automatically promotes waitlisted users when a booking is canceled and capacity becomes available.
Trending Show: Bonus feature to track the most popular show based on ticket volume.

🏗 Architecture & Design Patterns:
-----------------------------------
The project follows a clean, layered architecture to ensure maintainability and extensibility.

-Strategy Pattern: Used for the Search Ranking logic. The system currently ranks by StartTime, but new strategies (like ReviewRanking) can be plugged in without changing the core service logic.
-Repository Pattern: Since no external database is used, a centralized ShowRepository manages in-memory data structures (HashMap, ArrayList) to simulate a database.
-Singleton Pattern: Spring-managed beans ensure services and repositories act as singletons across the application.
-Observer-like Trigger: The cancellation logic triggers a waitlist processing sequence to automatically confirm pending requests.

📂 Project Structure:
-----------------------

src/main/java/com/vstech
├── constant       # Static application strings (AppConstants)
├── model          # Core entities (Show, Slot, Booking)
├── pojo           # Plain Old Java Objects (Genre, BookingRequest)
├── repository     # In-memory data storage (ShowRepository)
├── service        # Service Interfaces
│   └── serviceImpl # Business logic implementations
├── utils          # Common utility functions
└── Driver.java    # Application entry point & Automated Demo

🛠 Prerequisites:
------------------
Java: 17 or higher
Maven: 3.6+
Lombok: Ensure your IDE has the Lombok plugin installed.

🏃 Getting Started:
1. Clone the repository
   git clone https://github.com/your-username/show-booking-system.git
   cd show-booking-system

2. Build the project
   mvn clean install

3. Run the application
   mvn spring-boot:run
(The application includes a CommandLineRunner in Driver.java that will automatically execute a full end-to-end)

🧪 Example Usage (Input/Output):
---------------------------------
Registering a Show
Input: registerShow: TMKOC -> Comedy
Output: TMKOC show is registered !!

Onboarding Slots
Input: onboardShowSlots: TMKOC 9:00-10:00 3

Output: Done!

Waitlist Logic:
----------------
If a slot with capacity 2 receives a booking request for 3 people, the system will output:

Slot full. Added to waitlist.

📝 Assignment Requirements Covered:
-------------------------------------
[x] 1-hour slot constraints.
[x] No overlapping slots for the same show.
[x] Ranking by Start Time (extensible).
[x] User conflict check (No two shows at once).
[x] No partial bookings.
[x] Waitlist promotion on cancellation.
[x] Bonus: Trending shows.

=============
Demo Output:
=============

******************************************
      SHOW BOOKING SYSTEM DEMO START      
******************************************

i: registerShow: TMKOC -> Comedy
TMKOC show is registered !!

i: onboardShowSlots: TMKOC 9:00-11:00 3
Sorry, show timings are of 1 hour only

i: onboardShowSlots: TMKOC 9:00-10:00 3, 12:00-13:00 2, 15:00-16:00 5
Done!

i: registerShow: The Sonu Nigam Live Event -> Singing
The Sonu Nigam Live Event show is registered !!
Done!

i: showAvailByGenre: Comedy
TMKOC: (9:00-10:00) 3
TMKOC: (12:00-13:00) 2
TMKOC: (15:00-16:00) 5

i: bookTicket: (UserA, TMKOC, 12:00, 2)
Booked. Booking id: ab05

i: showAvailByGenre: Comedy
TMKOC: (9:00-10:00) 3
TMKOC: (12:00-13:00) 2
TMKOC: (15:00-16:00) 5

i: cancelBookingId: ab05
Booking Canceled

i: showAvailByGenre: Comedy
TMKOC: (9:00-10:00) 3
TMKOC: (12:00-13:00) 2
TMKOC: (15:00-16:00) 5

i: bookTicket: (UserB, TMKOC, 12:00, 3) -- Note: Capacity is 2
Slot full. Added to waitlist.

--- Trending Show Feature ---

******************************************
       SHOW BOOKING SYSTEM DEMO END       
******************************************

Process finished with exit code 0
