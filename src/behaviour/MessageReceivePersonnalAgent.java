
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
    

        	AID senderAID = message.getSender();
            String senderName = senderAID.getLocalName();
            
            GlobalCounter.Increment();
            String stringToDisplay = GlobalCounter.Get() + " " + "Received from " + senderName + " message: " + message.getPerformative();
            myAgent.windowsForm.AddTextLine(stringToDisplay);
            
            GlobalCounter.Increment();
            stringToDisplay = GlobalCounter.Get() + "Replying to " + senderName;

            switch (message.getPerformative())
            {
                case ACLMessage.INFORM:
                    //Agent says it exist
                	
                    //Wait until transportAgent answers for a proposal
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
                	
                    //To say that "transportAgent" answer. Then if other agent "INFORM" PersonnalAgent can anwser directly
                 	answer = true;
                	
                 	//In case agent is waiting for an answer, this will send them the CFP
                	if(activitiesIsWaiting){
                		sendCFP(activitiesAID);
                		activitiesIsWaiting = false;
                	}
                	
                	if(hotelIsWaiting){
                		sendCFP(hotelAID);
                		hotelIsWaiting = false;
                	}
                	
                	//Accept the proposition
    				sendConfirm(senderAID);
                	
                	//Check if the trip is complete then print it
                	myAgent.checkTrip();
                	break;

                case ACLMessage.FAILURE:
                    //Agent couldn't find something with give properties
                    
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
		myAgent.trip.setTransportBack(new Transport("Not Found", myAgent.request.city, "Iasi", myAgent.request.dateEnd, -1, 0));		
	}

	private void hotelFail() {
		myAgent.trip.setHotel(new Hotel("Not Found",myAgent.request.city, myAgent.request.dateBegin, myAgent.request.dateEnd));
	}

	private void activitiesFail() {
		myAgent.trip.addActivities(new Activities("","Not found","N/A",new Date(), new Date(), -1));
	}

	
//-------------------------------------------------
//When message has object to add to the TripObject (of Personnal Agent)
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
		
	}
	
	
//---------------------------------------------------
//Send Message
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
	
	private void sendConfirm(AID senderAID) {
		ACLMessage toSend = new ACLMessage(ACLMessage.AGREE);
		
		toSend.addReceiver(senderAID);

        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " Sending to sender" + senderAID.getLocalName() + " message: [AGREE]");
        myAgent.send(toSend);
        
        //One proposition is accepted and added to the trip
        //Increase the state of principalAgent
        myAgent.state++;
		
	}
}