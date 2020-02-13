package com.assign.mancala;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assign.mancala.controller.PlayerController;
import com.assign.mancala.model.Player;
import com.assign.mancala.object.PlayerDTO;
import com.assign.mancala.repository.PlayerRepository;
import com.assign.mancala.service.BoardService;
import com.assign.mancala.service.GameService;
import com.assign.mancala.service.PitService;
import com.assign.mancala.service.PlayService;
import com.assign.mancala.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService gameService;

	@MockBean
	private BoardService boardService;

	@MockBean
	private PlayerService playerService;

	@MockBean
	private PitService pitService;

	@MockBean
	private PlayService playService;

	@MockBean
	private PlayerRepository playerRepository; // Needed for securitycontext

	@Test
	@WithMockUser(username = "vivin", password = "vivin") // Needed because MockMVC does not use SecurityConfig
	public void testCreateUser() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();
		Player player = new Player("vivin", "vivin");
		PlayerDTO playerDTO = new PlayerDTO();

		when(playerService.createPlayer(any(PlayerDTO.class))).thenReturn(player);

		this.mockMvc
				.perform(post("/player/create").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objMapper.writeValueAsString(playerDTO)).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(objMapper.writeValueAsString(player))));
	}

}
