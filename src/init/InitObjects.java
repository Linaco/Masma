package init;
/**
 * @author Linaco
 * Populate the dataBase.
 * Hotel, Transport, Activities with csv files
 */
import objects.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InitObjects{

	static String csvFile = "/home/leo/Documents/Masma/TripCompany/src/objects/"; //adress of the directory with csv file for data

	public static void InitHotel(){

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try{
			br = new BufferedReader(new FileReader(csvFile + "Hotel.csv"));
			br.readLine();

			while( (line = br.readLine()) != null){
				String[] hotel = line.split(cvsSplitBy);

				new Hotel(hotel[0], hotel[1], hotel[2], hotel[3], Integer.parseInt(hotel[4]), Integer.parseInt(hotel[5]), Integer.parseInt(hotel[6]), Integer.parseInt(hotel[7]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null){
				try {
					br.close();
				} catch ( IOException e){
					e.printStackTrace();
				}
			}
		}

	}

	public static void InitTransport(){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try{
			br = new BufferedReader(new FileReader(csvFile + "Transport.csv"));
			br.readLine();

			while( (line = br.readLine()) != null){
				String[] transport = line.split(cvsSplitBy);

				new Transport(transport[0], transport[1], transport[2], transport[3], Integer.parseInt(transport[4]), Integer.parseInt(transport[5]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null){
				try {
					br.close();
				} catch ( IOException e){
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void InitActivities(){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try{
			br = new BufferedReader(new FileReader(csvFile + "Activities.csv"));
			br.readLine();

			while( (line = br.readLine()) != null){
				String[] transport = line.split(cvsSplitBy);
				
				new Activities(transport[0], transport[1], transport[2], transport[3], transport[4], Integer.parseInt(transport[5]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null){
				try {
					br.close();
				} catch ( IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	//To print everything to check if it works well
	public static void printAll(){
		for(int i=0; i < Activities.activities.size(); i++){
			System.out.println(Activities.activities.get(i));
		}
		System.out.println("----------------------------------------\n\n");
		for(int i=0; i < Hotel.hotels.size(); i++){
			System.out.println(Hotel.hotels.get(i));
		}
		System.out.println("----------------------------------------\n\n");
		for(int i=0; i < Transport.transports.size(); i++){
			System.out.println(Transport.transports.get(i));
		}
	}
}