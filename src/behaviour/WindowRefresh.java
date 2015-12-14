package AgentProtocol;

import jade.core.behaviours.TickerBehaviour;

public class InitiatorWindowRefresh extends TickerBehaviour {

    private InitiatorAgent myAgent;

    public InitiatorWindowRefresh(InitiatorAgent a, long period) {
        super(a, period);
        myAgent = a;
    }

    @Override
    public void onTick() {
        myAgent.windowsForm.repaint();
    }
}
