import java.util.*;
import java.text.*;

public class Activities(){
	public String name;
	public String description;
	public Date dateBegin;
	public Date dateEnd;
	public int price;



	public Activities(String name, String description, String dateBegin, String dateEnd, int price = 0){
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

	}
}