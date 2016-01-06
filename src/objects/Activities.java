package objects;

import java.util.*;
import java.text.*;

public class Activities {
	
	public static List<Activities> activities = new ArrayList<Activities>();
	
	public String name;
	public String description;
	public Date dateBegin;
	public Date dateEnd;
	public int price = 0;
	public String city;



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
	
	public String toString(){
		return name + " : " + description + ". " + price +"€ [" + city + "] " + dateBegin + " to " + dateEnd;
	}
	
	public static List<Activities> getListActivities(int price){
		List<Activities> map = new ArrayList<Activities>();
		
		for(int i = 0; i < activities.size(); i++){
			if(activities.get(i).price <= price ){
				map.add(activities.get(i));
			}
		}
		
		return sortList(map);
	}

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
}