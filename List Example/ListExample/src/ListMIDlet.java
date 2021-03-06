
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 *	@author  Saurabh Jain
 *	@version 1.0
 *	Practice Example : 4
 *  
 *	The aim of this example is to make you practice the UI element List.
 *  Please experiment with the example to reep full benefits.
 */
public class ListMIDlet extends javax.microedition.midlet.MIDlet 
{
    private Display display ;
    private List list ;
	private boolean init ;
    
    public ListMIDlet()
    {
        // NOTE : Heavy initialisations should be avoided in the constructor of the MIDlet.
		// The MIDlet is in the paused state when the constructor is called.
		// It does not have access to the resources it needs at this stage.
		// Therefore all heavy initialisations should be done in the way shown below in 
		// the startApp method.
    }
    public void startApp() 
    {
		// NOTE : The startApp method is called whenever the Application Management Software(AMS)
		// decides that the MIDlet needs to get activated.
		// During the lifecycle of particular instance of the application this situation may arise
		// many times. Therefore to save un-necessary initialisations every time the startApp method
		// is called by the AMS, we should use a boolean variable to control the number of initialisations to 1.

		if(init == false)
		{
			// This is the first step to using the user interface in MIDP
			// The statement sets the Display object in our display variable
			// This display object is unique to this MIDlet
			this.display = Display.getDisplay(this) ;
			
			//Initializing a List

			// NOTE : List can use three kinds of choice UI elements.
			// IMPLICIT	
			// EXCLUSIVE
			// MULTIPLE

			this.list = new List("List",List.IMPLICIT) ;       
			list.append("Choice 1",null) ;		// Appends string("Choice 1") and image part (null) to the List.
			list.append("Choice 2",null) ;		// Appends string("Choice 2") and image part (null) to the List.
			list.append("Choice 3",null) ;		// Appends string("Choice 2") and image part (null) to the List.

			// Displaying the List
			this.display.setCurrent(this.list) ;

			init = true ;
		}
    }
    
    public void pauseApp() 
    {
        
    }
    
    public void destroyApp(boolean unconditional) 
    {
        
    }
}


