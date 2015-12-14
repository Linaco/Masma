/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

/**
 *
 * @author lab
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

import util.AgentFrame;
import util.Products;
import util.YellowPages;

public class HotelAgent extends Agent {

	public AgentFrame windowsForm;
	public AID providerAID = null;

	@Override
	public void setup() {
		Object[] args = this.getArguments();
		if (args != null) {
			windowsForm = (AgentFrame) args[0];
		}

		windowsForm.setTitle(this.getName());
		windowsForm.setVisible(true);
		try {
			providerAID = YellowPages.FindService("AuctionService", this, 10);
		} catch (FIPAException ex) {
			Logger.getLogger(BuyerAgent.class.getName()).log(Level.SEVERE, null, ex);
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e);
		}

		if (providerAID == null) {
			windowsForm.AddTextLine("No auction provider found.");
		} else {
			windowsForm.AddTextLine("Found auction provider: " + providerAID.getLocalName());

			addBehaviour(new BuyerRegister(this));
			addBehaviour(new BuyerReceive(this));
		}
	}
}