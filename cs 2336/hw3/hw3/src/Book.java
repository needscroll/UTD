public class Book extends CatalogItem{
	
	private String title = "";
	private double price = 0;
	private String author = "";
	private int isbn = 0;
	
	public Book()
	{
		
	}
	
	public Book(String title, double price, String author, int isbn)
	{
		this.title = title;
		this.price = price;
		this.author = author;
		this.isbn = isbn;
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
}
