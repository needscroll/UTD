import java.util.List;

public class RentalFactory {

    private static RentalFactory factory;

    private RentalFactory()
    {

    }

    public synchronized static RentalFactory getInstance()
    {
        if (factory == null)
        {
            factory = new RentalFactory();
        }
        return factory;
    }

    public RentalStrategy getStrategy(Customer customer)
    {
        RentalStrategy strategy;
        boolean age18to22 = ageDiscount(customer);
        boolean twoOrMore = twoOrMoreDiscount((customer.getRentals()));

        if (age18to22 || twoOrMore)
        {
            strategy = new DoublePointRentalStrategy(customer.getRentals());
        }
        else
        {
            strategy = new DefaultRentalStrategy(customer.getRentals());
        }

        return strategy;
    }

    private boolean ageDiscount(Customer customer)
    {
        if (customer.getAge() >= 18 && customer.getAge() <= 22)
        {
            return true;
        }
        return false;
    }

    private boolean twoOrMoreDiscount(List<Rental> rentals)
    {
        if (rentals.size() < 2)
        {
            return false;
        }

        Movie firstMovie = rentals.get(0).getMovie();

        for (Rental r: rentals)
        {
            if (!r.getMovie().getClass().equals(firstMovie.getClass()))
            {
                return true;
            }
        }

        return false;
    }
}
