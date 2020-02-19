package com.assign.mancala.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Mancala Game Board model.
 */
@Entity
@Table(name = "mancala_board", catalog = "mancala_game")
public class MancalaBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "game_id", nullable = false)
	private Game game;

	@OneToMany(mappedBy = "gameBoard")
	private List<Pit> pits;

	public MancalaBoard(Game game) {
		this.game = game;
	}

	public MancalaBoard() {

	}

	public void setId(int id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}
}
