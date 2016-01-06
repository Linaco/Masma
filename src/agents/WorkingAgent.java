package agents;

import behaviour.*;
import jade.core.Agent;
import util.AgentFrame;

public abstract class WorkingAgent extends Agent {
	public AgentFrame windowsForm;
	
	public void onSetup(){
		
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
		
	}
}
