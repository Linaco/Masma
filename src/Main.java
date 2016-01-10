import init.*;
import jade.wrapper.ControllerException;
import util.*;

public class Main {

    public static void main(String[] args) throws ControllerException, InterruptedException 
    {
    	//Populate the dataBase
    	InitObjects.InitActivities();
    	InitObjects.InitHotel();
    	InitObjects.InitTransport();
    	//InitObjects.printAll();
        MASInit.DoInitialization();
        //new PersonnalAgentFrame().setVisible(true);;
    }
}