package com.jcatalan.proyecto.cl.demo.persistence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jcatalan.proyecto.cl.demo.model.User;

public interface UserDao extends CrudRepository<User, Long> {
	public User findByEmail(@Param("email") String email);
	public User findByUuid(@Param("uuid") String uuid);

}
