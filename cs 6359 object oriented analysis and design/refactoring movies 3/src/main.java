
public class main {
	
	public static void main(String[] args)
	{
		Customer customer = new Customer("the customer", 19);
		Movie movie1 = new ChildrensMovie("movie1");
		Movie movie2 = new NewMovie("movie2");
		Movie movie3 = new RegularMovie("movie3");
		
		Rental rental1 = new Rental(movie1, 9);
		Rental rental2 = new Rental(movie2, 5);
		Rental rental3 = new Rental(movie3, 3);
		
		customer.addRental(rental1);
		customer.addRental(rental2);
		customer.addRental(rental3);
		customer.addRental(rental3);
		customer.addRental(rental3);
		customer.addRental(rental3);
        customer.addRental(rental2);
        customer.addRental(rental2);



        System.out.println(customer.getReceipt());
		
	}
	
	
}
