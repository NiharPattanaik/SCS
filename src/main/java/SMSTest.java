import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
 
public class SMSTest {
	
	public static void main(String a[]){
		final String url = "http://192.168.0.4:8080/crm/rest/userReST/validateUser/admin_user/welcome123";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String response =  restTemplate.getForObject(url, String.class);

         
         //String response =  restTemplate.getForObject(url, String.class);
         JSONObject userJson = new JSONObject(response);
         System.out.println(response);
         if(userJson.getInt("status") == 1){
             JSONArray userArray = userJson.getJSONArray("businessEntities");
             if(userArray != null && userArray.length() > 0){
                 JSONObject userObject = new JSONObject(userArray.get(0).toString());
                 System.out.println(userObject.get("userID"));
                 JSONArray rolesArray = userObject.getJSONArray("roles");
                 if(rolesArray != null){
                	 for(int i=0; i<rolesArray.length(); i++){
                		 JSONObject role = new JSONObject(rolesArray.get(i).toString());
                		 System.out.println(role.getInt("roleID"));
                	 }
                 }
                 
             }
         }
	}
	
	public static void sendSms() {
		try {
			// Construct data
			String user = "username=" + "nihar.pattanaik@gmail.com";
			String hash = "&hash=" + "Welcome123";
			String message = "&message=" + "Test message from Nihar";
			String sender = "&sender=" + "Nihar";
			String numbers = "&numbers=" + "919740388348";
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("http://api.textlocal.in/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			JSONObject obj = new JSONObject(stringBuffer.toString());
			System.out.println(obj.get("status"));
			
			
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			
		}
	}
}