public class Magazine implements IDedObject{

	private int magazineID = 0;
	private	String magazineName = "";
	private String publisherName = "";
	
	@Override
	public int get_id() {
		return magazineID;
	}

	@Override
	public void printID() {
		System.out.println("Magazine id is: " + magazineID);
		System.out.println("magazine name is: " + magazineName);
		System.out.println("publisher name is:" + publisherName );
	}
	
	public Magazine(int id, String mag_name, String pub_name)
	{
		magazineID = id;
		magazineName = mag_name;
		publisherName = pub_name;
	}

}
