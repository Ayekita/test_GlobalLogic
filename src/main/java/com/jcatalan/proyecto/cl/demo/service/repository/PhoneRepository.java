package com.jcatalan.proyecto.cl.demo.service.repository;
import org.springframework.data.repository.CrudRepository;
import com.jcatalan.proyecto.cl.demo.model.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}