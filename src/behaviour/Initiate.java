
package behaviour;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;


//Initiate all agents
public class Initiate extends OneShotBehaviour
{
    private Agent myAgent;
    
    public Initiate(Agent a) 
    {
        super(a);
        myAgent = a; 
    }
    
    @Override
    public void action()
    {

    //initiate all agent
        //wait for callback to send info

        ACLMessage message = myAgent.receive();
        
        if(message != null)
        {
            String s = message.getContent() + " from " + message.getSender().getLocalName();
            myAgent.frame.AddTextLine(s);
            
            ACLMessage answer = new ACLMessage();
            answer.addReceiver(message.getSender());
            answer.setContent("(Message :id 1)");
            answer.setConversationId("ID1"); // "ID1" will have to be matched by any agents receiving the message
            myAgent.send(answer);
            
            answer.setContent("(Message :id 2)");
            answer.setConversationId("ID2");
            myAgent.send(answer);
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