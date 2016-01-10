package agents;
/**
 * @author Linaco
 * 
 * MotherAgent class of all agent to manage heritance
 */

import java.io.Serializable;

import behaviour.*;
import jade.core.Agent;
import objects.Request;
import util.AgentFrame;
import util.Frame;

public abstract class WorkingAgent extends Agent {
	public Frame windowsForm;
	
	//public abstract void AddTextLine(String s); 
	
	//Main method to be defined on other child agent
	public Serializable search(Request request){
		return 0;
	}
	
	public void onSetup(){
		
		addBehaviour(new MessageReceiveAgent(this));
		addBehaviour(new InitiatorSend(this));
		
	}
	
	//When agent will shutdown -> close the window
	@Override
	public void takeDown(){
		//in case behavior of windowRefresh wasn't active before ending the agent
		windowsForm.repaint();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		windowsForm.setVisible(false); //you can't see me!
		windowsForm.dispose();;
	}

	//Method could be abstract but i should have implement it in personnalAgent.
	public Object getNewDate(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
}
