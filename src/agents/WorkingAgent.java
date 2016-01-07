package agents;

import behaviour.*;
import jade.core.Agent;
import util.AgentFrame;
import util.Frame;

public abstract class WorkingAgent extends Agent {
	public Frame windowsForm;
	
	//public abstract void AddTextLine(String s); 
	
	public void onSetup(){
		
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
		
	}
}
