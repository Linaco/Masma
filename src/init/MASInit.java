/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

/**
 *
 * @author Linaco
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
		jade.wrapper.AgentContainer mainContainer = CreateContainer("TripCompany", true, "localhost", "", "1089");
		mainContainer.start();

		//Import data


		//Create frame of agents
		AgentFrame AgentManagerForm = new AgentFrame();
		AgentManagerForm.setLocation(50, 50);


		// create and start personnalAgent
		//Personnal agent
		jade.wrapper.AgentController managerAg = CreateAgent(mainContainer, "PersonnalAgent", "agents.PersonnalAgent", new Object[] { AgentManagerForm, mainContainer });

		managerAg.start();

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