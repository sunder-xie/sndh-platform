package com.nhry.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class BaseResource {
	
	public JSONObject throwMsg(String type,Object msg,Object data){
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
