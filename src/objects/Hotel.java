package objects;

import java.util.*;
import java.io.Serializable;
import java.text.*;

//Hotel object
public class Hotel implements Serializable{

	public static List<Hotel> hotels = new ArrayList<Hotel>(); //static list = dateBase
	public static int lastIndex = 0; //the search will take the first hotel to go. In case the personnal agent refuse, the search will search since this index

	public String name;
	public String city;
	public Date dateBegin;
	public Date dateEnd;
	public int price;
	public int numberOfBeds;
	public int numberOfRooms;
	public int range;
	
	private double pourcentageWE = 1.3; //To increase the price during the week end
	
	//Create an object and add it to the datebase
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
	
	//Create an object without adding it to the dataBase -> usefull to manipulate the object
	public Hotel(String name, String city, Date dateBegin, Date dateEnd){
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;

		this.name = name;
		this.price = -1;
		this.numberOfRooms = -1;
		this.numberOfBeds = -1;
		this.range = -1;
		this.city = city;
	}
	
	//Print the object
	public String toString(){
		return name + " : from" + price + "€/night. " + range + "* [" + city + "] " + dateBegin + " to " + dateEnd;
	}
	
	//Search the hotel regarding the given value
	public static Hotel getHotel(String city, int priceBase, Date dateBe, Date dateEn, int range, int numbPpl){
		//lastIndex in case of previous proposal isn't accepted
		Hotel lastHotel = null;
		int price = priceBase;
		
		//for(int i = lastIndex; i < hotels.size(); i++){
		for(int i = lastIndex; i < hotels.size(); i++){
			if( hotels.get(i).city.equals(city) && hotels.get(i).availabilty(dateBe, dateEn) && hotels.get(i).price(dateBe, dateEn, numbPpl) <= price && hotels.get(i).range >= range){
				//lastIndex = i + 1;
				//price = hotels.get(i).price(dateBe, dateEn, numbPpl);
				lastHotel = hotels.get(i);
				return lastHotel;
			}
		}
		return lastHotel;
	}

	//compute the price of the stay regarding the number of day stayed and the days stayed
	public int price(Date dateBe, Date dateEn, int nmbPpl) {
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
		
		return (result * nmbPpl);
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