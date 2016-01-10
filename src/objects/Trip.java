package objects;

import java.util.*;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

//Trip object to be populated during the search
public class Trip{
	public Hotel hotel;
	public Transport transportGo;
	public Transport transportBack;
	public List<Activities> activities = new ArrayList<Activities>();

	//public Date dateBegin;
	//public Date dateEnd;
	public int price;
	
	public boolean corrupted = false;

	public Trip(){
		
	}

	/*public Trip(String dateBegin, String dateEnd, int price){
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		try {
			this.dateBegin = ft.parse(dateBegin);
			this.dateEnd = ft.parse(dateEnd);
		} catch (ParseException e){
			System.out.println("Unparsable using "+ ft);
		}

		this.price = price;

	}*/
	
	//Override method to print the whole trip
	@Override
	public String toString(){
		String line = "Trip to " + hotel.city + " from " + hotel.dateBegin + " to " + hotel.dateEnd;
		String go = "Going by " + transportGo.name + " on " + hotel.dateBegin + " : " + transportGo.date + ". for " + transportGo.price + "€.";
		String hotl = "Staying at " + hotel.name + " for " + hotel.price + "€ per night and per person.";
		String back = "Coming back by " + transportBack.name + " on " + hotel.dateEnd + " : " + transportGo.date + ". for " + transportGo.price + "€.";
		String activity = "-----------Activities :\n";
		
		for(int i = 0; i < activities.size(); i++){
			activity += activities.get(i).toString() + "\n";
		}
		
		String br = "\n";
		return "-------------------\n-------Trip--------\n-------------------\n" + line + br + go + br + hotl + br + back + br + activity + "\n\nTotal price :" + price + "€";
	}

	
	//Get & Set
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
	
	public void setCorrupted(boolean bol){
		corrupted = bol;
	}
}