
package behaviour;

import agents.PersonnalAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import util.AgentFrame;


//Initiate all agents
public class Initiate extends OneShotBehaviour
{
    private PersonnalAgent myAgent;
    
    public Initiate(PersonnalAgent a) 
    {
        super(a);
        myAgent = a; 
    }
    
    @Override
    public void action()
    {

    //initiate and start all agent
        //wait for callback to send info

        AgentFrame agentHotel = new AgentFrame();
        agentHotel.setLocation(500, 50);

        AgentFrame agentTransport = new AgentFrame();
        agentTransport.setLocation(50, 370);

        AgentFrame agentActivities = new AgentFrame();
        agentActivities.setLocation(500, 370);

        // create and start each agents with their frame in parameters
        //Hotel
        jade.wrapper.AgentController ag1;
		try {
			ag1 = CreateAgent(myAgent.mainContainer, "Hotel", "agents.HotelAgent", new Object[] { agentHotel });
		
	        //Transport
	        jade.wrapper.AgentController ag2 = CreateAgent(myAgent.mainContainer, "Transport", "agents.TransportAgent", new Object[] { agentTransport });
	        //Activities
	        jade.wrapper.AgentController ag3 = CreateAgent(myAgent.mainContainer, "Activities", "agents.ActivitiesAgent", new Object[] { agentActivities });
	
	        ag1.start();
	        ag2.start();
	        //ag3.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static AgentController CreateAgent(AgentContainer container, String agentName, String agentClass, Object[] args) throws StaleProxyException {
		return container.createNewAgent(agentName, agentClass, args);
	}
}