public class Rental {
    private Movie movie;
    private int daysRented;
    
    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }
    
    public int getDaysRented() {
        return daysRented;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public double getPrice()
    {
    	return movie.getPrice(daysRented);
    }

    /*
    public int getPoints()
    {
    	return movie.getPoints(daysRented);
    }
    */

    public String getMovieTitle()
    {
        return movie.getTitle();
    }

}