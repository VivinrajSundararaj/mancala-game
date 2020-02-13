package com.assign.mancala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assign.mancala.model.Game;
import com.assign.mancala.model.MancalaBoard;
import com.assign.mancala.repository.BoardRepository;

/**
 * Class to handle @{@link MancalaBoard} related actions
 */
@Service
@Transactional
public class BoardService {

	private BoardRepository boardRepository;

	/**
	 * BoardService constructor
	 *
	 * @param boardRepository @{@link BoardRepository} dependency
	 */
	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	/**
	 * Create new board
	 *
	 * @param game @{@link Game} for which to create GameBoard
	 * @return @{@link MancalaBoard} that has been created
	 */
	public MancalaBoard createNewBoard(Game game) {
		MancalaBoard board = new MancalaBoard(game);

		boardRepository.save(board);

		return board;
	}

	/**
	 * Retrieve a GameBoard by @{@link Game}
	 *
	 * @param game @{@link Game} to retrieve GameBoard for
	 * @return @{@link MancalaBoard} matching Game given
	 */
	public MancalaBoard getBoardByGame(Game game) {
		return boardRepository.findByGame(game);
	}

}
