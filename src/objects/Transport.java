import java.util.*;
import java.text.*;

public class Transport(){
	public String name;
	public String destination;
	public Date dateBegin;
	public Date dateEnd;
	public int price;
	public int range;



	public Transport(String name, String destination, String dateBegin, String dateEnd, int range, int price = 0){
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
		this.range = range;

	}
}