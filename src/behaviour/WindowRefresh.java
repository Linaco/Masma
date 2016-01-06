package behaviour;

import agents.WorkingAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class WindowRefresh extends TickerBehaviour {

    private WorkingAgent myAgent;

    public WindowRefresh(WorkingAgent a, long period) {
        super(a, period);
        myAgent = a;
    }

    @Override
    public void onTick() {
        myAgent.windowsForm.repaint();
    }
}
