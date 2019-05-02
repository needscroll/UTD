import java.util.ArrayList;

public class Movie {

    String title;
    int seats;

    public Movie(String title, int seats) {
        this.title = title;
        this.seats = seats;
    }

    public String toString()
    {
        return title + ":" + seats;
    }

    public void incrementSeats()
    {
        seats++;
    }

    public void decrementSeats()
    {
        seats--;
    }

    public boolean matches(String name)
    {
        return title.equals(name);
    }

    public int getSeats()
    {
        return seats;
    }

    public String getTitle()
    {
        return title;
    }

}
