package com.assign.mancala.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assign.mancala.model.Player;
import com.assign.mancala.object.PlayerDTO;
import com.assign.mancala.service.PlayerService;

/**
 * Class to handle @{@link Player} related REST calls
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

	private PlayerService playerService;

	private final Logger logger = LoggerFactory.getLogger(PlayerController.class);

	/**
	 * PlayerController constructor
	 *
	 * @param playerService @{@link PlayerService} dependency
	 */
	@Autowired
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * REST endpoint to create a player
	 *
	 * @param playerDTO @{@link PlayerDTO} with new player info
	 * @return @{@link Player} instance of the newly created player
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Player createAccount(@RequestBody PlayerDTO playerDTO) {
		logger.debug("Creating new Player");

		Player newPlayer = playerService.createPlayer(playerDTO);
		return newPlayer;
	}

	/**
	 * REST endpoint to get the currently logged in player
	 *
	 * @return @{@link Player} instance of the currently logged in player
	 */
	@RequestMapping(value = "/logged", produces = MediaType.APPLICATION_JSON_VALUE)
	public Player getLoggedInPlayer(String playerName) {
		logger.debug("Getting the currently logged in player");
		return playerService.getPlayerByUsername(playerName);
	}
}