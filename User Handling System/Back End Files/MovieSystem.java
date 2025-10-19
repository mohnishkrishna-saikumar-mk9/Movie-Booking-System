public class MovieSystem {
    private String title;
    private String genre;
    private int durationMinutes;
    private String director;
    private double rating;
    private int year;
    private String description;

    public MovieSystem(String title, String genre, int duration, String director, double rating, int year, String description) {
        this.title = title;
        this.genre = genre;
        this.durationMinutes = duration;
        this.director = director;
        this.rating = rating;
        this.year = year;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDurationMinutes() { return durationMinutes; }

    public String fullInfo() {
        return String.format("%s (%s)\nDuration: %d min\nDirector: %s\nRating: %.1f\nYear: %d\nDescription: %s",
                title, genre, durationMinutes, director, rating, year, description);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d min", title, genre, durationMinutes);
    }
}
