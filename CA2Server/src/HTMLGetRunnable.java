import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class HTMLGetRunnable implements Runnable{
	
	protected Socket clientSocket = null;
	ArrayList<URLObject> pages = null;
	public URLObject temp_urlobj = null;
	
	
	public HTMLGetRunnable(Socket clientSocket, ArrayList<URLObject> pages ){
		this.clientSocket = clientSocket;
		this.pages = pages;
	}
	
	public synchronized void AddToPages(URLObject urlobj){
		System.out.println("Adding " + urlobj.url + " to Cache");
		pages.add(urlobj);
	}
	
	public synchronized String SearchCache(){
		String Response = "";
		System.out.println("Searching Array List (Cache)");
		for (int i = 0; i<pages.size(); i++){
			URLObject u = (URLObject)pages.get(i);
			
			if (u.url.equals(temp_urlobj.url)){
				Response = u.html;
				System.out.println("Returning a Cached Page");
				break;
			}
			else{
				System.out.println("Page not in Cache");
				Response = "empty";
			}
		}
		
		return Response;
	}
	

	public void run() {
		try{
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			if (in.readLine().equals("WebPageRequest")){
				String url, cache_request;
				Boolean no_Cache;
				
				url = in.readLine();
				temp_urlobj = new URLObject(url);
				System.out.println("Request for " + url);
				
				cache_request = in.readLine();
				if(cache_request.toUpperCase().equals("TRUE")){
					no_Cache = true;
				} 
				else
					no_Cache = false;
				
				
				if(no_Cache){
					System.out.println("No_Cache = false - getting html for the first time");
					temp_urlobj.getHtml(true);
					out.println(temp_urlobj.html);
					AddToPages(temp_urlobj);
				}
				else if (!no_Cache){
					System.out.println("No_Cache = true - searching cache to see if url is there");
					String RequestedHtml = "";
					RequestedHtml = SearchCache();
					if (RequestedHtml.equals("empty")){
						System.out.println("Page not in cache - getting from the WWW");
						RequestedHtml = temp_urlobj.getHtml(false);
					}
					AddToPages(temp_urlobj);
					out.println(RequestedHtml);
				}
			}
			
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
