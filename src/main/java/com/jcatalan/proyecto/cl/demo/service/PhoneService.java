package com.jcatalan.proyecto.cl.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcatalan.proyecto.cl.demo.model.Phone;
import com.jcatalan.proyecto.cl.demo.persistence.PhoneDao;

@Service
public class PhoneService {

	  @Autowired
	    private PhoneDao phoneDao;

	    public Phone create(Phone phone) {
	        return phoneDao.save(phone);
	    }
    
}
