
public class NewMovie extends Movie{
	
    public NewMovie(String title) {
        this.title = title;
    }

	@Override
	double getPrice(double _daysRented) 
	{
		return _daysRented * 3;
	}

	/*
	@Override
	int getPoints(double _daysRented) {
		if (_daysRented > 1)
		{
			return 1;
		}
		return 0;
	}
    */

}
