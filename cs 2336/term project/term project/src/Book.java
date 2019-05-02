public class Book extends CatalogItem implements Comparable<Book>{
	
	private String title = "";
	private double price = 0;
	private String author = "";
	private int isbn = 0;
	private double discount = .9;
	
	public Book()
	{
		
	}
	
	public Book(String title, double price, String author, int isbn, double discount)
	{
		this.title = title;
		this.price = price;
		this.author = author;
		this.isbn = isbn;
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
		result += "Author: " + get_author() + " | ";
		result += "Price: " + get_price() + " | ";
		result += "ISBN: " + get_isbn();
		//returns all info about book
		return result;
	}
	
	public String get_author()
	{
		return author;
	}
	
	public int get_isbn()
	{
		return isbn;
	}
	
	public double get_discount()
	{
		return discount;
	}

	@Override
	public int compareTo(Book other) {
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
