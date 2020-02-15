package com.assign.mancala.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.util.StringUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.assign.mancala.model.CustomPlayer;
import com.assign.mancala.model.Player;
import com.assign.mancala.repository.PlayerRepository;

/**
 * Class for User details
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final PlayerRepository playerRepository;

	@Autowired
	public CustomUserDetailsService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		checkNotNull(username);
		if (isEmpty(username)) {
			throw new UsernameNotFoundException("Username cannot be empty");
		}

		Player player = playerRepository.findOneByUsername(username);
		if (player == null) {
			throw new UsernameNotFoundException("Player " + username + " doesn't exist");
		}
		return new CustomPlayer(player);
	}
}