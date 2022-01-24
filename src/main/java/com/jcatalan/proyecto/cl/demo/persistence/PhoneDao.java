package com.jcatalan.proyecto.cl.demo.persistence;
import org.springframework.data.repository.CrudRepository;
import com.jcatalan.proyecto.cl.demo.model.Phone;

public interface PhoneDao extends CrudRepository<Phone, Long> {
	
}
