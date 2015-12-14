
package messages;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.io.*;

public class Agent2 extends Agent 
{
     private AgentFrame frame;
     private int info;
     
     @Override
     public void setup()
     {
         info = 3;
         Object[] args = this.getArguments();
         if(args != null)
         {
             frame = (AgentFrame)args[0];
             if(args.length > 1)
             {
                 info = (int)args[1];
             }
         }
         
         frame.setTitle(this.getName());
         frame.setVisible(true);
         
         addBehaviour(new FrameRefreshBehaviour(this, 100));
         addBehaviour(new ListSendBehaviour(this));
         addBehaviour(new ReceiveBehaviour(this));
     }
     
     private class ListSendBehaviour extends OneShotBehaviour
     {
         private Agent2 myAgent;
         
         public ListSendBehaviour(Agent2 a)
         {
             super(a);
             myAgent = a;
         }
         
         @Override
         public void action()
         {
         
             for(int i = 0; i < myAgent.info; i++)
             {
                 myAgent.frame.AddTextLine("Displaying private info: " + (i+1));
                 
                // send 2 messages to MyAgent1
                 ACLMessage message = new ACLMessage();
                 AID receiverID = new AID("MyAgent1", AID.ISLOCALNAME); // send to an agent from the same container
                 message.addReceiver(receiverID);
                 message.setContent("(Message :container main :type continue :value" + (i + 1) + ")");
                 myAgent.send(message);
                 
                 // sends a message to Agent3 from another container; the IP is required to acces the container 
                 
                 String ip = "";
                 try 
                 {
                    BufferedReader in = new BufferedReader(new FileReader("ip.txt"));
                    ip = in.readLine();
                    in.close();
                 } catch (IOException e) 
                 {
                     System.out.println("Could not read ip file...");
                 }
                 
                 message = new ACLMessage();
                 receiverID = new AID("MyAgent3@" + ip + ":1090/JADE", AID.ISGUID);
                 message.addReceiver(receiverID);
                 message.setContent("Message :container second :value " + (i + 1) + ")");
                 myAgent.send(message);
             }
         }
     }
     
     private class ReceiveBehaviour extends CyclicBehaviour
     {
         private Agent2 myAgent;
         
         public ReceiveBehaviour(Agent2 a)
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
                 myAgent.frame.AddTextLine(s);
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
     
     private class FrameRefreshBehaviour extends TickerBehaviour
     {
         private Agent2 myAgent;
         
         public FrameRefreshBehaviour(Agent2 a, long period)
         {
             super(a, period);
             myAgent = a;
         }
         
         @Override
         public void onTick()
         {
             myAgent.frame.repaint();
             
         }
     }
}