public class Dvd extends CatalogItem implements Comparable<Dvd>{

	private String title = "";
	private double price = 0;
	private int year = 0;
	private int dvdcode = 0;
	private String director = "";
	private double discount = .8;
	
	public Dvd()
	{
		
	}
	
	public Dvd(String title, double price, int year, int dvdcode, String director, double discount)
	{
		this.title = title;
		this.price = price;
		this.year = year;
		this.dvdcode = dvdcode;
		this.director = director;
		this.discount = discount;
	}
	
	@Override
	public String get_name() {
		return title;
	}

	@Override
	public double get_price() {
		return price * discount;
	}
	
	@Override
	public String get_info()
	{
		String result = "";
		result += "Title: " + get_name() + " | ";
		result += "Director: " + get_director() + " | ";
		result += "Price: " + get_price() + " | ";
		result += "Year: " + get_year() + " | ";
		result += "DvdCode: " + get_dvdcode();
		//returns all information about dvd
		return result;
	}
	
	public int get_year()
	{
		return year;
	}
	
	public int get_dvdcode()
	{
		return dvdcode;
	}

	public String get_director()
	{
		return director;
	}
	
	public double get_discount()
	{
		return discount;
	}

	@Override
	public int compareTo(Dvd other) {
		if (this.price > other.price)
		{
			return 1;
		}
		else if(this.price == other.price)
		{
			return 0;
		}
		else return -1;
	}
}
