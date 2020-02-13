package com.assign.mancala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assign.mancala.model.MancalaBoard;
import com.assign.mancala.model.Pit;
import com.assign.mancala.repository.PitRepository;

/**
 * Class for @{@link Pit} related actions
 */
@Service
@Transactional
public class PitService {

	private PitRepository pitRepository;

	/**
	 * PitService constructor
	 *
	 * @param pitRepository @{@link PitRepository} dependency
	 */
	@Autowired
	public PitService(PitRepository pitRepository) {
		this.pitRepository = pitRepository;
	}

	/**
	 * Function to create a Pit
	 *
	 * @param board      @{@link MancalaBoard} to create on
	 * @param pitType    @{@link Pit.PitType} of the Pit to create
	 * @param position   of the Pit
	 * @param nrOfStones number of stones in the Pit
	 * @return @{@link Pit} created
	 */
	public Pit createPit(MancalaBoard board, Pit.PitType pitType, int position, int nrOfStones) {
		// Create Pit
		Pit pit = new Pit(board, position, nrOfStones, pitType);

		// Save Pit
		pitRepository.save(pit);

		return pit;
	}

	/**
	 * Function to update the number of stones on a Pit
	 *
	 * @param board      @{@link MancalaBoard} the Pit is on
	 * @param position   of the Pit
	 * @param nrOfStones new number of stones in the Pit
	 * @return @{@link Pit} that was updated
	 */
	public Pit updatePitNumberOfStones(MancalaBoard board, int position, int nrOfStones) {
		// Retrieve Pit
		Pit pit = getPitByBoardAndPosition(board, position);

		// Update Pit
		pit.setStoneCount(nrOfStones);

		// Save Pit
		pitRepository.save(pit);

		return pit;
	}

	/**
	 * Function to update the number of stones on a Pit by a certain amount
	 *
	 * @param board    @{@link MancalaBoard} the Pit is on
	 * @param position of the Pit
	 * @param amount   number of stones to add in the Pit
	 * @return @{@link Pit} that was updated
	 */
	public Pit updatePitNumberOfStonesByAmount(MancalaBoard board, int position, int amount) {
		// Retrieve Pit
		Pit pit = getPitByBoardAndPosition(board, position);

		// Update Pit
		int currentAmount = pit.getStoneCount();
		int newAmount = currentAmount + amount;
		pit.setStoneCount(newAmount);

		// Save Pit
		pitRepository.save(pit);

		return pit;
	}

	/**
	 * Function to update the number of stones on a Pit by one
	 *
	 * @param board    @{@link MancalaBoard} the Pit is on
	 * @param position of the Pit
	 * @return @{@link Pit} that was updated
	 */
	public Pit updatePitNumberOfStonesByOne(MancalaBoard board, int position) {
		// Retrieve Pit
		Pit pit = getPitByBoardAndPosition(board, position);

		// Update Pit
		int currentAmount = pit.getStoneCount();
		currentAmount++;
		pit.setStoneCount(currentAmount);

		// Save Pit
		pitRepository.save(pit);

		return pit;
	}

	/**
	 * Function to get the number of stones by GameBoard and position
	 *
	 * @param board    @{@link MancalaBoard} to get info from
	 * @param position of the Pit to get info from
	 * @return The number of stones on the Pit
	 */
	public int getPitNumberOfStonesByBoardAndPosition(MancalaBoard board, int position) {
		Pit pit = getPitByBoardAndPosition(board, position);
		return pit.getStoneCount();
	}

	/**
	 * Function to retrieve a Pit by @{@link MancalaBoard} and position
	 *
	 * @param board    @{@link MancalaBoard} to get Pit from
	 * @param position of the Pit
	 * @return @{@link Pit} matching params
	 */
	public Pit getPitByBoardAndPosition(MancalaBoard board, int position) {
		return pitRepository.findByGameBoardAndPosition(board, position);
	}
}
