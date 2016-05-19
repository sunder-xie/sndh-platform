package com.nhry.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class BaseResource {
	
	@GET
	@Path("*")
	@Produces(MediaType.APPLICATION_JSON)
    public JSONObject notFoundForGet() {
        return throwMsg(ErrorType.notFound.name(), "404");
    }
	
	@POST
	@Path("*")
	@Produces(MediaType.APPLICATION_JSON)
    public JSONObject notFoundForPost() {
        return throwMsg(ErrorType.notFound.name(), "404");
    }
	
	enum ErrorType{
		notFound,error,warn,normal
	}
	
	public JSONObject throwMsg(String type,String msg){
		JSONObject json = new JSONObject();
		try {
			json.put("type", type);
			json.put("msg", msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public JSONObject throwMsg(String type,String msg,Object data){
		JSONObject json = new JSONObject();
		try {
			json.put("type", type);
			json.put("msg", msg);
			json.put("data", data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
