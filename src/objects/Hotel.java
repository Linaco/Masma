package objects;

import java.util.*;
import java.io.Serializable;
import java.text.*;

public class Hotel implements Serializable{

	public static List<Hotel> hotels = new ArrayList<Hotel>();
	public static int lastIndex = 0;

	public String name;
	public String city;
	public Date dateBegin;
	public Date dateEnd;
	public int price;
	public int numberOfBeds;
	public int numberOfRooms;
	public int range;
	
	private double pourcentageWE = 1.3;
	
	
	public Hotel(String name, String city, String dateBegin, String dateEnd, int numberOfBeds, int numberOfRooms, int range, int price){
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		try {
			this.dateBegin = ft.parse(dateBegin);
			this.dateEnd = ft.parse(dateEnd);
		} catch (ParseException e){
			System.out.println("Unparsable using "+ ft);
		}

		this.name = name;
		this.price = price;
		this.numberOfRooms = numberOfRooms;
		this.numberOfBeds = numberOfBeds;
		this.range = range;
		this.city = city;

		//Add it to the static map of Hotel
		hotels.add(this);

	}
	
	public String toString(){
		return name + " : " + price + "€. " + range + "* [" + city + "] " + dateBegin + " to " + dateEnd;
	}
	
	public static Hotel getHotel(String city, int priceBase, Date dateBe, Date dateEn, int range){
		//lastIndex in case of previous proposal isn't accepted
		Hotel lastHotel = null;
		int price = priceBase;
		
		//for(int i = lastIndex; i < hotels.size(); i++){
		for(int i = 0; i < hotels.size(); i++){
			if(hotels.get(i).price(dateBe, dateEn) <= price){
				//lastIndex = i + 1;
				price = hotels.get(i).price(dateBe, dateEn);
				lastHotel = hotels.get(i);
			}
		}
		return lastHotel;
	}

	//compute the price of the stay regarding the number of day stayed and the days stayed
	private int price(Date dateBe, Date dateEn) {
		int result = 0;
		
		while( !dateBe.after(dateEn)){
			if(dateBe.getDay() == 6 || dateBe.getDay() == 7){
				result += (int) (price * pourcentageWE);
			} else {
				result += price;
			}
			
			//Add one to the date
			dateBe.setDate(dateBe.getDate() + 1);
		}
		
		return result;
	}
	
	//number of days between two date
	private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	//return boolean regarding the availability of the hotel
	private boolean availabilty(Date d1, Date d2){
		if( !(d1.after(this.dateBegin) && d1.before(this.dateEnd))){
			return false;
		}
		
		if ( !(d2.after(this.dateBegin) && d2.before(this.dateEnd))){
			return false;
		}
		
		return true;
	}
}