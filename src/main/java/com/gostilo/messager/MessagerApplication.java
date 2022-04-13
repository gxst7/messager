package com.gostilo.messager;

import com.gostilo.messager.domain.Role;
import com.gostilo.messager.domain.User;
import com.gostilo.messager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class MessagerApplication {

	private static final Logger log = LoggerFactory.getLogger(MessagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MessagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository repository) {
		return (args) -> {
			// save a few users
			repository.save(new User("Jack", "Bauer", "jbauer@gmail.com", "jack", "pass", true, Set.of(Role.USER)));
			repository.save(new User("Chloe", "O'Brian", "obrian@gmail.com", "chloe", "pass", true, Set.of(Role.ADMIN)));
			repository.save(new User("Kim", "Bauer", "kbauer@gmail.com", "kim", "pass", true, Set.of(Role.USER)));
			repository.save(new User("David", "Palmer", "dpalmer@gmail.com", "david", "pass", true, Set.of(Role.USER)));
			repository.save(new User("Michelle", "Dessler", "mdessler@gmail.com", "michelle", "pass", true, Set.of(Role.ADMIN)));

			// fetch all users
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User user : repository.findAll()) {
				log.info(user.toString());
			}
			log.info("");

			// fetch an individual user by ID
			User user = repository.findById(1L);
			log.info("Users found with findById(1L):");
			log.info("--------------------------------");
			log.info(user.toString());
			log.info("");

			// fetch users by last name
			log.info("Users found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});
			// for (User bauer : repository.findByLastName("Bauer")) {
			//  log.info(bauer.toString());
			// }
			log.info("");
		};
	}
}
