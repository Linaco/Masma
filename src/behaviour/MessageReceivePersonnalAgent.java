
package behaviour;

import java.io.IOException;
import java.util.Date;

import agents.PersonnalAgent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import objects.*;
import util.GlobalCounter;

    
public class MessageReceivePersonnalAgent extends CyclicBehaviour
{
    private PersonnalAgent myAgent;
    private boolean answer = false;
    
    private boolean hotelIsWaiting = false;
    private AID hotelAID;
    private boolean activitiesIsWaiting = false;
    private AID activitiesAID;
    
    public MessageReceivePersonnalAgent(PersonnalAgent a) 
    {
        super(a);
        myAgent = a;
        
        answer = (myAgent.request.transport == "false") ? true : false;
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
                	
                    //Wait until transportAgent answers for a proposal
                    System.out.println(senderName);
                	if (senderName.equals("Transport") || answer){
                		
                        sendCFP(senderAID);
                        
                	} else {
                		switch (senderName)
                		{
                			case "Activities":
                				System.out.println(senderName);
                				activitiesIsWaiting = true;
                				activitiesAID = senderAID;
                				break;
                				
                			case "Hotel":
                				System.out.println(senderName);
                				hotelIsWaiting = true;
                				hotelAID = senderAID;
                				break;
                					
                			default:
                				System.out.println("Fail");
                				break;
                		}
                			
                			
                		stringToDisplay = "Waiting answer from transport";
                        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);
                	}

                    break;

                case ACLMessage.PROPOSE:
                    //Receive proposition from agent about a price
                	
                	GlobalCounter.Increment();
                    stringToDisplay += GlobalCounter.Get() + " " + "Received from " + senderName + " message: [PROPOSE]";
                    myAgent.windowsForm.AddTextLine(stringToDisplay);
                	
                    switch (senderName)
            		{
            			case "Activities":
            				activitiesMessage(message);
            				break;
            				
            			case "Hotel":
            				hotelMessage(message);
            				break;
            			
            				
            			case "Transport":
            				transportMessage(message);
            				break;
            				
            			default:
            				break;
            		}
                	        	
                 	answer = true;
                	
                	if(activitiesIsWaiting){
                		sendCFP(activitiesAID);
                		activitiesIsWaiting = false;
                	}
                	
                	if(hotelIsWaiting){
                		sendCFP(hotelAID);
                		hotelIsWaiting = false;
                	}
                	
                	//Check if the trip is complete then print it
                	myAgent.checkTrip();
                	break;

                case ACLMessage.FAILURE:
                    //Agent couldn't find something with give properties
                	GlobalCounter.Increment();
                    stringToDisplay += GlobalCounter.Get() + " " + "Received from " + senderName + " message: [FAILURE]";
                    myAgent.windowsForm.AddTextLine(stringToDisplay);
                    
                    //The trip won't be good because something is bad
                    myAgent.trip.setCorrupted(true);
                	
                    //Put default value to Trip Object
                    switch (senderName)
            		{
            			case "Activities":
            				activitiesFail();
            				break;
            				
            			case "Hotel":
            				hotelFail();
            				break;
            			
            				
            			case "Transport":
            				transportFail();
            				break;
            				
            			default:
            				break;
            		}
                	
                  //Check if the trip is complete then print it
                    myAgent.state++;
                    myAgent.checkTrip();


                    //Send message to say "ok, shutdown"
                	
                	sendConfirm(senderAID);
                	break;
                                   
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

	private void transportFail() {
		myAgent.trip.setTransportGo(new Transport("Not Found", "Iasi", myAgent.request.city, myAgent.request.dateBegin, -1, 0));
		myAgent.trip.setTransportGo(new Transport("Not Found", myAgent.request.city, "Iasi", myAgent.request.dateEnd, -1, 0));		
	}

	private void hotelFail() {
		myAgent.trip.setHotel(new Hotel("Not Found",myAgent.request.city, myAgent.request.dateBegin, myAgent.request.dateEnd));
	}

	private void activitiesFail() {
		myAgent.trip.addActivities(new Activities("","Not found","N/A",new Date(), new Date(), -1));
	}

	private void sendConfirm(AID senderAID) {
		ACLMessage toSend = new ACLMessage(ACLMessage.AGREE);

        String stringToDisplay = "Sending to ";

        toSend.addReceiver(senderAID);
        myAgent.send(toSend);

        stringToDisplay += senderAID.getLocalName() + " message: [AGREE]";

        GlobalCounter.Increment();
        
        //One proposition is accepted and added to the trip
        //Increase the state of principalAgent
        myAgent.state++;
        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);
		
	}

	private void hotelMessage(ACLMessage message) {
		try {
			myAgent.windowsForm.AddTextLine("-----------------Hotel----------");
			Hotel hotel = (Hotel) message.getContentObject();
			myAgent.trip.setHotel(hotel);
			myAgent.trip.increasePrice(hotel.price);
			myAgent.windowsForm.AddTextLine(hotel.toString() + "\n");
		
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendConfirm(message.getSender());
		
	}

	private void activitiesMessage(ACLMessage message) {
		try {
			myAgent.windowsForm.AddTextLine("-----------------Activities----------");
			Activities[] activities = (Activities[]) message.getContentObject();
			for(int i = 0; i < activities.length; i++){
				myAgent.trip.addActivities(activities[i]);
				myAgent.trip.increasePrice(activities[i].price);
				myAgent.windowsForm.AddTextLine(activities[i].toString()  + "\n");
			}
			
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendConfirm(message.getSender());
	}

	private void transportMessage(ACLMessage message) {
		try {
			myAgent.windowsForm.AddTextLine("-----------------Transport----------");
			Transport[] transport = (Transport[]) message.getContentObject();
			myAgent.trip.setTransportGo(transport[0]);
			myAgent.request.substractTransport(transport[0].price);
			myAgent.trip.increasePrice(transport[0].price);
			myAgent.windowsForm.AddTextLine(transport[0].toString());
			
			myAgent.trip.setTransportBack(transport[1]);						
			myAgent.request.substractTransport(transport[1].price);
			myAgent.trip.increasePrice(transport[1].price);
			myAgent.windowsForm.AddTextLine(transport[1].toString() + "\n");
		
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendConfirm(message.getSender());
		
	}

	private void sendCFP(AID aid) {
		String stringToDisplay = "Replying to " + aid.getLocalName() + " with message: [CFP]";

        ACLMessage reply = new ACLMessage(ACLMessage.CFP);

        reply.addReceiver(aid);
        try {
        	reply.setContentObject(myAgent.request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
        myAgent.send(reply);

        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " " + stringToDisplay);
		
	}
}