
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.* ;
import javax.microedition.io.* ;

/**
 * @author  Saurabh Jain
 * @version 1.0
 * Practice Example : 7
 *
 * A MIDlet which shows a simple example for connecting a mobile phone to a web server
 * and retrieving data.
 * This example also helps you practice commands.
 * This example will also teach you to close the application from with the code
 */
public class ConnectMIDlet extends MIDlet implements CommandListener, Runnable
{
    // The variable for knowing the initialization state
    private boolean init ;
    
    // Command Variables
    private Command exitCommand;	// The exit command
    private Command Connect ;		// The command that will call the connection code
    
    // Important application variables
    private Display display;    // The display for this MIDlet
    private TextBox  t ;
    private String st ;
  
	public ConnectMIDlet()
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
            display = Display.getDisplay(this);

			// Initialising the commands
            Connect = new Command("Connect",Command.OK,1) ;
            exitCommand = new Command("Exit", Command.EXIT, 2);

			// Initialising the text box
            t = new TextBox("SKJ Explorer", "http://www.skjworld.com", 400, 0);

			// Adding commands to the text box
            t.addCommand(Connect) ;
            t.addCommand(exitCommand);

			// Setting the command listener
            t.setCommandListener(this);

            init = true ;
        }
        
        display.setCurrent(t);
    }
    
    public void pauseApp()
	{

    }
    
    /**
     * Destroy must cleanup everything not handled by the garbage collector.
     * In this case there is nothing to cleanup.
     */
    public void destroyApp(boolean unconditional)
	{

    }
    
    /*
     * Responds to commands
     */
    public void commandAction(Command c, Displayable s)
    {
        if(c == this.Connect)
        {
			// Running the network connection in a new thread
			// Do remember that a network connection should
			// be run in a different thread fom the application's default thread
            new Thread(this).start() ;
        }
        else if (c == exitCommand)
        {
			// Close the application from with the code
            notifyDestroyed() ;
        }
    }

	// The method defined by the Runnable interface
	public void run()
	{
		t.setString(this.INet(t.getString())) ;
	}
    
    // Method for getting the content out of a particular URL
    private String INet(String url)
    {
        // Initialization of important local variables
        HttpConnection c = null ;
        InputStream is = null ;
        StringBuffer sb = new StringBuffer() ;
        String s = "1" ;

        try
        {
            // Setting up a HttpConnection
            c = (HttpConnection)Connector.open(url);
            c.setRequestProperty("Accept","text/html") ;
                
            // Getting the InputStream will open the connection
            is = c.openInputStream() ;  
            
            // Get the length and process the data
            int len = (int)c.getLength();
            
			if (len > 0)
            {
                int i,data ;
                i = 0 ;
                data = 0 ;
                
                while(i < t.getMaxSize() && data != -1)
                {
                    data = is.read() ;
                    
                    sb.append((char)data) ;
                    i++ ;
                }
                
                s = sb.toString() ;
            }
            else
            {
                int ch;
                while ((ch = is.read()) != -1)
                {
                    sb.append(ch) ;
                }
                
                s = sb.toString() ;
            }
        }
        catch(Exception e)
        {
            s = e.toString() ;
        }
        finally
        {
            try
            {
				// Closing the inout stream and the connection
                if (is != null)
                is.close();
                if (c != null)
                c.close();
            }
            catch(Exception e)
            {
                e.toString() ;
            }
        }
        
        return s ;
    }
}
