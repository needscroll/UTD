public class AudioBook extends Book{
	
	private String title = "";
	private double price = 0;
	private String author = "";
	private int isbn = 0;
	private double runtime = 0;
	private double discount = .5;
	
	public AudioBook()
	{
		
	}
	
	public AudioBook(String title, double price, String author, int isbn, double runtime, double discount)
	{
		this.title = title;
		this.price = price;
		this.author = author;
		this.isbn = isbn;
		this.runtime = runtime;
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
		result += "ISBN: " + get_isbn() + " | ";
		result += "Running Time: " + get_runtime();
		//returns all information about the audiobook
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
	
	public double get_runtime()
	{
		return runtime;
	}
	
	public double get_discount()
	{
		return discount;
	}
}
