package br.vet.certvet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CertvetApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertvetApplication.class, args);
	}

}
