package com.assign.mancala.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assign.mancala.model.CustomPlayer;
import com.assign.mancala.model.Player;
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
	 * REST endpoint to get the currently logged in player
	 *
	 * @return @{@link Player} instance of the currently logged in player
	 */
	@RequestMapping(value = "/logged", produces = MediaType.APPLICATION_JSON_VALUE)
	public Player getLoggedInPlayer() {
		logger.debug("Getting the currently logged in player");

		CustomPlayer principal = (CustomPlayer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return playerService.getPlayerByUsername(principal.getPlayer().getUsername());
	}
}