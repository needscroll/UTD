
public abstract class Movie {
	
    protected String title;
    protected double price;
    abstract double getPrice(double _daysRented);
    //abstract int getPoints(double _daysRented);
    
    public String getTitle()
    {
    	return title;
    }
    
}
