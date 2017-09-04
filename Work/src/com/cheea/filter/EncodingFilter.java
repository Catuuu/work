package com.cheea.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter{
	private String charSet;
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		String method = request.getMethod();
		if(method.equalsIgnoreCase("post")){
			request.setCharacterEncoding(charSet);
		} else{
			req=new EncodingRequest(request,charSet);
		}
		arg2.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
		if(arg0.getInitParameter("charset").equals("")||arg0.getInitParameter("charset")==null){
			charSet="UTF-8";
		}
		else
			charSet=arg0.getInitParameter("charset");		
	}
	
}
