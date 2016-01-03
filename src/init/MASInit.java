/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

/**
 *
 * @author lab
 */
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import util.AgentFrame;

public class MASInit {
	public static void DoInitialization() throws ControllerException, InterruptedException {
		// create main container
		jade.wrapper.AgentContainer mainContainer = CreateContainer("TripCompany", true, "localhost", "", "1090");
		mainContainer.start();

		//Import data


		//Create frame of agents
		AgentFrame AgentManagerForm = new AgentFrame();
		AgentManagerForm.setLocation(50, 50);

		AgentFrame agent1Form = new AgentFrame();
		agent1Form.setLocation(500, 50);

		AgentFrame agent2Form = new AgentFrame();
		agent2Form.setLocation(50, 370);

		AgentFrame agent3Form = new AgentFrame();
		agent3Form.setLocation(500, 370);

		// create and start each agents with their frame in parameters
		//Personnal agent
		jade.wrapper.AgentController managerAg = CreateAgent(mainContainer, "AgentManager", "agents.AuctionAgentManager", new Object[] { AgentManagerForm });
		//Hotel
		jade.wrapper.AgentController ag1 = CreateAgent(mainContainer, "Hotel", "agents.BuyerAgent", new Object[] { agent1Form });
		//Transport
		jade.wrapper.AgentController ag2 = CreateAgent(mainContainer, "Transport", "agents.BuyerAgent", new Object[] { agent2Form });
		//Activities
		jade.wrapper.AgentController ag3 = CreateAgent(mainContainer, "Activities", "agents.BuyerAgent", new Object[] { agent3Form });

		managerAg.start();
		Thread.sleep(10);
		ag1.start();
		ag2.start();
		ag3.start();

	}

	/*
	 * Create a container:
	 * 
	 * hostAddress = the IP address of the host hostPort = the port through
	 * which the host communicates localPort = the local port through which
	 * agents communicate
	 */
	private static AgentContainer CreateContainer(String containerName, boolean isMainContainer, String hostAddress, String hostPort, String localPort) {
		ProfileImpl p = new ProfileImpl();

		if (containerName.isEmpty() == false) {
			p.setParameter(Profile.CONTAINER_NAME, containerName);
		}

		p.setParameter(Profile.MAIN, String.valueOf(isMainContainer));

		if (localPort != null) {
			p.setParameter(Profile.LOCAL_PORT, localPort);
		}

		if (hostAddress.isEmpty() == false) {
			p.setParameter(Profile.MAIN_HOST, hostAddress);
		}

		if (hostPort.isEmpty() == false) {
			p.setParameter(Profile.MAIN_PORT, hostPort);
		}

		if (isMainContainer == true) {
			return Runtime.instance().createMainContainer(p);
		} else {
			return Runtime.instance().createAgentContainer(p);
		}
	}

	private static AgentController CreateAgent(AgentContainer container, String agentName, String agentClass, Object[] args) throws StaleProxyException {
		return container.createNewAgent(agentName, agentClass, args);
	}
}