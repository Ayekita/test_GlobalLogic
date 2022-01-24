package com.jcatalan.proyecto.cl.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcatalan.proyecto.cl.demo.error.ErrorInfo;
import com.jcatalan.proyecto.cl.demo.error.ErrorOut;
import com.jcatalan.proyecto.cl.demo.function.Utils;
import com.jcatalan.proyecto.cl.demo.model.Phone;
import com.jcatalan.proyecto.cl.demo.model.User;
import com.jcatalan.proyecto.cl.demo.model.UserCreateOut;
import com.jcatalan.proyecto.cl.demo.service.PhoneService;
import com.jcatalan.proyecto.cl.demo.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class UserController {
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	
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
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity userByToken(@RequestHeader HttpHeaders headers) {

		String authenticationHeader = headers.get(HEADER).get(0);
		
		String userUuid =  Utils.validateToken(authenticationHeader);
		if (userUuid == null) {
			List<String> errors = new ArrayList<String>();
			errors.add("Invalid Token");
			List<ErrorInfo> listErrorinfo =  new ArrayList<ErrorInfo>();
    		listErrorinfo.add(new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), errors));    		
    		ErrorOut errorOut = new ErrorOut(listErrorinfo);    		
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorOut);			
		}else{
			User user = userService.getUserByUuid(userUuid);		
			if(user == null) {
				List<String> errors = new ArrayList<String>();
				errors.add("Not authorized");
				List<ErrorInfo> listErrorinfo =  new ArrayList<ErrorInfo>();
	    		listErrorinfo.add(new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), errors));    		
	    		ErrorOut errorOut = new ErrorOut(listErrorinfo);    		
	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorOut);
			}else {
				System.out.println("pase 1");
				System.out.println(user);
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a");
	    		String dateLastLogin = formatter.format(calendar.getTime());
	    		String token = Utils.getToken(user.getUuid(), user.getEmail());
				user.setLastLogin(dateLastLogin);
				user.setToken(token);
				user = userService.update(user);   
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}  
		}
    }

    @PostMapping(value = "/sign-up",
    		consumes= {MediaType.APPLICATION_JSON_VALUE},
    		produces= {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity create(@RequestBody User user) {
    	
    	List<Phone> listado = user.getPhones();
    	List<Phone> listadoOk = new ArrayList<Phone>();
    	for (Phone phone : listado) {
    		Phone phoneCreated = phoneService.create(phone);   	
    		listadoOk.add(phoneCreated);
		}    	
    	user.setPhones(listadoOk);
    	user.setEmail(user.getEmail().toLowerCase().trim());
    	
    	List<String> errors = userService.validate(user);    	
    	if (errors.size()==0) {
    		Calendar calendar = Calendar.getInstance();
    		
    		User userAux = userService.getUserByEmail(user.getEmail());
    		if (userAux != null) {
    			errors = new ArrayList<String>();
    			errors.add("The user email is already registered");
				List<ErrorInfo> listErrorinfo =  new ArrayList<ErrorInfo>();
	    		listErrorinfo.add(new ErrorInfo(HttpStatus.CONFLICT.value(), errors));    		
	    		ErrorOut errorOut = new ErrorOut(listErrorinfo);    		
	    		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorOut);
    		}else{
	    		UUID uuid = UUID.randomUUID();
	    		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a");
	    		String dateCreate = formatter.format(calendar.getTime());
	    		String token = Utils.getToken(uuid.toString(), user.getEmail());
	    		
	    		user.setUuid(uuid.toString());
	    		user.setToken(token);
	    		user.setCreated(dateCreate);
	    		user.setLastLogin(null); 
	    		user.setIsActive(true);
	    		User userCreated = userService.create(user);   
	    		
	    		UserCreateOut userOut = new UserCreateOut();
	    		userOut.setId(userCreated.getUuid());
	    		userOut.setCreated(userCreated.getCreated());
	    		userOut.setLastLogin(userCreated.getLastLogin());
	    		userOut.setToken(userCreated.getToken());
	    		userOut.setIsActive(userCreated.getIsActive());
	
	    		return ResponseEntity.status(HttpStatus.CREATED).body(userOut);
    		}
    	}else {    	
    		List<ErrorInfo> listErrorinfo =  new ArrayList<ErrorInfo>();
    		listErrorinfo.add(new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errors));    		
    		ErrorOut errorOut = new ErrorOut(listErrorinfo);    		
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorOut);
    	}
        
    }

}