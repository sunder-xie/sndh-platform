package com.nhry.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.sun.jersey.api.NotFoundException;

@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

	private static final Logger LOGGER = Logger.getLogger(ExceptionMapperSupport.class);

	private static final String CONTEXT_ATTRIBUTE = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;

	/**
	 * 异常处理
	 * @param exception
	 * @return 异常处理后的Response对象
	 */
	public Response toResponse(Exception exception) {
		String message = ExceptionCode.SERVER_ERROR;
		Status statusCode = Status.INTERNAL_SERVER_ERROR;
		// 处理unchecked exception
		if (exception instanceof BaseException) {
			BaseException baseException = (BaseException) exception;
			String code = baseException.getCode();
			Object msg = baseException.getValue();
		} else if (exception instanceof NotFoundException) {
			message = ExceptionCode.REQUEST_NOT_FOUND;
			statusCode = Status.NOT_FOUND;
		} 
		LOGGER.error(message, exception);
		return Response.ok(message, MediaType.TEXT_PLAIN).status(statusCode).build();
	}
}
