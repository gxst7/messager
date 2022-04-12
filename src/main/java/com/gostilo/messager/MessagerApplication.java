package com.gostilo.messager;

import com.gostilo.messager.domain.User;
import com.gostilo.messager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagerApplication {

	private static final Logger log = LoggerFactory.getLogger(MessagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MessagerApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(UserRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new User("Jack", "Bauer", "jbauer@gmail.com", "jack", "pass"));
			repository.save(new User("Chloe", "O'Brian", "obrian@gmail.com", "chloe", "pass"));
			repository.save(new User("Kim", "Bauer", "kbauer@gmail.com", "kim", "pass"));
			repository.save(new User("David", "Palmer", "dpalmer@gmail.com", "david", "pass"));
			repository.save(new User("Michelle", "Dessler", "mdessler@gmail.com", "michelle", "pass"));

			// fetch all customers
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User user : repository.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			User user = repository.findById(1L);
			log.info("Users found with findById(1L):");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

			// fetch customers by last name
			log.info("Users found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			//  log.info(bauer.toString());
			// }
			log.info("");
		};
	}
}
