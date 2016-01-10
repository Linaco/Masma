package agents;

/**
 *
 * @author Linaco
 * 
 * Agent in charge of activities
 */
import jade.core.AID;
import objects.*;

import java.io.Serializable;

import behaviour.*;
import util.AgentFrame;

public class ActivitiesAgent extends WorkingAgent {

	public AID providerAID = null;

	@Override
	public void setup() {
		Object[] args = this.getArguments();
		if (args != null) {
			windowsForm = (AgentFrame) args[0];
		}
		windowsForm.setTitle(this.getName());
		windowsForm.setVisible(true);
		
		//Add behavior to agent
		addBehaviour(new WindowRefresh(this,1000));
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));		
	}
	
	//Search object regarding the request
	@Override
	public Serializable search(Request request){
		
		return Activities.getListActivities(request.city,request.dateBegin,request.dateEnd,request.priceMax);
		
	}
}