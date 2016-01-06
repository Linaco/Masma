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

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		
		addBehaviour(new WindowRefresh(this,1000));
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
		
		
	}
}