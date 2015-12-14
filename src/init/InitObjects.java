package init;

import objects.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public static class InitObjects(){

	String path = "";

	public static void InitHotel(){

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try{
			br = new BufferedReader(new FileReader(csvFile + "Hotel.csv"));

			while( (line = br.readLine()) != null){
				String[] hotel = line.split(cvsSplitBy);

				new Hotel(hotel[0], hotel[1], hotel[2], hotel[3], hotel[4], hotel[5], hotel[6], hotel[7]);
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

			while( (line = br.readLine()) != null){
				String[] transport = line.split(cvsSplitBy);

				new Transport(transport[0], transport[1], transport[2], transport[3], transport[4], transport[5]);
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
		
	}
}