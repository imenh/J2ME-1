/*
 * NCMIDlet.java
 *
 * Created on March 5, 2003, 7:05 PM
 */

import javax.microedition.midlet.* ;
import javax.microedition.lcdui.* ;
import java.io.* ;
import javax.microedition.rms.* ;

/**
 *
 * @author Saurabh Jain
 * @version 1.0
 */

public class NCMIDlet extends MIDlet
{
    // Defining the instance variable
    private SplashScreen splash ;
    private Display display ;
    
    private boolean animate ;
    
    private String highscores[] ;
    private RecordStore recordStore ;
    private RecordStore recordStore1 ;
    
    private boolean scoreUpdated ;
    
    private boolean started ;
    private boolean saved ;
    
    // Defining the constructor
    public NCMIDlet( )
    {
        
    }
    
    public void startApp() 
    {
        if(this.started == false)
        {
             // Initialising the settings
            this.animate = true ;
            this.initSettings() ;

            this.display = Display.getDisplay( this ) ;

            this.splash = new SplashScreen( this ) ;
            this.display.setCurrent(this.splash ) ;

            // Initialising the variables
            this.highscores = new String[5] ;
            for (int i = 0; i < 5; i++) 
            {
                this.highscores[i] = "User 0  Device 0" ;
            }
            this.initHighScores() ;
            this.scoreUpdated = false ;

            this.saved = false ;
        }
}
    
    public void pauseApp( ) 
    {
        
    }
    
    public void destroyApp( boolean unconditional ) 
    {
        
    }
    
    public Display getDisplay()
    {
        return this.display ; 
    }
    
    public void setDisplayable( Displayable dis )
    {
        this.display.setCurrent( dis ) ; 
    }
    
    protected boolean getAnimate()
    {
        return this.animate ;
    }
    
    protected void setAnimate(boolean an)
    {
        this.animate = an ;
        
        this.saveSettings() ;
    }
    
    protected String getScoreAt(int i)
    {
        return this.highscores[i] ;
    }
   
   private void initHighScores()
   {   
        //Retrieving the high scores
        try
        {
            this.recordStore = RecordStore.openRecordStore("scores", true) ;
           
            if (this.recordStore.getNumRecords() == 0) 
            {
				// If record store is blank then add default values
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                
                byte[] b ;
				dos.writeUTF("User 0  Device 0");
				b = baos.toByteArray();
			    dos.close();

                for (int i = 0; i < 5; i++) 
                {
                    this.recordStore.addRecord(b, 0, b.length);
                }
            }
            else
            {
                /* Read high score store */
				ByteArrayInputStream bais;
				DataInputStream dis;
				byte data[];
		
				for (int i = 0; i < 5; i++)
                {
					data = this.recordStore.getRecord(i+1);
                    
					if (data != null) 
                    {
                        bais = new ByteArrayInputStream(data);
                        dis  = new DataInputStream(bais) ;
                        this.highscores[i] = dis.readUTF();
                        dis.close() ;
                    }
                }   
            }
       }
       catch(Exception e)
       {
            e.printStackTrace() ;
       }
   }
   
    protected void setHighScore(String score)
    {   
        if(this.saved == false)
        {
            this.highscores[4] = this.highscores[3] ;
            this.highscores[3] = this.highscores[2] ;
            this.highscores[2] = this.highscores[1] ;
            this.highscores[1] = this.highscores[0] ;
            this.highscores[0] = score ;
            
            this.saved = true ;
        }
        else
        {
            this.highscores[0] = score ;
        }
        
       this.saveScores() ;
   }
   
   private void saveScores()
   {
        ByteArrayOutputStream baos;
		DataOutputStream das;
		byte[] data;

		try
        {
            baos = new ByteArrayOutputStream();
            das = new DataOutputStream(baos);

            for(int i = 0; i < 5; i++)
            {
                das.writeUTF(this.highscores[i]) ;
                data = baos.toByteArray();
            
				this.recordStore.setRecord(i + 1, data, 0, data.length) ;
                
                // For reseting the variables
                baos.reset() ;
                data = null ;
            }
            
            das.close() ;
	} 
        catch (Exception e)
        {
	    e.printStackTrace() ;
	}
   }
   
   private void initSettings()
   {
       //Retrieving the settings
        try
        {
            this.recordStore1 = RecordStore.openRecordStore("settings", true) ;
           
            if (this.recordStore1.getNumRecords() == 0) 
            {
				// If record store is blank then add default values
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                
                byte[] b ;
				dos.writeBoolean(this.animate);
				b = baos.toByteArray();
				dos.close();

                this.recordStore1.addRecord(b, 0, b.length) ;
            }
            else
            {
                /* Read settings */
				ByteArrayInputStream bais;
				DataInputStream dis;
				byte data[] ;
		
                data = this.recordStore1.getRecord(1);
                
                if (data != null) 
                {
                    bais = new ByteArrayInputStream(data);
                    dis  = new DataInputStream(bais) ;
                    this.animate = dis.readBoolean();
                    dis.close() ;
                }   
            }
       }
       catch(Exception e)
       {
            e.printStackTrace() ;
       }
   }
   
   private void saveSettings()
   {
        ByteArrayOutputStream baos;
		DataOutputStream das;
		byte[] data;

		try
        {
            baos = new ByteArrayOutputStream();
            das = new DataOutputStream(baos);

            das.writeBoolean(this.animate) ;
            data = baos.toByteArray();

            this.recordStore1.setRecord(1, data, 0, data.length) ;

            baos.reset() ;
            data = null ;
            
            das.close() ;
	} 
        catch (Exception e) 
        {
            e.printStackTrace() ;
		}
	}
}
