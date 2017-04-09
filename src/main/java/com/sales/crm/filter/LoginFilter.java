package com.sales.crm.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sales.crm.model.Role;
import com.sales.crm.model.User;
import com.sales.crm.service.UserService;

public class LoginFilter implements Filter {
	
	
	UserService userService = null;
	
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException { 
    	HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath()+"/web/userWeb/login";
        String logoutURI = request.getContextPath()+"/logout";

        boolean loggedIn = false;
        boolean loginRequest = false;
        boolean restRequest = false;
        
        if(request.getRequestURI().equals(logoutURI)){
			session.invalidate();
		}else{
			loggedIn = session != null && session.getAttribute("user") != null;
			loginRequest = request.getRequestURI().equals(loginURI);
			restRequest = request.getRequestURI().contains("/crm/rest/") ? true : false;
			
		}
		
        if(loginRequest && !loggedIn){
        	String userName = request.getParameter("uname");
    		String password = request.getParameter("psw");
    		User user = userService.getUser(userName);
    		if(!userService.validateUserCredential(userName, password)){
    			Map<String, Object> modelMap = new HashMap<String, Object>();
    			modelMap.put("msg", "Invalid user name or password.");
    			RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");  
            	rd.include(req, res);
    		}else if (!isAdminUser(user)){
    			Map<String, Object> modelMap = new HashMap<String, Object>();
    			modelMap.put("msg", "User <b>"+ userName +"</b> not having required previligaes to access the application");
    			RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");  
            	rd.include(req, res);
    		}else{
    			chain.doFilter(request, response);
    		}
        }else if(restRequest && !loggedIn){
        	if(validateRESTCredential(request, response)){
        		chain.doFilter(request, response);
        	}else{
        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        	}
        }else if(loggedIn ) {
            chain.doFilter(request, response);
        } else {
        	RequestDispatcher rd=req.getRequestDispatcher("/index.jsp");  
        	rd.include(req, res); 
        }
    }
    
    private boolean isAdminUser(User user){
		List<Role> roles = user.getRoles();
		for(Role role : roles){
			if(role.getRoleID() == 1){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		userService = 
  			  (UserService)WebApplicationContextUtils.
  			    getRequiredWebApplicationContext(filterConfig.getServletContext()).
  			    getBean("userService");
	
		
	}

    boolean validateRESTCredential(HttpServletRequest request, HttpServletResponse response){
    	String authCredentials = request.getHeader("Authorization");
    	if (null == authCredentials)
			return false;
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"
				+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(
					encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String userName = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		return userService.validateUserCredential(userName, password);
    	
    	
    }
}