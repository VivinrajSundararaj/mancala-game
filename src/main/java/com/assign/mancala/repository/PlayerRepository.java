package com.assign.mancala.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assign.mancala.model.Player;

/**
 * Repository for @{@link Player}
 */
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
	/**
	 * Find Player by username
	 *
	 * @param username of the player
	 * @return @{@link Player} by name
	 */
	Player findOneByUsername(String username);
}
