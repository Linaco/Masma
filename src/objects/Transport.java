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
	
	public String toString(){
		return name + " : " + price + "€. " + " From [" + from + " to " + destination + "] " + date;
	}
	
	
	
	public static Transport getTransport(String from, String city, Date dateBegin, Date dateEnd, boolean flex){
		Transport transport = null;
		int lastPrice = 99999;
		
		
		for (int i = 0; i < transports.size() ; i++){
			if(transports.get(i).from == from && transports.get(i).destination == city && transports.get(i).price <= lastPrice){
				lastPrice = transports.get(i).price;
				transport = transports.get(i);
			}
		}
		
		lastPrice = 99999;
		
		for (int i = 0; i < transports.size() ; i++){
			if(transports.get(i).from == city && transports.get(i).destination == from && transports.get(i).price <= lastPrice){
				lastPrice = transports.get(i).price;
				transport = transports.get(i);
			}
		}
		
		return transport;
	}
}