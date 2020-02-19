package com.assign.mancala.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assign.mancala.model.MancalaBoard;
import com.assign.mancala.model.Pit;

/**
 * Repository for @{@link Pit}
 */
@Repository
public interface PitRepository extends CrudRepository<Pit, Long> {

	/**
	 * Find Pit by @{@link MancalaBoard} and position
	 *
	 * @param board    @{@link MancalaBoard} to find by
	 * @param position to find by
	 * @return @{@link Pit} by board and position
	 */
	Pit findByGameBoardAndPosition(MancalaBoard board, int position);
}