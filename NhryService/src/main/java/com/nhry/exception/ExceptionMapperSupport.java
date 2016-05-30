package com.nhry.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;

import com.nhry.common.model.ResponseModel;
import com.nhry.utils.SysContant;
import com.sun.jersey.api.NotFoundException;

@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;

	private static final Logger LOGGER = Logger.getLogger(ExceptionMapperSupport.class);

	private static final String CONTEXT_ATTRIBUTE = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

	/**
	 * 异常处理
	 * @param exception
	 * @return 异常处理后的Response对象
	 */
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		String code = MessageCode.SERVER_ERROR;
		Object msg = null;
		Status statusCode = Status.INTERNAL_SERVER_ERROR;
		// 处理unchecked exception
		if (exception instanceof BaseException) {
			BaseException baseException = (BaseException) exception;
			String _code = baseException.getCode();
			if(!StringUtils.isEmpty(_code)){
				code = baseException.getCode();
			}
			Object _value = baseException.getValue();
			if(_value != null){
				msg = baseException.getValue();
			}
		} else if (exception instanceof NotFoundException) {
			code = MessageCode.REQUEST_NOT_FOUND;
			statusCode = Status.NOT_FOUND;
		}
		if(msg == null){
			msg = SysContant.getSystemConst(code);
		}
		LOGGER.error(msg, exception);
		return formatData(code, msg, null, statusCode);
	}
	
	protected Response formatData(String type, Object msg, Object data,Status statusCode) {
//		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
//		response.setHeader("Access-Control-Allow-Credentials", "true");  
//		response.setHeader("Access-Control-Expose-Headers", "Content-Type"); 
//		response.setHeader("Access-Control-Allow-Origin","*");
//		
		ResponseModel rsmodel = new ResponseModel();
		rsmodel.setType(type);
		rsmodel.setMsg(msg);
		rsmodel.setData(data==null ? "" : data);
		return Response.ok(rsmodel,MediaType.APPLICATION_JSON).status(statusCode).build();
	}
}
