import java.util.*;

public class CinemaList {
    private String name;
    private String location;
    private List<CinemaScreen> screens;

    public CinemaList(String name, String location) {
        this.name = name;
        this.location = location;
        this.screens = new ArrayList<>();
    }

    public void addScreen(CinemaScreen s) {
        screens.add(s);
    }

    public List<CinemaScreen> getScreens() {
        return screens;
    }

    public CinemaScreen getScreenByNumber(int screenNum) {
        for (CinemaScreen s : screens)
            if (s.getScreenNumber() == screenNum) return s;
        return null;
    }

    public void displayScreens() {
        System.out.println("Screens in " + name + " (" + location + "):");
        for (CinemaScreen screen : screens) {
            System.out.printf("Screen %d - Genre: %s - Seats: %d - Price: Rs. %.2f\n",
                screen.getScreenNumber(), screen.getGenre(),
                screen.getSeatCapacity(), screen.getTicketPrice());
        }
    }

    public void displayScreensAndMovies() {
        displayScreens();
        for (CinemaScreen screen : screens) {
            System.out.println("Movies in Screen " + screen.getScreenNumber() + ":");
            screen.displayMovies();
        }
    }

    public void searchMovies(String keyword) {
        boolean found = false;
        for (CinemaScreen screen : screens) {
            for (MovieSystem movie : screen.getMovies()) {
                if (movie.getTitle().toLowerCase().contains(keyword)) {
                    System.out.printf("Found: %s in Screen %d (%s)\n",
                            movie.getTitle(), screen.getScreenNumber(), screen.getGenre());
                    found = true;
                }
            }
        }
        if (!found)
            System.out.println("No movies found matching: " + keyword);
    }
}
