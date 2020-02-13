package com.assign.mancala.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assign.mancala.model.Game;
import com.assign.mancala.model.MancalaBoard;
import com.assign.mancala.model.Player;
import com.assign.mancala.service.BoardService;
import com.assign.mancala.service.GameService;
import com.assign.mancala.service.PlayService;
import com.assign.mancala.service.PlayerService;

/**
 * Gameplay related actions
 */
@RestController
@RequestMapping("/play")
public class PlayController {

	private GameService gameService;
	private PlayerService playerService;
	private BoardService boardService;
	private PlayService playService;
	private HttpSession httpSession;
	private SimpMessagingTemplate template;

	private final Logger logger = LoggerFactory.getLogger(PlayController.class);

	/**
	 * PlayController constructor
	 *
	 * @param gameService   @{@link GameService} dependency
	 * @param playerService @{@link PlayerService} dependency
	 * @param boardService  @{@link BoardService} dependency
	 * @param playService   @{@link PlayService} dependency
	 * @param httpSession   @{@link HttpSession} dependency
	 * @param template      @{@link SimpMessagingTemplate} dependency
	 */
	@Autowired
	public PlayController(GameService gameService, PlayerService playerService, BoardService boardService,
			PlayService playService, HttpSession httpSession, SimpMessagingTemplate template) {
		this.gameService = gameService;
		this.playerService = playerService;
		this.boardService = boardService;
		this.playService = playService;
		this.httpSession = httpSession;
		this.template = template;
	}

	/**
	 * REST endpoint to do a player move
	 *
	 * @param position the position of the house to start move from
	 * @return @{@link MancalaBoard} instance of the current board layout
	 */
	@RequestMapping(value = "/move/{position}", method = RequestMethod.POST)
	public MancalaBoard doMove(@PathVariable int position) {
		logger.debug("Starting move for Player");

		String playerName = "";

		if (position == 1 || position == 2 || position == 3 || position == 4 || position == 5 || position == 6) {
			playerName = "vivin";
		} else if (position == 7 || position == 8 || position == 9 || position == 10 || position == 11 || position == 12
				|| position == 13) {
			playerName = "sundar";
		}

		// Get info
		Player player = playerService.getPlayerByUsername(playerName);
		Long gameId = (Long) httpSession.getAttribute("gameId");
		Game game = gameService.getGameById(gameId);

		// Do move
		MancalaBoard board = playService.doMove(game, player, position);

		// Notify players of board change
		template.convertAndSend("/update/position/" + gameId.toString(), "moved");

		// Notify lobby if game is finished (to remove from playable games
		if (game.getState() == Game.State.GAME_OVER) {
			template.convertAndSend("/update/home", "update");
		}

		return board;
	}

	/**
	 * REST endpoint to get the current players turn
	 *
	 * @return @{@link Player} instance of the player turn
	 */
	@RequestMapping(value = "/turn", produces = MediaType.APPLICATION_JSON_VALUE)
	public Player getPlayerTurn() {
		logger.debug("Getting player turn");

		// Get info
		Long gameId = (Long) httpSession.getAttribute("gameId");
		Game game = gameService.getGameById(gameId);

		// Return turn
		return game.getPlayerTurn();
	}

	/**
	 * REST endpoint to get the logged in player's score
	 *
	 * @return @{@link Integer} of the score
	 */
	@RequestMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer getScore(@PathVariable String playerName) {
		logger.debug("Getting player score");

		// Get Info
		Player player = playerService.getPlayerByUsername(playerName);
		Long gameId = (Long) httpSession.getAttribute("gameId");
		Game game = gameService.getGameById(gameId);

		// Return score
		return playService.getScore(game, player);
	}

	/**
	 * REST endpoint to get the game state
	 *
	 * @return @{@link Game.State} of the current game
	 */
	@RequestMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
	public Game.State getState() {
		logger.debug("Getting game state");

		// Get info
		Long gameId = (Long) httpSession.getAttribute("gameId");
		Game game = gameService.getGameById(gameId);

		// Return state
		return game.getState();
	}

	/**
	 * REST endpoint to get the board info
	 *
	 * @return @{@link MancalaBoard} of the current board
	 */
	@RequestMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
	public MancalaBoard getBoard() {
		logger.debug("Retrieving game board");

		// Get info
		Long gameId = (Long) httpSession.getAttribute("gameId");
		Game game = gameService.getGameById(gameId);
		MancalaBoard board = boardService.getBoardByGame(game);

		// Return board
		return board;
	}
}
