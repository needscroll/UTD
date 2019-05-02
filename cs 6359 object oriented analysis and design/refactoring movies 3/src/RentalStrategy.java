import java.util.List;

public abstract class RentalStrategy {

    int points = 0;
    double total = 0;
    int discounts = 0;

    protected List<Rental> rentals;

    public RentalStrategy(List<Rental> rentals)
    {
        this.rentals = rentals;
        this.points = computePoints(rentals);
        this.total = computeTotal(rentals);
    }

    public int getPoints()
    {
        return points;
    }

    public double getTotal() {
        return total;
    }

    abstract int computePoints(List<Rental> rentals);
    abstract double computeTotal(List<Rental> rentals);
    abstract String getBill();

}
