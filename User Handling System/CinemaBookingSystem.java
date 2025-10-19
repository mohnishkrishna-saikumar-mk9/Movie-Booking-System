import java.util.*;

public class CinemaBookingSystem {
    private CinemaList cinema;
    private Map<String, UserAccount> users;
    private UserAccount currentUser;
    private Scanner scanner;

    public CinemaBookingSystem() {
        users = new HashMap<>();
        scanner = new Scanner(System.in);
        setupCinema();
    }

    // Setup single cinema with 8 screens, each populated with genre and multiple movies
    private void setupCinema() {
        cinema = new CinemaList("Downtown Cinema", "Main Street");

        cinema.addScreen(new CinemaScreen(1, "Action", 100, 150));
        cinema.addScreen(new CinemaScreen(2, "Animation", 80, 140));
        cinema.addScreen(new CinemaScreen(3, "Drama", 90, 130));
        cinema.addScreen(new CinemaScreen(4, "Comedy", 85, 120));
        cinema.addScreen(new CinemaScreen(5, "Thriller", 90, 130));
        cinema.addScreen(new CinemaScreen(6, "Sci-Fi", 100, 160));
        cinema.addScreen(new CinemaScreen(7, "Documentary", 80, 120));
        cinema.addScreen(new CinemaScreen(8, "Horror", 90, 135));

        // Fill movies for each screen
        cinema.getScreenByNumber(1).addMovie(new MovieSystem("Avengers: Endgame", "Action", 181, "Anthony Russo", 8.5, 2019, "Superhero blockbuster."));
        cinema.getScreenByNumber(1).addMovie(new MovieSystem("Mad Max: Fury Road", "Action", 120, "George Miller", 8.1, 2015, "Post-apocalyptic chase."));
        cinema.getScreenByNumber(2).addMovie(new MovieSystem("Spirited Away", "Animation", 125, "Hayao Miyazaki", 8.6, 2001, "Young girl in a magical world."));
        cinema.getScreenByNumber(2).addMovie(new MovieSystem("Your Name", "Animation", 106, "Makoto Shinkai", 8.4, 2016, "Body-swapping teens find each other."));
        cinema.getScreenByNumber(3).addMovie(new MovieSystem("Forrest Gump", "Drama", 142, "Robert Zemeckis", 8.8, 1994, "Life story of Forrest Gump."));
        cinema.getScreenByNumber(3).addMovie(new MovieSystem("The Shawshank Redemption", "Drama", 142, "Frank Darabont", 9.3, 1994, "Wrongfully imprisoned."));
        cinema.getScreenByNumber(4).addMovie(new MovieSystem("Superbad", "Comedy", 113, "Greg Mottola", 7.6, 2007, "High schoolers on an adventure."));
        cinema.getScreenByNumber(4).addMovie(new MovieSystem("Mean Girls", "Comedy", 97, "Mark Waters", 7.1, 2004, "Navigate teenage cliques."));
        cinema.getScreenByNumber(5).addMovie(new MovieSystem("Gone Girl", "Thriller", 149, "David Fincher", 8.1, 2014, "Wife disappears, secrets unravel."));
        cinema.getScreenByNumber(5).addMovie(new MovieSystem("Se7en", "Thriller", 127, "David Fincher", 8.6, 1995, "Detectives chase a serial killer."));
        cinema.getScreenByNumber(6).addMovie(new MovieSystem("Inception", "Sci-Fi", 148, "Christopher Nolan", 8.8, 2010, "Dream heist thriller."));
        cinema.getScreenByNumber(6).addMovie(new MovieSystem("Interstellar", "Sci-Fi", 169, "Christopher Nolan", 8.6, 2014, "Space travel for survival."));
        cinema.getScreenByNumber(7).addMovie(new MovieSystem("Free Solo", "Documentary", 100, "Jimmy Chin", 8.0, 2018, "Climbing El Capitan."));
        cinema.getScreenByNumber(7).addMovie(new MovieSystem("The Last Dance", "Documentary", 500, "Jason Hehir", 9.1, 2020, "Michael Jordan's career."));
        cinema.getScreenByNumber(8).addMovie(new MovieSystem("Get Out", "Horror", 104, "Jordan Peele", 7.7, 2017, "Visit turns horrific."));
        cinema.getScreenByNumber(8).addMovie(new MovieSystem("The Conjuring", "Horror", 112, "James Wan", 7.5, 2013, "Paranormal investigators."));
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Movie Booking System Main Menu ---");
            System.out.println("1. Register/Login");
            System.out.println("2. View Screens and Movies");
            System.out.println("3. Search Movies");
            System.out.println("4. Book Ticket");
            System.out.println("5. Cancel Booking");
            System.out.println("6. View Booking History");
            System.out.println("7. Movie Info");
            System.out.println("8. Add Movie (Admin)");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");
            int option = getIntInput();
            switch (option) {
                case 1: loginMenu(); break;
                case 2: cinema.displayScreensAndMovies(); break;
                case 3: searchMoviesMenu(); break;
                case 4: bookTicketMenu(); break;
                case 5: cancelBookingMenu(); break;
                case 6: showBookingHistoryMenu(); break;
                case 7: movieInfoMenu(); break;
                case 8: addMovieAdminMenu(); break;
                case 9: System.out.println("Thanks for using!"); return;
                default: System.out.println("Invalid choice."); break;
            }
        }
    }

    private void loginMenu() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        currentUser = users.computeIfAbsent(username, UserAccount::new);
        System.out.println("Logged in as " + username);
    }

    private void searchMoviesMenu() {
        System.out.print("Enter search keyword: ");
        scanner.nextLine();
        String keyword = scanner.nextLine().toLowerCase();
        cinema.searchMovies(keyword);
    }

    private void bookTicketMenu() {
        if (!checkUser()) return;
        cinema.displayScreens();
        System.out.print("Select screen number: ");
        int sNum = getIntInput();
        CinemaScreen screen = cinema.getScreenByNumber(sNum);
        if (screen == null) {
            System.out.println("Screen not found.");
            return;
        }
        screen.displayMovies();
        System.out.print("Select movie number: ");
        int mNum = getIntInput();
        MovieSystem movie = screen.getMovieByNumber(mNum);
        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }
        screen.displaySeats();
        System.out.print("Seat number to book: ");
        int seatNum = getIntInput();
        if (screen.bookSeat(seatNum)) {
            System.out.println("Booked seat " + seatNum + " at Rs. " + screen.getTicketPrice() + " for " + movie.getTitle());
            currentUser.addBooking(movie.getTitle(), screen.getScreenNumber(), seatNum, screen.getTicketPrice());
        } else {
            System.out.println("Seat unavailable.");
        }
    }

    private void cancelBookingMenu() {
        if (!checkUser()) return;
        cinema.displayScreens();
        System.out.print("Select screen number: ");
        int sNum = getIntInput();
        CinemaScreen screen = cinema.getScreenByNumber(sNum);
        if (screen == null) {
            System.out.println("Screen not found.");
            return;
        }
        screen.displaySeats();
        System.out.print("Seat number to cancel: ");
        int seatNum = getIntInput();
        if (screen.cancelSeat(seatNum)) {
            System.out.println("Canceled seat booking for seat " + seatNum + " in screen " + sNum);
            currentUser.addCancellation(screen.getScreenNumber(), seatNum);
        } else {
            System.out.println("Cannot cancel; seat was not booked.");
        }
    }

    private void showBookingHistoryMenu() {
        if (!checkUser()) return;
        currentUser.displayHistory();
    }

    private void movieInfoMenu() {
        cinema.displayScreens();
        System.out.print("Select screen: ");
        int sNum = getIntInput();
        CinemaScreen screen = cinema.getScreenByNumber(sNum);
        if (screen == null) {
            System.out.println("Screen not found.");
            return;
        }
        screen.displayMovies();
        System.out.print("Select movie: ");
        int mNum = getIntInput();
        MovieSystem movie = screen.getMovieByNumber(mNum);
        if (movie != null) {
            System.out.println(movie.fullInfo());
        } else {
            System.out.println("Movie not found.");
        }
    }

    private void addMovieAdminMenu() {
        cinema.displayScreens();
        System.out.print("Select screen: ");
        int screenNum = getIntInput();
        CinemaScreen screen = cinema.getScreenByNumber(screenNum);
        if (screen == null) {
            System.out.println("Screen not found.");
            return;
        }
        System.out.print("Movie Title: ");
        String t = scanner.nextLine();
        System.out.print("Genre: ");
        String g = scanner.nextLine();
        System.out.print("Duration: ");
        int d = getIntInput();
        System.out.print("Director: ");
        String director = scanner.nextLine();
        System.out.print("Rating: ");
        double rating = getDoubleInput();
        System.out.print("Year: ");
        int year = getIntInput();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        screen.addMovie(new MovieSystem(t, g, d, director, rating, year, desc));
        System.out.println("Movie added!");
    }

    private boolean checkUser() {
        if (currentUser == null) {
            System.out.println("Please login/register first.");
            return false;
        }
        return true;
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid. Enter number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // For nextLine
        return val;
    }

    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid. Enter number: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // For nextLine
        return val;
    }

    public static void main(String[] args) {
        new CinemaBookingSystem().run();
    }
}
