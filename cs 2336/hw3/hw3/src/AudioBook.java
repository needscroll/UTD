public class AudioBook extends Book{
	
	private String title = "";
	private double price = 0;
	private String author = "";
	private int isbn = 0;
	private double runtime = 0;
	
	public AudioBook()
	{
		
	}
	
	public AudioBook(String title, double price, String author, int isbn, double runtime)
	{
		this.title = title;
		this.price = price;
		this.author = author;
		this.isbn = isbn;
		this.runtime = runtime;
	}
	
	@Override
	public String get_name() {
		return title;
	}

	@Override
	public double get_price() {
		return price * .9;
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

}
