package objects;

import java.util.Date;

public class Request {
	public String city;
	public Date dateBegin;
	public Date dateEnd;
	public String flexible;
	public int nbrRooms;
	public int nbrPpl;
	public int nbrStars;
	public int priceMax;
	public int priceMin;
	public String transport;
	
	public String toString(){
		return city + " " + dateBegin + " " + dateEnd + " " + flexible + " " + nbrRooms + " " + nbrPpl + " " + nbrStars + " " + priceMax + " " + priceMin + " " + transport;
	}

}
