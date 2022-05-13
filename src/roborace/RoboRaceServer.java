package roborace;

import COSC3P91.graphics.ImageManager;
import COSC3P91.xml.XMLReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JFrame;
import roborace.common.NetworkPort;
import roborace.common.GameDialogs;
import roborace.common.Port;
import roborace.server.GameMaster;

public class RoboRaceServer {
    
    private static final int PORT = 10997;
      
    public static void main(String[] args) {
    	JFrame.setDefaultLookAndFeelDecorated(true);
	JDialog.setDefaultLookAndFeelDecorated(true);
        ImageManager.getInstance().setImagePath("./Images/");
	XMLReader.setXMLPath("./");
	XMLReader.setXSDPath("./XSD/");
        
    	int nHuman = 0;
    	while (nHuman == 0 || nHuman > 4) {
            try {
                nHuman = Integer.parseInt(GameDialogs.showInputDialog("Number of players", "Please, input the number of players (1-4):",null));
            } catch (NumberFormatException e) {}
        }
	Port ports[] = new Port[nHuman];
	String names[] = new String[nHuman];
        
	
        try {
            ServerSocket server = new ServerSocket(PORT);
            for (int i=0; i<nHuman; i++){
                Socket client = server.accept();
                NetworkPort currentPort = new NetworkPort(client);
                ports[i] = currentPort;
                names[i] = currentPort.receive();
                currentPort.send(Integer.toString(i));
            }
        } catch (IOException ex){
            ex.printStackTrace();}
        
        (new GameMaster(names,ports)).run();
    }
}	   
