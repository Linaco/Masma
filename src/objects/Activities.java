package objects;

import java.util.*;
import java.io.Serializable;
import java.text.*;

//Activities object
public class Activities implements Serializable {
	
	public static List<Activities> activities = new ArrayList<Activities>(); //DataBase for activities
	
	public String name;
	public String description;
	public Date dateBegin;
	public Date dateEnd;
	public int price = 0;
	public String city;


	//Create an object and add it to the datebase
	public Activities(String city, String name, String description, String dateBegin, String dateEnd, int price){
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
		this.city = city;
		
		activities.add(this);
	}
	
	//Create an object without adding it to the dataBase -> usefull to manipulate the object
	public Activities(String city, String name, String description, Date dateBegin, Date dateEnd, int price){
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.name = name;
		this.description = description;
		this.price = price;
		this.city = city;
	}
	
	//print object
	public String toString(){
		return name + " : " + description + ". " + price +"€ [" + city + "] " + dateBegin + " to " + dateEnd;
	}
	
	//Search list of activities and return an array
	public static Activities[] getListActivities(String city, Date dateBegin, Date dateEnd, int price){
		List<Activities> map = new ArrayList<Activities>();
		
		for(int i = 0; i < activities.size(); i++){
			if(activities.get(i).city.equals(city) && activities.get(i).availabilty(dateBegin, dateEnd) && activities.get(i).price <= price ){
				map.add(activities.get(i));
			}
		}
		return toArray(map);
		//return toArray(sortList(map));
	}

	//Transform the list into an array
	private static Activities[] toArray(List<Activities> sortList) {
		Activities[] activities2 = new Activities[sortList.size()];
		//activities2[0] = new Activities("","Not found","N/A",new Date(), new Date(), -1);
		
		for(int i = 0; i < sortList.size(); i++){
			activities2[i] = sortList.get(i);
				
		}
		return activities2;
	}

	//Sort the list by increasing price
	private static List<Activities> sortList(List<Activities> map) {
		List<Activities> sortedMap = new ArrayList<Activities>();
		
		int actualPrice = 999999;
		int nextPrice = 0;
		System.out.println("Sorting the list");
		while(!map.isEmpty()){
			for(int i = 0; i < map.size(); i++){
				if(map.get(i).price == actualPrice ){
					sortedMap.add(map.get(i));
					
					map.remove(i);
					
				} else {
					if(map.get(i).price < nextPrice ){
						nextPrice = map.get(i).price;
					}
				}
			}
		}
		
		return sortedMap;		
		
	}
	
	//return boolean regarding the availability of the activity
	//d1 & d2 date of begin and end of trip
	private boolean availabilty(Date d1, Date d2){
		if( d2.after(this.dateBegin) && d1.before(this.dateEnd)){
			return true;
		}
		
		return false;
	}
}