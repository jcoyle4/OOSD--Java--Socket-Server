import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class URLObject {
	public String url;
	public String html;
	
	public URLObject(String url){
		this.url = url;
	}
	
	
	public String getHtml(Boolean first_time){
		String temp_html = "";
		try{
			URL u = new URL(this.url);
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			String input_line;
			while ((input_line = in.readLine()) != null){
				//System.out.println(input_line);
				temp_html += input_line;
			}
			in.close();
		} catch (Exception e){
			System.out.println("Get HTML Page Error!"); 
		}
		
		this.html = temp_html;
		if(!first_time)
			return temp_html;
		else
			return "";
	}
}
