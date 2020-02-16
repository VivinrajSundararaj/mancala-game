package com.assign.mancala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assign.mancala.model.CustomPlayer;
import com.assign.mancala.model.Player;
import com.assign.mancala.object.PlayerDetails;
import com.assign.mancala.repository.PlayerRepository;

/**
 * Class for @{@link Player} related actions
 */
@Service
@Transactional
public class PlayerService {

	private PlayerRepository playerRepository;

	/**
	 * PlayerService constructor
	 *
	 * @param playerRepository @{@link PlayerRepository} dependency
	 */
	@Autowired
	public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	/**
	 * Function to create a new Player
	 *
	 * @param playerDTO @{@link PlayerDetails} to create player from
	 * @return @{@link Player} that was created
	 */
	public Player createPlayer(PlayerDetails playerDTO) {
		// Create Player
		Player player = new Player(playerDTO.getUsername(), playerDTO.getPassword());

		// Save Player
		playerRepository.save(player);

		return player;
	}

	/**
	 * Function to retrieve Player by username
	 *
	 * @param name The username of the Player
	 * @return @{@link Player} that was found by the username
	 */
	public Player getPlayerByUsername(String name) {
		return playerRepository.findOneByUsername(name);
	}

	/**
	 * Function to retrieve the currently logged in user
	 *
	 * @return @{@link Player} that is logged in
	 */
	public Player getLoggedInUser() {
		CustomPlayer principal = (CustomPlayer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return playerRepository.findOneByUsername(principal.getPlayer().getUsername());
	}
}
