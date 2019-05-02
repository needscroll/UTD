
public class RegularMovie extends Movie{
	
    public RegularMovie(String title) {
        this.title = title;
        this.price = 2;
    }

	@Override
	double getPrice(double _daysRented) 
	{
		if (_daysRented > 2)
		{
			return price + 1.5 * _daysRented - 2;
		}
		return price;
	}

	/*
	@Override
	int getPoints(double _daysRented) {
		// TODO Auto-generated method stub
		return 0;
	}
    */


}
