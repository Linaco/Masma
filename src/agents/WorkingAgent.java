package agents;

import java.io.Serializable;

import behaviour.*;
import jade.core.Agent;
import objects.Request;
import util.AgentFrame;
import util.Frame;

public abstract class WorkingAgent extends Agent {
	public Frame windowsForm;
	
	//public abstract void AddTextLine(String s); 
	
	public Serializable search(Request request){
		return 0;
	}
	
	public void onSetup(){
		
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
		
	}
}
