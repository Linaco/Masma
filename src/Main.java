import init.*;
import jade.wrapper.ControllerException;

public class Main {

    public static void main(String[] args) throws ControllerException, InterruptedException 
    {
    	InitObjects.InitActivities();
    	InitObjects.InitHotel();
    	InitObjects.InitTransport();
    	InitObjects.printAll();
        //MASInit.DoInitialization();
    }
}