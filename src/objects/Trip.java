package objects;

import java.util.*;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

public class Trip{
	public Hotel hotel;
	public Transport transportGo;
	public Transport transportBack;
	public List<Activities> activities = new ArrayList<Activities>();

	public Date dateBegin;
	public Date dateEnd;
	public int price;



	public Trip(String dateBegin, String dateEnd, int price){
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		try {
			this.dateBegin = ft.parse(dateBegin);
			this.dateEnd = ft.parse(dateEnd);
		} catch (ParseException e){
			System.out.println("Unparsable using "+ ft);
		}

		this.price = price;

	}

	public void setHotel(Hotel hotel){
		this.hotel = hotel;
	}

	public void setTransportGo(Transport transport){
		this.transportGo = transport;
	}
	
	public void setTransportBack(Transport transport){
		this.transportBack = transport;
	}

	public void addActivities(Activities activities){
		this.activities.add(activities);
	}
	
	public void increasePrice(int val){
		price += val;
	}
}