import java.util.*;
import java.text.*;

public class Hotel(){
	public String name;
	public String city;
	public Date dateBegin;
	public Date dateEnd;
	public int price;
	public int numberOfBeds;
	public int numberOfRooms;
	public int range;



	public Hotel(String name, String city, String dateBegin, String dateEnd, int numberOfBeds, int numberOfRooms, int range, int price = 0){
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		try {
			this.dateBegin = ft.parse(dateBegin);
			this.dateEnd = ft.parse(dateEnd);
		} catch (ParseException e){
			System.out.println("Unparsable using "+ ft);
		}

		this.name = name;
		this.description = description;
		this.price = price;
		this.numberOfRooms = numberOfRooms;
		this.numberOfBeds = numberOfBeds;
		this.range = range;

	}
}