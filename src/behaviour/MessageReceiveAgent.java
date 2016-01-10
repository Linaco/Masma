package behaviour;
/**
 * Main behavior to receive message from personnalAgent
 * Behavior for all agents
 */
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import objects.*;
import util.GlobalCounter;

import java.io.*;
import agents.WorkingAgent;

public class MessageReceiveAgent extends CyclicBehaviour
{
    private WorkingAgent myAgent;

    public MessageReceiveAgent(WorkingAgent a)
    {
        super(a);
        myAgent = a;
    }

    @Override 
    public void action()
    {
        ACLMessage message = null;

        message = myAgent.receive();

        if (message != null)
        {
        	try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //String s = message.getContent() + " from " + message.getSender().getLocalName();
            //myAgent.windowsForm.AddTextLine(s);
            
            
        	AID senderAID = message.getSender();
            String senderName = senderAID.getLocalName();
            
            GlobalCounter.Increment();
            String stringToDisplayReceive = GlobalCounter.Get() + " " + "Received from " + senderName + " message: ";
            
            GlobalCounter.Increment();
            String stringToDisplay = GlobalCounter.Get() + "Replying to " + senderName;
            ACLMessage reply;

            switch (message.getPerformative())
            {       	
                case ACLMessage.CFP:
                    //Personnal agent call for a proposition of service
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[CFP]");
                	System.out.println("CFP");
                	
                	//Answer
                    search(stringToDisplay, message);

                    break;

                case ACLMessage.ACCEPT_PROPOSAL:
                    //Receive agreement about the proposition
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[ACCEPT_PROPOSAL]");
                	
                    stringToDisplay = "Shutting down";
                    myAgent.windowsForm.AddTextLine(stringToDisplay);
                    //shutdown
                	myAgent.doDelete();
                	break;
                	
                case ACLMessage.REJECT_PROPOSAL:
	                //Personnal agent refuses the proposition
	            	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[REJECT_PROPOSAL]");
	                //agent has to try again
	            	
	            	//Ask to search again
	            	search(stringToDisplay, message);
	            	
	            	
	            	
	            	
	            	//Shutting down
	            	/*stringToDisplay = "Shutting down";
	                myAgent.windowsForm.AddTextLine(stringToDisplay);
	                //shutdown
	            	myAgent.doDelete();*/
	            	break;

                	
                case ACLMessage.AGREE:
                	//Agree the request
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[AGREE]");
                	break;
                
                case ACLMessage.REFUSE:
                	//Refuse the request
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[REFUSE]");
                	//No possibilty, send failure
                	reply = new ACLMessage(ACLMessage.FAILURE);
			        stringToDisplay += " with message: [FAILURE]";
					reply.addReceiver(senderAID);

					myAgent.send(reply);
		                    
		            myAgent.windowsForm.AddTextLine(stringToDisplay);
                	
                	break;

                default:
                    break;
            }
        }
        else
        {
            /* very important: without this the behaviour cycles infinitely,
            not allowing for other behaviours to execute
            */
            block();
        }
    }
    
    public void search(String stringToDisplay,ACLMessage message){
    	Object temp = null;
    	AID senderAID = message.getSender();
        ACLMessage reply;
        
		Request request;
		try {
			request = (Request) message.getContentObject();
			//Object which will be returned to personnalAgent (data regarding the the request)
			temp = myAgent.search(request);
			
			if (temp != (Serializable) null){
				//object isn't null -> we find something relevent
				reply = new ACLMessage(ACLMessage.PROPOSE);
				stringToDisplay += " with message: [PROPOSE]";
			} else {
				//Object is null -> we need to find another date or return a null object to say that nothing is found
   
				if(myAgent.getLocalName().equals("Transport") || myAgent.getLocalName().equals("Hotel")){
					//if agent is transport or hotel, we can find a new date
					reply = new ACLMessage(ACLMessage.REQUEST);
					stringToDisplay += " with message: [REQUEST]";
					temp = myAgent.getNewDate(request);
	   
				} else {
					//if not : it's a fail
					reply = new ACLMessage(ACLMessage.FAILURE);
					stringToDisplay += " with message: [FAILURE]";
				}
			}
			
			//put object to the reply to be send
			reply.setContentObject((Serializable) temp);
			reply.addReceiver(senderAID);

			 myAgent.send(reply);
                
             myAgent.windowsForm.AddTextLine(stringToDisplay);
		} catch (UnreadableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}