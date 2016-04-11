import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
	
	int port = 44444;
	ServerSocket ServerSocket = null;
	public ArrayList<URLObject> pages = new ArrayList<URLObject>();
	
	public void runServer(){
		try{
			ServerSocket = new ServerSocket(port);
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
		
		while(true){
			try {
				Socket clientSocket = ServerSocket.accept();
				//start New Thread
				new Thread(new HTMLGetRunnable(clientSocket, pages)).start();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}

}
