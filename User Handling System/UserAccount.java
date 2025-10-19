import java.util.*;

public class UserAccount {
    private String username;
    private List<String> bookingHistory;

    public UserAccount(String username) {
        this.username = username;
        this.bookingHistory = new ArrayList<>();
    }

    public void addBooking(String movieTitle, int screen, int seat, double price) {
        bookingHistory.add(String.format("Booked: %s at Screen %d, Seat %d, Price Rs. %.2f", movieTitle, screen, seat, price));
    }

    public void addCancellation(int screen, int seat) {
        bookingHistory.add(String.format("Canceled: Screen %d, Seat %d", screen, seat));
    }

    public void displayHistory() {
        System.out.println("Booking History for " + username + ":");
        if (bookingHistory.isEmpty())
            System.out.println("No bookings yet.");
        for (String s : bookingHistory)
            System.out.println(s);
    }
}
