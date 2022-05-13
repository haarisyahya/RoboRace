package roborace;

import COSC3P91.graphics.ImageManager;
import COSC3P91.xml.XMLReader;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JDialog;
import javax.swing.JFrame;
import roborace.client.ImageAndAnimationFactory;
import roborace.client.Player;
import roborace.client.RoboRaceSoundManager;
import roborace.common.NetworkPort;
import roborace.common.GameDialogs;
import roborace.common.Port;

public class RoboRaceClient {
    
    private static final int PORT = 10997;
    private static final String HOST = "localhost";
      
    public static void main(String[] args) {
    	JFrame.setDefaultLookAndFeelDecorated(true);
	JDialog.setDefaultLookAndFeelDecorated(true);
        ImageManager.getInstance().setImagePath("./Images/");
        ImageAndAnimationFactory.getInstance();
        RoboRaceSoundManager.getInstance(); // uncomment this later so that the RoboRaceSoundManager gets instantiated early.
	XMLReader.setXMLPath("./");
	XMLReader.setXSDPath("./XSD/");
        int playerID;
        
        
    	String name = null;
        while (name==null) {
            try {
                name = GameDialogs.showInputDialog("Name","Please enter your name:",null);
            } catch (Exception e) {}
        }
        try {
            Socket sock = new Socket(HOST, PORT);
            NetworkPort port = new NetworkPort(sock);
            port.send(name);
            playerID = Integer.parseInt(port.receive());
            
            Player play = new Player(playerID, port);
            play.run();
        } catch (IOException e){
            Logger.getLogger(RoboRaceClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }	   
}