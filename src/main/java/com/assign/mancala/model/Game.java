package com.assign.mancala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Game entity DTO class
 */
@Entity
@Table(name = "game", catalog = "mancala_game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	/**
	 * Enum for the current state of the game
	 */
	public enum State {
		GAME_IN_PROGRESS, WAIT_FOR_OPPONENT, GAME_OVER
	}

	@Column(name = "state", nullable = false)
	private State state;

	@ManyToOne
	@JoinColumn(name = "first_player_id", nullable = false)
	private Player firstPlayer;

	@ManyToOne
	@JoinColumn(name = "second_player_id", nullable = true)
	private Player secondPlayer;

	@ManyToOne
	@JoinColumn(name = "player_turn_id", nullable = true)
	private Player playerTurn;

	public Game(Player firstPlayer, Player playerTurn, State state) {
		this.firstPlayer = firstPlayer;
		this.playerTurn = playerTurn;
		this.state = state;
	}

	public Game(Long id, Player firstPlayer, Player secondPlayer, Player playerTurn, State state) {
		this.id = id;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.playerTurn = playerTurn;
		this.state = state;
	}

	public Game() {

	}

	public Long getId() {
		return id;
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(Player secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public Player getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(Player playerTurn) {
		this.playerTurn = playerTurn;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
