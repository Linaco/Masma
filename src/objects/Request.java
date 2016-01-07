package objects;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
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
	static public Request request;
	
	public Request(){
		this.request = this;
	}
	
	public String toString(){
		return city + " " + dateBegin + " " + dateEnd + " " + flexible + " " + nbrRooms + " " + nbrPpl + " " + nbrStars + " " + priceMax + " " + priceMin + " " + transport;
	}
	
	public void substractTransport(int val){
		priceMax -= val;
	}

}
