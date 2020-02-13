package com.assign.mancala.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assign.mancala.model.Game;

/**
 * Repository for @{@link Game}
 */
@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
	/**
	 * Find game by @{@link Game.State}
	 *
	 * @param gameState @{@link Game.State} to find by
	 * @return List of @{@link Game} with given Game.State
	 */
	List<Game> findByState(Game.State gameState);

}
