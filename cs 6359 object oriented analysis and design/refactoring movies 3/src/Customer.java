import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    int age;
    List<Rental> rentals;
    //int renterPoints;
    
    public Customer (String name, int age) {
        this.name = name;
        this.age = age;
        //renterPoints = 0;
        rentals = new ArrayList<Rental>();
    }
    
    public String getName() {
        return name;
    }

    /*
    private int getPoints()
    {
        return renterPoints;
    }
    */

    public void addRental(Rental rental)
    {
        rentals.add(rental);
    }

    public String getReceipt()
    {
        RentalFactory factory = RentalFactory.getInstance();
        RentalStrategy strategy = factory.getStrategy(this);
        String result = writeHeader();
        result += strategy.getBill();

        /*
    	String result = "";
    	result += writeHeader();
    	result += bill.getStatement();
    	result += writePoints();
    	*/

        return result;
    }

    public List<Rental> getRentals()
    {
        return rentals;
    }

    public int getAge()
    {
        return age;
    }

    private String writeHeader()
    {
        String result = "";
        result += "name: " + getName() + "\n";
        return result;
    }

    /*
    private String writePoints()
    {
        String result = "";
        result += "points: " + getPoints() + "\n";
        return result;
    }
    */
}