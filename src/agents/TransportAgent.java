/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author Linaco
 */
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import objects.Request;
import objects.Transport;

import java.io.Serializable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.AgentFrame;
import behaviour.*;

public class TransportAgent extends WorkingAgent {

	
	public AID providerAID = null;

	@Override
	public void setup() {
		Object[] args = this.getArguments();
		if (args != null) {
			windowsForm = (AgentFrame) args[0];
		}
		windowsForm.setTitle(this.getName());
		windowsForm.setVisible(true);
		
		addBehaviour(new WindowRefresh(this,1000));
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
	}
	
	@Override
	public Serializable search(Request request){
		
		if(!request.transport.equals("private car")){
			System.out.println("Recherche d'un transport");
			return Transport.getTransport("Iasi",request.city,request.dateBegin,request.dateEnd, request.transport,false);
		}
		
		System.out.println("Private car");
		
		Transport[] array = {new Transport("Private car","Iasi",request.city,request.dateBegin,-1,0), new Transport("Private car",request.city,"Iasi",request.dateBegin,-1,0)};
		
		//System.out.println(array[1]);
		
		return array;
		
	}
}