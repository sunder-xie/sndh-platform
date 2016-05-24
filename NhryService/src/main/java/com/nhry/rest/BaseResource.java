package com.nhry.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.nhry.domain.ResponseModel;

public class BaseResource {
	protected final int pageSize = 8;
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;

	protected Response formatData(String type, Object msg, Object data) {
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");  
		response.setHeader("Access-Control-Expose-Headers", "Content-Type"); 
		response.setHeader("Access-Control-Allow-Origin","*");
		
		ResponseModel rsmodel = new ResponseModel();
		rsmodel.setType(type);
		rsmodel.setMsg(msg);
		rsmodel.setData(data==null ? "" : data);
		return Response.ok().entity(rsmodel).build();
	}
}
