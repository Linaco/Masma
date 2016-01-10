/*
 * Personnal Agent -> manage the software to find a trip regarding the request
 * Manage all communication between agent
 */
package agents;

/**
 *
 * @author Linaco
 */
import jade.core.AID;
import jade.core.Agent;
import objects.Request;
import objects.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import behaviour.*;
import util.*;

public class PersonnalAgent extends WorkingAgent {

	public jade.wrapper.AgentContainer mainContainer;
	public AID providerAID = null;
	
	public Request request = new Request();
	
	//Manage the state of the agent regarding the monitoring of research
	public int state = 0; //0 - none / 1 - transport / / 3 - transport + activities + hotel

	public Trip trip = new Trip();

	@Override
	public void setup() {
		windowsForm = new PersonnalAgentFrame(this);
		Object[] args = this.getArguments();
		if (args != null) {
			//windowsForm = (PersonnalAgentFrame) args[0];
			mainContainer = (jade.wrapper.AgentContainer) args[1];
		}
		windowsForm.setTitle(this.getName());
		windowsForm.setVisible(true);
		
		//add behavior
		addBehaviour(new WindowRefresh(this,1000));
		addBehaviour(new MessageReceivePersonnalAgent(this));

	}

	//first method called when a request is sent to the personnalAgent
	//Then will create and start all the agent of the software
	public void init(String text) {
		//reset state -> new request
		state = 0;
		getRequest(text);
		addBehaviour(new Initiate(this));
		
	}

	//Tokenise the string to populate a request object
	private void getRequest(String text) {
		String[] requestString = text.split(",");
		
		request.city = requestString[0];
		request.flexible = requestString[3];
		request.nbrRooms = Integer.parseInt(requestString[4]);
		request.nbrPpl = Integer.parseInt(requestString[5]);
		request.nbrStars = Integer.parseInt(requestString[6]);
		request.priceMin = Integer.parseInt(requestString[7]);
		request.priceMax = Integer.parseInt(requestString[8]);
		request.transport = requestString[9];
		
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		try {
			request.dateBegin = ft.parse(requestString[1]);
			request.dateEnd = ft.parse(requestString[2]);
		} catch (ParseException e){
			System.out.println("Unparsable using "+ ft);
		}
		
		//System.out.println(request);
	}
	
	//Check the state of the research
	public void checkTrip(){
		if(state == 3){
			//print trip
			windowsForm.AddTextLine(trip.toString());
		}
	}
}