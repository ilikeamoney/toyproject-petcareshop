package hello.petshop;

import hello.petshop.service.MemberPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@SpringBootApplication
public class HelloPetShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloPetShopApplication.class, args);
	}
}
