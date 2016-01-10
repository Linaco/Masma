package objects;

import java.util.*;
import java.io.Serializable;
import java.text.*;

public class Transport implements Serializable{
	
	public static List<Transport> transports = new ArrayList<Transport>();
	
	public String name;
	public String from;
	public String destination;
	public Date date;
	public int price;
	public int range;
	
	public static int indexGo;
	public static int indexBack;

	//Implement an object and add it to the map (dataBase)
	public Transport(String name, String from, String destination, String date, int range, int price){
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		try {
			this.date = ft.parse(date);
		} catch (ParseException e){
			System.out.println("Unparsable using "+ ft);
		}

		this.name = name;
		this.destination = destination;
		this.from = from;
		this.price = price;
		this.range = range;
		
		transports.add(this);

	}
	
	//Create a simple object to manipulate it without adding it to the dataBase
	public Transport(String name, String from, String destination, Date date, int range, int price) {
		this.date = date;
		this.name = name;
		this.destination = destination;
		this.from = from;
		this.price = price;
		this.range = range;
	}

	//TO print the object
	public String toString(){
		return name + " : " + price + "€. " + " From [" + from + " to " + destination + "] " + date;
	}
	
	
	//to search a go/come back transport for the trip
	public static Transport[] getTransport(String from, String city, Date dateBegin, Date dateEnd, String mean, boolean flex){
		Transport[] transport = new Transport[2];
		int lastPrice = 99999;
		
		//search the go
		for (int i = indexGo; i < transports.size() ; i++){
			if(transports.get(i).from.equals(from) && transports.get(i).destination.equals(city) && transports.get(i).price <= lastPrice && transports.get(i).name.equals(mean) && transports.get(i).date.equals(dateBegin)){
				//lastPrice = transports.get(i).price;
				transport[0] = transports.get(i);
				i = transports.size();
			}
		}
		
		//Useless now -> we don't modify the price to simulate a new research in case of the PersonnalAgent refuse the proposal
		lastPrice = 99999;
		
		//the come back
		for (int i = indexBack; i < transports.size() ; i++){
			if(transports.get(i).from.equals(city) && transports.get(i).destination.equals(from) && transports.get(i).price <= lastPrice  && transports.get(i).name.equals(mean) && transports.get(i).date.equals(dateEnd)){
				//lastPrice = transports.get(i).price;
				transport[1] = transports.get(i);
				i = transports.size();
			}
		}
		
		//If one travel isn't find, we return null -> corrupted transport
		if(transport[0] == null || transport[1] == null) {
			return null;
		}
		
		return transport;
	}
	
	//Will give the best date if no transport exist
	public static Date[] getNewDate(String from, String city, Date d1, Date d2, String mean){
		Date[] array = new Date[2];
		int go = 3;
		int back = 3;
		
		for (int i = 0; i < transports.size() ; i++){
			//Go
			if(transports.get(i).from.equals(from) && transports.get(i).destination.equals(city) && transports.get(i).name.equals(mean)){
				if(Math.abs(Util.daysBetween(d1, transports.get(i).date)) <= Math.abs(go) ){
					array[0] = transports.get(i).date;
					go = Util.daysBetween(d1, transports.get(i).date);	
				}
			}
			//Comme back
			if(transports.get(i).from.equals(city) && transports.get(i).destination.equals(from) && transports.get(i).name.equals(mean)){
				if(Math.abs(Util.daysBetween(d2, transports.get(i).date)) <= Math.abs(back) ){
					array[1] = transports.get(i).date;
					back = Util.daysBetween(d2, transports.get(i).date);	
				}
			}
		}
		
		return array;
	}
}