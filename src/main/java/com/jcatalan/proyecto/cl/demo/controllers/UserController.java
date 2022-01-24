package com.jcatalan.proyecto.cl.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jcatalan.proyecto.cl.demo.error.ErrorInfo;
import com.jcatalan.proyecto.cl.demo.error.ErrorOut;
import com.jcatalan.proyecto.cl.demo.model.Phone;
import com.jcatalan.proyecto.cl.demo.model.User;
import com.jcatalan.proyecto.cl.demo.service.PhoneService;
import com.jcatalan.proyecto.cl.demo.service.UserService;
import com.jcatalan.proyecto.cl.demo.service.repository.PhoneRepository;
import com.jcatalan.proyecto.cl.demo.service.repository.Token;
import com.jcatalan.proyecto.cl.demo.service.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class UserController {
	@Autowired
	private UserRepository repository;
	

    @Autowired
    private UserService userService;
    
    @Autowired
    private PhoneService phoneService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<User> list() {
        List<User> users = userService.list();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> userById(@RequestParam(value = "id") long id) {
        Optional<User> user = userService.get(id);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping(value = "/create",
    		consumes= {MediaType.APPLICATION_JSON_VALUE},
    		produces= {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity create(@RequestBody User user) {
    	
    	List<Phone> listado = user.getPhones();
    	List<Phone> listadoOk = new ArrayList<Phone>();
    	for (Phone phone : listado) {
    		System.out.println("pase aca la vida0");
    		Phone phoneCreated = phoneService.create(phone);   	
    		System.out.println(phoneCreated);
    		listadoOk.add(phoneCreated);
    		System.out.println("pase aca la vida1");
		}
    	System.out.println("pase aca la vida2");
    	
    	user.setPhones(listadoOk);
    	
    	List<String> errors = userService.validate(user);
    	System.out.println(errors);
    	
    	if (errors.size()==0) {
    		Calendar calendar = Calendar.getInstance();
    		
    		User userAux = repository.findByEmail(user.getEmail());
    		if (userAux != null) {
    			System.out.println(userAux.getEmail());
    		}
    		
    		
    		UUID uuid = UUID.randomUUID();
    		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a");
    		String dateCreate = formatter.format(calendar.getTime());
    		
    		user.setUuid(uuid.toString());
    		user.setToken(Token.getToken(uuid.toString(), user.getEmail()));
    		user.setCreated(dateCreate);
    		user.setLastLogin(null); 
    		user.setIsActive(true);
    		User userCreated = userService.create(user);   		

    		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    	}else {    	
    		List<ErrorInfo> listErrorinfo =  new ArrayList<ErrorInfo>();
    		listErrorinfo.add(new ErrorInfo(400, errors));
    		
    		ErrorOut errorOut = new ErrorOut(listErrorinfo);
    		
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOut);
    	}
        
    }

}