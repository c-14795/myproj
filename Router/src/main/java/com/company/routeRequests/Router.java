package com.company.routeRequests;

/**
 *
 * @author c14795
 */
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class Router {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @SuppressWarnings("CallToPrintStackTrace")
    public Response route(InputStream payload) throws Exception{        
        String data = readIncomingData(payload);
        Map<String,Object> json_map = getMapFromJson(data);
        String url = json_map.remove("url").toString();
        json_map.put("a", "b");
        System.out.println(json_map.toString());
        String res = RestCalls.httpPostCall(url, json_map, new HashMap<String,String>());
        return Response.status(200).entity(res).build();
    }

    @Path("/https")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response routeHttps(InputStream payload) {
        String data = readIncomingData(payload);        
        return Response.status(200).entity(data).build();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private String readIncomingData(InputStream payload) {
       
        StringBuilder data = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(payload))) {
            String line = null;
            while ((line = in.readLine()) != null) {
                data.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing data received "+ e.getMessage());
            System.out.println("Stack trace goes below");
            e.printStackTrace();
        }
        return data.toString() ;
    }

    Map getMapFromJson(String data) {
     Gson gson = new Gson();
     Map<String, Object> respmap = gson.fromJson(data, Map.class);
     return respmap;
    }
}