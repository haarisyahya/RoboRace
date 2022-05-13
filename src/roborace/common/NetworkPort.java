package roborace.common;

import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkPort implements Port {
	private Socket socket;
	private BufferedReader read;
	private PrintWriter write;

    public NetworkPort(Socket socket){
        this.socket = socket;
	try {
            read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            write = new PrintWriter (socket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void send(String message) { 
	write.println(message);
        write.println("\0");
        write.flush();
    }

    @Override
    public synchronized String receive()  {
        String result = "";
        String message;
        try {
            message = read.readLine();
            while (!message.equals("\0")){
                result += message;
                message = read.readLine();
            }
        } catch (IOException e){
            Logger.getLogger(NetworkPort.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    

    @Override
    public void close() {
 	
    }
}