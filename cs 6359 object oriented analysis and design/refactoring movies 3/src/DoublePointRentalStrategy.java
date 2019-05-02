import java.util.List;

public class DoublePointRentalStrategy extends DefaultRentalStrategy{

    public DoublePointRentalStrategy(List<Rental> rentals) {
        super(rentals);
    }

    @Override
    int computePoints(List<Rental> rentals) {
        int perMovie = rentals.size();
        int perType = 0;

        for (Rental r: rentals)
        {
            if (r.getMovie().getClass() == NewMovie.class)
            {
                if (r.getDaysRented() > 1)
                {
                    perType += 1;
                }
            }
        }

        return (perMovie + perType) * 2;
    }
}
