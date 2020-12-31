package it.mycompany.heroku;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HerokuApplication implements CommandLineRunner{
	
	@Autowired
	private SalutoRepository salutoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HerokuApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(salutoRepository.count()==0) 
			salutoRepository.save(new Saluto("Hello World"));		
	}

}

@RestController
class CiaoController {
	
	@Autowired
	private SalutoRepository salutoRepository;
	
	@GetMapping("/")
	ResponseEntity<List<Saluto>> helloFromDB() {
		return ResponseEntity.ok().body(salutoRepository.findAll());
	}
	
}

@Entity
class Saluto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String messaggio;

	public Saluto(String messaggio) {
		super();
		this.messaggio = messaggio;
	}

	public Long getId() {
		return id;
	}

	public String getMessaggio() {
		return messaggio;
	}
	
}

interface SalutoRepository extends JpaRepository<Saluto, Long>{}