/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author Linaco
 * Agent in charge of manipulate request to find a Hotel
 */
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import objects.Hotel;
import objects.Request;

import java.io.Serializable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import behaviour.InitiatorSend;
import behaviour.MessageReceiveAgent;
import behaviour.WindowRefresh;
import util.AgentFrame;

public class HotelAgent extends WorkingAgent {

	public AID providerAID = null;

	@Override
	public void setup() {
		Object[] args = this.getArguments();
		if (args != null) {
			windowsForm = (AgentFrame) args[0];
		}
		windowsForm.setTitle(this.getName());
		windowsForm.setVisible(true);
		
		//Add behavior
		addBehaviour(new WindowRefresh(this,1000));
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
	}
	
	//Search object regarding the request
	@Override
	public Serializable search(Request request){
		
		
		return Hotel.getHotel(request.city,request.priceMax,request.dateBegin,request.dateEnd,request.nbrStars, request.nbrPpl);
	}
}