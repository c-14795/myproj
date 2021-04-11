package com.wipro.test.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.test.bean.CredentialsBean;
@Service("credentialsDao")
@SuppressWarnings({ "rawtypes" })
public class CredentialsDaoImpl implements CredentialsDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public boolean validateUser(CredentialsBean credentialsBean) {
		boolean response = false;
	
		try {
			Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("From CredentialsBean  where userid=? and password=?");
		q.setString(0, credentialsBean.getUserID());
		q.setString(1, credentialsBean.getPassword());
		List list = q.list();
		if (!list.isEmpty()) {
			response=true;
		}
		
		} catch (Exception e) {
			// TODO: handle exception
		}

		return response;
	}

	
	public String getUserType(CredentialsBean credentialsBean) {
		String response="A";
		
		try {
			Query q = sessionFactory.getCurrentSession().createQuery(
					"From CredentialsBean  where userid=? and password=?");
			q.setString(0, credentialsBean.getUserID());
			q.setString(1, credentialsBean.getPassword());
			List list = q.list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				CredentialsBean object = (CredentialsBean) it.next();
				response = object.getUserType();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
}
