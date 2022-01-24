package com.jcatalan.proyecto.cl.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcatalan.proyecto.cl.demo.function.Utils;
import com.jcatalan.proyecto.cl.demo.model.User;
import com.jcatalan.proyecto.cl.demo.persistence.UserDao;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public Optional<User> get(long userId) {
        return userDao.findById(userId);
    }
    
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
    public User getUserByUuid(String uuid) {
        return userDao.findByUuid(uuid);
    }

    public List<User> list() {
        Iterable<User> users = userDao.findAll();
        List<User> list = new ArrayList<User>();
        users.forEach(list::add);
        return list;
    }

    public User create(User user) {
        return userDao.save(user);
    }
    
    public User update(User user) {
        return userDao.save(user);
    }
    
    public List<String> validate(User user) {
    	List<String> errors = new ArrayList<String>();
    		
    	if (user.getEmail()== null || user.getEmail().isEmpty()) {
    		errors.add("email required");
    	}	
    	if (!Utils.validateEmail(user.getEmail())){
    		errors.add("invalid email");
    	}
    	if (user.getPassword()== null || user.getPassword().isEmpty()) {
    		errors.add("password required");
    	}else {	
    		if (!Utils.validatePassword(user.getPassword())) {
    			errors.add("password invalid");
    		}
    	}
    	return errors;
    }
    
}
