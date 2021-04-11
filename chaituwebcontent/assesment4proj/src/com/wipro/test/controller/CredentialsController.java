package com.wipro.test.controller;
 


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wipro.test.bean.CredentialsBean;
import com.wipro.test.service.UserImpl;

@Controller
public class CredentialsController {
	
	
	@RequestMapping(value="/login" ,method=RequestMethod.POST)
	public ModelAndView logincontrroller(@RequestParam("UserID") String name ,@RequestParam("Password") String password)
	{
		ModelAndView maw=null;
		CredentialsBean bean=new CredentialsBean();
		bean.setUserID(name);
		bean.setPassword(password);
		UserImpl s=new UserImpl();
		String response=s.login(bean);
		if(response.equals("A"))
		{
			maw=new ModelAndView("adminhome");
			maw.addObject("message","Welcome Admin! This is Admin Home Page");
		}
		if(response.equals("C"))
		{
			maw =new ModelAndView("customerhome");
			maw.addObject("message","Welcome Customer! This is user home Page");
		}
		if(response.equals("INVALID"))
		{
			maw=new ModelAndView("login");
		}
			
		return maw;
		
	}

}
