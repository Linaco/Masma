
package behaviour;

import agents.PersonnalAgent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import util.GlobalCounter;

    
public class MessageReceivePersonnalAgent extends CyclicBehaviour
{
    private PersonnalAgent myAgent;
    
    public MessageReceivePersonnalAgent(PersonnalAgent a) 
    {
        super(a);
        myAgent = a; 
    }
    
    @Override
    public void action()
    {
        ACLMessage message = myAgent.receive();
        
        if(message != null)
        {
        	try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
            /*String s = message.getContent() + " from " + message.getSender().getLocalName();
            myAgent.frame.AddTextLine(s);
            
            ACLMessage answer = new ACLMessage();
            answer.addReceiver(message.getSender());
            answer.setContent("(Message :id 1)");
            answer.setConversationId("ID1"); // "ID1" will have to be matched by any agents receiving the message
            myAgent.send(answer);
            
            answer.setContent("(Message :id 2)");
            answer.setConversationId("ID2");
            myAgent.send(answer);*/

            AID senderAID = message.getSender();
            String senderName = senderAID.getLocalName();
            String stringToDisplay = "";

            switch (message.getPerformative())
            {
                case ACLMessage.INFORM:
                    //Agent says it exist

                    GlobalCounter.Increment();
                    
                    stringToDisplay += GlobalCounter.Get() + " " + "Received from " + senderName + " message: [INFORM]";
                    myAgent.windowsForm.AddTextLine(stringToDisplay);

                    stringToDisplay = "Replying to " + senderName + " with message: [PROPOSE]";

                    ACLMessage reply = new ACLMessage(ACLMessage.PROPOSE);

                    //decide if to accept the proposal
                    /*if (Probability.Validate(myAgent.probabilityToAcceptProposal) == true)
                    {
                        reply = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                        stringToDisplay += "[ACCEPT_PROPOSAL]";
                    }
                    else
                    {
                        reply = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                        stringToDisplay += "[REJECT_PROPOSAL]";
                    }*/

                    reply.addReceiver(senderAID);
                    myAgent.send(reply);

                    myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);

                    break;

                case ACLMessage.PROPOSE:
                    //Receive proposition from agent about a price

                case ACLMessage.FAILURE:
                    //Agent couldn't find something with give properties
                    


                    //Send message to say "ok, shutdown"
                    ACLMessage toSend = new ACLMessage(ACLMessage.CONFIRM);

                    stringToDisplay = "Sending to ";

                    toSend.addReceiver(senderAID);
                    myAgent.send(toSend);

                    stringToDisplay += senderAID.getLocalName() + " message: [CONFIRM]";

                    GlobalCounter.Increment();

                    myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);
                    
                case ACLMessage.REQUEST:
                	//Ask to change the date


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