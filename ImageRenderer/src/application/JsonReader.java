package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
	public ArrayList<String> GetJsonData()
	{
		String jsonString = CallURL("http://s3.amazonaws.com/vodassets/showcase.json");
		ArrayList<String> imageList = new ArrayList<String>();
 
		try 
		{  
			JSONArray jsonArray = new JSONArray(jsonString);
			int count = jsonArray.length();
			for(int i=0 ; i< count; i++)
			{  
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONArray cardImagesArray = jsonObject.getJSONArray("cardImages");
				int imgCount = cardImagesArray.length();
				for(int j = 0; j < imgCount; j++)
				{
					JSONObject imgObject = cardImagesArray.getJSONObject(j);
					imageList.add(imgObject.getString("url"));
				}
				
				JSONArray keyArtImagesArray = jsonObject.getJSONArray("keyArtImages");
				imgCount = keyArtImagesArray.length();
				for(int j = 0; j < imgCount; j++)
				{
					JSONObject imgObject = keyArtImagesArray.getJSONObject(j);
					imageList.add(imgObject.getString("url"));
				}
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return imageList;
	}
	public String CallURL(String myURL) {
		
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try 
		{
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) 
			{
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) 
				{
					int cp;
					while ((cp = bufferedReader.read()) != -1) 
					{
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} 
		catch (Exception e) 
		{
			throw new RuntimeException("Exception while calling URL:"+ myURL, e);
		} 
 
		return sb.toString();
	}
}
