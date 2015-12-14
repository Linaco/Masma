/*
 * Personnal Agent
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

public class PersonnalAgent extends Agent {

	public AgentFrame windowsForm;
	public AID providerAID = null;

	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

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

	private class BuyerRegister extends OneShotBehaviour {
		private BuyerAgent myAgent;

		public BuyerRegister(BuyerAgent a) {
			super(a);
			myAgent = a;
		}

		@Override
		public void action() {
			// inform managerAgent that I want to register for the auction

			ACLMessage m = new ACLMessage(ACLMessage.INFORM);

			AID receiverAID = new AID("AgentManager", AID.ISLOCALNAME);
			m.addReceiver(receiverAID);
			m.setContent("registering");
			myAgent.send(m);

			myAgent.windowsForm.AddTextLine("Registering for auction ...");
		}
	}

	private class BuyerReceive extends CyclicBehaviour {

		private BuyerAgent myAgent;

		public BuyerReceive(BuyerAgent a) {
			super(a);
			myAgent = a;
		}

		@Override
		public void action() {
			ACLMessage m = myAgent.receive();

			if (m != null) {
				String received = m.getContent();

				if (received.contains("registered")) {
					// manager registered me for the auction
					myAgent.windowsForm.AddTextLine("Successfully registered for auction");
				}

				if (received.contains("Product to sell")) {
					// received new call from managerAgent

					// the highest current price is:
					String productName = received.substring(18);
					myAgent.windowsForm.AddTextLine("Received product pool: " + productName);

					// probability to make offer decreases with each price
					// increase
					myAgent.probabilityToBid *= myAgent.probabilityDecreaseFactor;

					ACLMessage toSend = new ACLMessage(ACLMessage.INFORM);
					toSend.addReceiver(m.getSender());
					// use agent name to generate unique seed for random numbers
					int seed = 1573 * Integer.parseInt(myAgent.getLocalName().substring(5));
					// decide if to send new offer or retreat
					if (myAgent.probabilityToBid > new Random(seed).nextDouble()) {
						// make an offer which is higher than the current price
						seed = 1723 * Integer.parseInt(myAgent.getLocalName().substring(5));
						int bidPrice = randInt(Products.findProductPrice(productName)/10, Products.findProductPrice(productName));
						toSend.setContent("offer" + bidPrice);
						myAgent.windowsForm.AddTextLine("Sent new offer: " + bidPrice);
					} else {
						// price is too high, retreat from auction
						toSend.setContent("retreat");
						myAgent.windowsForm.AddTextLine("Retreated from auction.");
					}
					myAgent.send(toSend);
				}
			} else {
				block();
			}
		}
	}
}
