
package behaviour;

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
            myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[]");
            
            GlobalCounter.Increment();
            String stringToDisplay = GlobalCounter.Get() + "Replying to " + senderName;

            switch (message.getPerformative())
            {
                case ACLMessage.CFP:
                    //Personnal agent call for a proposition of service
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[CFP]");
                	System.out.println("CFP");

                    
                    
                    //Answer
                    ACLMessage reply;
                    Object temp = null;
                    
                    
					Request request;
					try {
						request = (Request) message.getContentObject();
						temp = myAgent.search(request);
						
						 if (temp != (Serializable) null){
		                    	reply = new ACLMessage(ACLMessage.PROPOSE);
		                    	stringToDisplay += " with message: [PROPOSE]";
		                   } else {
		                    	reply = new ACLMessage(ACLMessage.FAILURE);
		                    	stringToDisplay += " with message: [FAILURE]";
		                    }
						 reply.setContentObject((Serializable) temp);
						 reply.addReceiver(senderAID);

						 myAgent.send(reply);
		                    
		                 myAgent.windowsForm.AddTextLine(stringToDisplay);
					} catch (UnreadableException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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
                	
                	//Shutting down
                	stringToDisplay = "Shutting down";
                    myAgent.windowsForm.AddTextLine(stringToDisplay);
                    //shutdown
                	myAgent.doDelete();
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
}