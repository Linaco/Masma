package behaviour;
/**
 * Main behavior to receive message from Agent
 * Behavior for PersonnalAgent
 */
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
            String stringToDisplayReceive = GlobalCounter.Get() + " " + "Received from " + senderName + " message: ";
            
            GlobalCounter.Increment();
            String stringToDisplay = GlobalCounter.Get() + "Replying to " + senderName;
            ACLMessage reply;

            switch (message.getPerformative())
            {
                case ACLMessage.INFORM:
                    //Agent says it exist
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[INFORM]");
                	
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
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[PROPOSE]");
                	if(Math.random() >= 0.5){
                		//Accept Proposal
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
                        //Accept the proposition
        				sendConfirm(senderAID);
                    	
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
                		
                		
                	} else {
                		//reject Proposal
                		sendRefuse(senderAID);
                	}
                                    	
                	
                	
                	//Check if the trip is complete then print it
                	myAgent.checkTrip();
                	break;

                case ACLMessage.FAILURE:
                    //Agent couldn't find something with give properties
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[FAILURE]");
                    
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

                    //Send message to say "ok, shutdown"
                	
                	sendConfirm(senderAID);
                	break;
                                   
                case ACLMessage.REQUEST:
                	//Ask to change the date
                	myAgent.windowsForm.AddTextLine(stringToDisplayReceive + "[REQUEST]");
                	reply = new ACLMessage(ACLMessage.REFUSE);
                	String mess = "[REFUSE]";
                	
                	if(myAgent.request.flexible.equals("true")){
                		if(Math.random() >= 0.2){
                			try {
                    			Date[] newDate = (Date[]) message.getContentObject();
                    			myAgent.request.dateBegin = newDate[0];
                    			myAgent.request.dateEnd = newDate[1];
                    			reply = new ACLMessage(ACLMessage.AGREE);
                    			mess = "[AGREE]";
                    		} catch (UnreadableException e) {
                    			e.printStackTrace();
                    		}
                		} else {
                		}
                	} else {
                	}
                	
                	//Say that agree or REFUSE
                	myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " Sending to " + senderAID.getLocalName() + " message: " + mess);
                	
                	reply.addReceiver(senderAID);
                	myAgent.send(reply);
                	
                	GlobalCounter.Increment();
                	
                	sendCFP(senderAID);
                	break;
                	
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
//------------------------------------------------------------
//When the ACLMessage.Failure -> adding a default object to Trip object
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
			myAgent.trip.increasePrice(hotel.price(myAgent.request.dateBegin, myAgent.request.dateEnd, myAgent.request.nbrPpl));
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
		ACLMessage toSend = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		
		toSend.addReceiver(senderAID);

        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " Sending to " + senderAID.getLocalName() + " message: [ACCEPT_PROPOSAL]");
        myAgent.send(toSend);
        
        //One proposition is accepted and added to the trip
        //Increase the state of principalAgent
        myAgent.state++;
		
	}
	
	private void sendRefuse(AID senderAID) {
		ACLMessage toSend = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
		
		toSend.addReceiver(senderAID);
		
		try {
        	toSend.setContentObject(myAgent.request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        myAgent.windowsForm.AddTextLine(GlobalCounter.Get() + " Sending to " + senderAID.getLocalName() + " message: [REJECT_PROPOSAL]");
        myAgent.send(toSend);
		
	}
}