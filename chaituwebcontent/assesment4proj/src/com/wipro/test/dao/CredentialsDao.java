package com.wipro.test.dao;

import com.wipro.test.bean.CredentialsBean;

public interface CredentialsDao {
	
	boolean validateUser(CredentialsBean credentialsBean);
	String getUserType(CredentialsBean credentialsBean);

}
