package com.jcatalan.proyecto.cl.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcatalan.proyecto.cl.demo.model.User;
import com.jcatalan.proyecto.cl.demo.service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> get(long userId) {
        return repository.findById(userId);
    }

    public List<User> list() {
        Iterable<User> users = repository.findAll();
        List<User> list = new ArrayList<User>();
        users.forEach(list::add);
        return list;
    }

    public User create(User user) {
        return repository.save(user);
    }
    
    public List<String> validate(User user) {
    	List<String> errors = new ArrayList<String>();
    		
    	if (user.getEmail()== null || user.getEmail().isEmpty()) {
    		errors.add("email required");
    	}	
    	if (!Validations.validateEmail(user.getEmail())){
    		errors.add("invalid email");
    	}
    	if (user.getPassword()== null || user.getPassword().isEmpty()) {
    		errors.add("password required");
    	}else {	
    		if (!Validations.validatePassword(user.getPassword())) {
    			errors.add("password invalid");
    		}
    	}
    	return errors;
    }
    
}
