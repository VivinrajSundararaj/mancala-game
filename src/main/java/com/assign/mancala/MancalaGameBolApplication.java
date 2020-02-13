package com.assign.mancala;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.assign.mancala.model.Player;
import com.assign.mancala.repository.PlayerRepository;

@SpringBootApplication
@EntityScan(basePackages = { "com.assign.mancala.model" })
public class MancalaGameBolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MancalaGameBolApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PlayerRepository playerRepository) {
		return (args) -> {

			// Create 2 players
			playerRepository.save(new Player("vivin", "vivin"));
			playerRepository.save(new Player("sundar", "sundar"));

		};
	}

}
