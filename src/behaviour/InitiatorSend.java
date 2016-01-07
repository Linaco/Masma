
package behaviour;

import java.io.IOException;

import agents.WorkingAgent;
import util.*;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import objects.Request;

public class InitiatorSend extends OneShotBehaviour {
    
    private WorkingAgent myAgent;

    public InitiatorSend(WorkingAgent a)
    {            
        super(a);
        myAgent = a;
    }

    @Override
    public void action()
    {
        //send inform of existing        
        ACLMessage m = new ACLMessage(ACLMessage.INFORM);

        String stringToDisplay = "Sending to ";

        AID receiverAID = new AID("PersonnalAgent", AID.ISLOCALNAME); 
        m.addReceiver(receiverAID);
       
        myAgent.send(m);

        stringToDisplay += receiverAID.getLocalName() + " message: [INFORM]";

        GlobalCounter.Increment();

        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);
        
    }
    
}