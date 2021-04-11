package com.wipro.test.service;

import org.springframework.stereotype.Service;

import com.wipro.test.bean.CredentialsBean;
import com.wipro.test.dao.CredentialsDaoImpl;
@Service("User")
public class UserImpl implements User {

	public String login(CredentialsBean credentialsBean) {
		String response = "INVALID";

		if (credentialsBean != null && credentialsBean.getPassword() != null
				&& credentialsBean.getUserID() != null) {
			
			CredentialsDaoImpl daoimpl=new CredentialsDaoImpl();
			if(daoimpl.validateUser(credentialsBean))
			{
				response=daoimpl.getUserType(credentialsBean);
				
			}
			
		
		}

		return response;
	}

}
