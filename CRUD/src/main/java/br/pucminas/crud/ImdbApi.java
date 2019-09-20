package br.pucminas.crud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImdbApi {
	
	//fazer a api :)
	/*
	 * https://www.baeldung.com/java-http-request
	 * https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
	 * http://www.omdbapi.com/
	 */

	public void getInfoImdb () throws Exception {
		
		URL urlForGetRequest = new URL ("http://worldclockapi.com/api/json/est/now");
		String line = "";
		
		System.out.println("ok1");
		
		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		conection.setRequestMethod("GET");
		
		int response = conection.getResponseCode();
		
		if (response == HttpURLConnection.HTTP_OK) {
			
			System.out.println("ok2");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			String text = "";
			
//			line = br.readLine();
//			System.out.println(line);
			while ((line = br.readLine()) != null) {
				
				text = text + line;
				
			}
			
			br.close();
			
			System.out.println("Texto: " + text);
		}
	}
}
