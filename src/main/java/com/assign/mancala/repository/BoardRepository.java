package com.assign.mancala.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assign.mancala.model.Game;
import com.assign.mancala.model.MancalaBoard;

/**
 * Repository for @{@link MancalaBoard}
 */
@Repository
public interface BoardRepository extends CrudRepository<MancalaBoard, Long> {
	/**
	 * Find a board by instance of @{@link Game}
	 *
	 * @param game @{@link Game} instance
	 * @return @{@link MancalaBoard} instance
	 */
	public MancalaBoard findByGame(Game game);
}