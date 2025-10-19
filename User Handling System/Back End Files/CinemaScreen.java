import java.util.*;

public class CinemaScreen {
    private int screenNumber;
    private String genre;
    private int seatCapacity;
    private double ticketPrice;
    private List<MovieSystem> movies;
    private boolean[] seats;

    public CinemaScreen(int screenNumber, String genre, int seatCapacity, double ticketPrice) {
        this.screenNumber = screenNumber;
        this.genre = genre;
        this.seatCapacity = Math.max(seatCapacity, 80);
        this.ticketPrice = Math.max(ticketPrice, 120);
        this.movies = new ArrayList<>();
        this.seats = new boolean[this.seatCapacity];
    }

    public int getScreenNumber() { return screenNumber; }
    public String getGenre() { return genre; }
    public int getSeatCapacity() { return seatCapacity; }
    public double getTicketPrice() { return ticketPrice; }

    public void addMovie(MovieSystem movie) { movies.add(movie); }
    public List<MovieSystem> getMovies() { return movies; }

    public MovieSystem getMovieByNumber(int index) {
        if (index < 1 || index > movies.size()) return null;
        return movies.get(index-1);
    }

    public int getAvailableSeatsCount() {
        int count = 0;
        for (boolean seat : seats) if (!seat) count++;
        return count;
    }

    public boolean bookSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seatCapacity || seats[seatNumber-1]) return false;
        seats[seatNumber-1] = true;
        return true;
    }

    public boolean cancelSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seatCapacity || !seats[seatNumber-1]) return false;
        seats[seatNumber-1] = false;
        return true;
    }

    public void displaySeats() {
        System.out.println("Screen " + screenNumber + " Seat Map (X = Booked, O = Available):");
        for (int i = 0; i < seats.length; i++) {
            System.out.print(seats[i] ? "X " : "O ");
            if ((i+1) % 10 == 0) System.out.println();
        }
        System.out.println();
    }

    public void displayMovies() {
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i+1) + ". " + movies.get(i));
        }
    }
}
