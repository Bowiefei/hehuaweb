package com.hehuaweb.web;

import java.net.URI;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.hehuaweb.dao.EntityDao;

/**
 * 
 * @author Sean version 1.0 2014年12月17日
 */
public abstract class BaseResource {

	protected static final int PAGE_SIZE = 0;

	protected Response goIndex() {
		try {
			URI location = new URI("/");
			return Response.seeOther(location).build();

		} catch (Throwable t) {
			return null;
		}
	}

	@Context
	protected HttpServletRequest request;
	@Resource
	protected EntityDao entityDao;



}
