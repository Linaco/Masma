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
	
	@Override
	public void takeDown(){
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

	public Object getNewDate(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
}
