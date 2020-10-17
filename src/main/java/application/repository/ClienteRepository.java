package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import application.model.Cliente;



public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	/*
	  @Query("SELECT t FROM Person t where t.surname = :surname")
	    public List<Person> findAllBySurname(String surname);

	    @Query("SELECT t FROM Person t where t.name = :name")
	    public List<Person> findAllByName(String name);
*/
}
