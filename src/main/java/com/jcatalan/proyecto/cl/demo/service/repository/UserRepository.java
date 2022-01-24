package com.jcatalan.proyecto.cl.demo.service.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jcatalan.proyecto.cl.demo.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByEmail(@Param("email") String email);

}