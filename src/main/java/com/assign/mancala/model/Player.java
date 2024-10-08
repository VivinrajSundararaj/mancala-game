package com.assign.mancala.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Player Data Transfer Object.
 */
@Entity
@Table(name = "player", catalog = "mancala_game")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "user_name", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Player(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Player() {
	}
}
