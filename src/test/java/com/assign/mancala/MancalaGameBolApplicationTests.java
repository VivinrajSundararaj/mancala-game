package com.assign.mancala;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.assign.mancala.controller.GameController;
import com.assign.mancala.controller.PlayController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MancalaGameBolApplicationTests {

	@Autowired
	private GameController gameController;

	@Autowired
	private PlayController playController;

	@Test
	public void contextLoads() {
		assertThat(gameController).isNotNull();
		assertThat(playController).isNotNull();
	}
}
