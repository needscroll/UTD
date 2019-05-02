public class Dvd extends CatalogItem{

	private String title = "";
	private double price = 0;
	private int year = 0;
	private int dvdcode = 0;
	private String director = "";
	
	public Dvd()
	{
		
	}
	
	public Dvd(String title, double price, int year, int dvdcode, String director)
	{
		this.title = title;
		this.price = price;
		this.year = year;
		this.dvdcode = dvdcode;
		this.director = director;
	}
	
	@Override
	public String get_name() {
		return title;
	}

	@Override
	public double get_price() {
		return price;
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
}
