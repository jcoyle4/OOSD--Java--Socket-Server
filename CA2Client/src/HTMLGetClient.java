import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTMLGetClient {

	public static void main(String[] args) {
		String hostName = "127.0.0.1";
		int port = 44444;
		Socket clientSocket;
		PrintWriter out;
		BufferedReader in;
		InputStreamReader ir;
		BufferedReader stdIn;

		
		try{
			clientSocket = new Socket(hostName, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			ir = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(ir);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			out.println("WebPageRequest");
			
			System.out.println("Enter Url: ");
			out.println(stdIn.readLine());
			System.out.println("Enter no_cache vairable (true if not in cache): ");
			out.println(stdIn.readLine());
			
			String serverResponse = in.readLine();
			
			System.out.println("Server says: " + serverResponse);
			
			clientSocket.close();
		} catch(UnknownHostException f){
			System.exit(1);
		} catch (IOException e){
			System.exit(1);
		}

	}

}
