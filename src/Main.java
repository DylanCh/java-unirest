/**
 * Created by Hanjun Chen on 2/10/18.
 */

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Main {

    public static void main(String[] args){
        final String REQUEST_URL = "https://raw.githubusercontent.com/DylanCh/Startupp/master/Startups.json";
        try {
            HttpResponse<JsonNode> response = Unirest.get(REQUEST_URL)
                    .asJson();
            System.out.println(response.getBody());

            String json = response.getBody().toString();

            PrintWriter writer = new PrintWriter("data.json","UTF-8");
            writer.print(new JSONObject(json).toString(4));
            writer.close();

            JSONObject root = new JSONObject(json);

            if(root.has("startups")){
                JSONArray startups = root.getJSONArray("startups");
                List<Object> list = new ArrayList<>();
                for(int i= 0; i< startups.length(); i++){
                    if(!new JSONObject(startups.get(i).toString()).getBoolean("hidden")) {
                        list.add(startups.get(i));
                    }
                }

                for(final Object o : list){
                    System.out.println(o);
                }
            }


        } catch (UnirestException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
