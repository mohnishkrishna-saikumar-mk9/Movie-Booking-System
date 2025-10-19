public class booking {
    private UserAccount user;
    private MovieShow show;
    private int seatNumber;
    private double price;
    private Date bookingDate;
    private boolean canceled = false;

    public Booking(UserAccount user, MovieShow show, int seatNumber, double price) {
        this.user = user;
        this.show = show;
        this.seatNumber = seatNumber;
        this.price = price;
        this.bookingDate = new Date();
        show.bookSeat(seatNumber);
        user.addBooking(show.getMovie().getTitle(), show.getMovie().getGenre(),
                        show.getShowTime(), seatNumber, price);
    }

    public boolean cancel() {
        if (!canceled && show.cancelSeat(seatNumber)) {
            canceled = true;
            user.addCancellation(show.getMovie().getTitle(), show.getShowTime(), seatNumber);
            return true;
        }
        return false;
    }

    public String generateTicket() {
        return "Cinema Ticket\n" +
               "User: " + user.getUsername() + "\n" +
               "Movie: " + show.getMovie().getTitle() + "\n" +
               "Time: " + show.getShowTime() + "\n" +
               "Seat: " + seatNumber + "\n" +
               "Price: Rs. " + price + "\n" +
               "Date: " + bookingDate + "\n" +
               (canceled ? "Status: Canceled" : "Status: Confirmed");
    }

    public boolean isCanceled() { return canceled; }
    public MovieShow getShow() { return show; }
    public int getSeat() { return seatNumber; }
    public double getPrice() { return price; }
    public Date getDate() { return bookingDate; }
}
