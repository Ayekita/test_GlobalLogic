package com.jcatalan.proyecto.cl.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcatalan.proyecto.cl.demo.model.Phone;
import com.jcatalan.proyecto.cl.demo.model.User;
import com.jcatalan.proyecto.cl.demo.service.repository.PhoneRepository;
import com.jcatalan.proyecto.cl.demo.service.repository.UserRepository;

@Service
public class PhoneService {

	  @Autowired
	    private PhoneRepository repository;

	    public Phone create(Phone phone) {
	        return repository.save(phone);
	    }
    
}
