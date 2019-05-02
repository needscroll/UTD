
public class ChildrensMovie extends Movie{
	
    public ChildrensMovie(String title) {
        this.title = title;
        this.price = 1.5;
    }

	@Override
	double getPrice(double _daysRented) 
	{
		if (_daysRented > 2)
		{
			return price + 1.5 * _daysRented - 3;
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
