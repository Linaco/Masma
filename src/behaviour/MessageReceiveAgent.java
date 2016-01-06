
package behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
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

        MessageTemplate pattern = MessageTemplate.MatchConversationId("ID1");
        message = myAgent.receive(pattern);

        if (message != null)
        {
            String s = message.getContent() + " from " + message.getSender().getLocalName();
            myAgent.windowsForm.AddTextLine(s);
            
            
            AID senderAID = message.getSender();
            String senderName = senderAID.getLocalName();
            String stringToDisplay = "";

            switch (message.getPerformative())
            {
                case ACLMessage.CFP:
                    //Personnal agent call for a proposition of service

                    GlobalCounter.Increment();
                    
                    stringToDisplay += GlobalCounter.Get() + " " + "Received from " + senderName + " message: [PROPOSE]";
                    myAgent.windowsForm.AddTextLine(stringToDisplay);

                    stringToDisplay = "Replying to " + senderName + " with message: ";

                    ACLMessage reply = null;

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

                    GlobalCounter.Increment();

                    myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);

                    break;

                case ACLMessage.AGREE:
                    //Receive agreement about the proposition
                    //shutdown
                	myAgent.doDelete();

                case ACLMessage.REFUSE:
                    //Personnal agent refuses the proposition
                    //agent has to try again

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