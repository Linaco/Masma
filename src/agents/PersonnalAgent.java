/*
 * Personnal Agent
 */
package agents;

/**
 *
 * @author Linaco
 */
import jade.core.AID;
import jade.core.Agent;

import behaviour.*;
import util.AgentFrame;

public class PersonnalAgent extends WorkingAgent {

	public jade.wrapper.AgentContainer mainContainer;
	public AID providerAID = null;

	private Agent hotel;
	private Agent transport;
	private Agent activities;

	@Override
	public void setup() {
		Object[] args = this.getArguments();
		if (args != null) {
			windowsForm = (AgentFrame) args[0];
			mainContainer = (jade.wrapper.AgentContainer) args[1];
		}
		windowsForm.setTitle(this.getName());
		windowsForm.setVisible(true);
		
		addBehaviour(new WindowRefresh(this,1000));
		addBehaviour(new MessageReceivePersonnalAgent(this));

		//addBehaviour(new Initiate(this));

	}
}