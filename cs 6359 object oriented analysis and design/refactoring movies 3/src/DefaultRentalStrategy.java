import java.util.List;

public class DefaultRentalStrategy extends RentalStrategy{

    public DefaultRentalStrategy(List<Rental> rentals) {
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

        return perMovie + perType;
    }

    @Override
    double computeTotal(List<Rental> rentals) {
        double total = 0;
        int discounts = computePoints(rentals) / 10;
        this.discounts = discounts;

        for (Rental r: rentals)
        {
            total += r.getPrice();
        }

        for (int i = 0; i < discounts; i++)
        {
            total -= rentals.get(i).getPrice();
            //System.out.println("subbing: " + rentals.get(i).getPrice());
        }
        return total;
    }

    @Override
    String getBill()
    {
        String result = "";
        result += "------------------------" + "\n";
        result += "RECEIPT" + "\n";

        int numDiscounts = discounts;
        for (Rental r: rentals)
        {
            result += "title: " + r.getMovieTitle() + "\n";
            if (numDiscounts > 0)
            {
                result += "price: FREE discount from " + r.getPrice() + "\n";
                numDiscounts--;
            }
            else
            {
                result += "price: " + r.getPrice() + "\n";
            }
        }

        result += "------------------------" + "\n";
        result += "points = " + getPoints() + "\n";
        result += "total = " + getTotal() + "\n";
        result += "discounts = " + discounts + "\n";

        return result;
    }
}
