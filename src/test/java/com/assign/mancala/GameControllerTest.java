package com.assign.mancala;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assign.mancala.controller.GameController;
import com.assign.mancala.model.Game;
import com.assign.mancala.model.MancalaBoard;
import com.assign.mancala.model.Pit;
import com.assign.mancala.model.Player;
import com.assign.mancala.repository.PlayerRepository;
import com.assign.mancala.service.BoardService;
import com.assign.mancala.service.GameService;
import com.assign.mancala.service.PitService;
import com.assign.mancala.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {
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
	private PlayerRepository playerRepository;

	@MockBean
	private SimpMessagingTemplate templateMock;

	@Test
	@WithMockUser(username = "vivin", password = "vivin")
	public void testListGamesToJoin() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();

		Player player = new Player("vivin", "vivin");
		Long gameId = 1L;
		Game gameOne = new Game(gameId, player, null, player, Game.State.WAIT_FOR_OPPONENT);
		List<Game> games = new ArrayList<>();
		games.add(gameOne);

		when(playerService.getPlayerByUsername("vivin")).thenReturn(player);
		when(gameService.getGamesToJoin(player)).thenReturn(games);

		this.mockMvc.perform(get("/game/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(objMapper.writeValueAsString(games))));
	}

	@Test
	@WithMockUser(username = "vivin", password = "vivin")
	public void testListGamesActive() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();

		Player player = new Player("vivin", "vivin");
		Player playerTwo = new Player("sundar", "sundar");
		Long gameId = 1L;
		Game gameOne = new Game(gameId, player, playerTwo, player, Game.State.GAME_IN_PLAY);
		List<Game> games = new ArrayList<>();
		games.add(gameOne);

		when(playerService.getPlayerByUsername("vivin")).thenReturn(player);
		when(gameService.getPlayerGames(player)).thenReturn(games);

		this.mockMvc.perform(get("/game/player/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(objMapper.writeValueAsString(games))));
	}

	@Test
	@WithMockUser(username = "vivin", password = "vivin")
	public void testSelectGame() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();

		Player player = new Player("vivin", "vivin");
		Player playerTwo = new Player("sundar", "sundar");

		Long gameId = 1L;
		Game gameOne = new Game(gameId, player, playerTwo, player, Game.State.GAME_IN_PLAY);

		when(gameService.getGameById(gameId)).thenReturn(gameOne);

		this.mockMvc.perform(get("/game/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(objMapper.writeValueAsString(gameOne))));
	}

	@Test
	@WithMockUser(username = "sundar", password = "sundar")
	public void testJoinGame() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();

		Player player = new Player("vivin", "sundar");
		Player playerTwo = new Player("sundar", "sundar");
		Long gameId = 1L;
		Game gameOne = new Game(gameId, player, playerTwo, player, Game.State.GAME_IN_PLAY);

		when(playerService.getPlayerByUsername("sundar")).thenReturn(playerTwo);
		when(gameService.joinGame(playerTwo, gameId)).thenReturn(gameOne);
		this.mockMvc.perform(post("/game/join/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(objMapper.writeValueAsString(gameOne))));
	}

	@Test
	@WithMockUser(username = "vivin", password = "vivin")
	public void testCreateGame() throws Exception {
		ObjectMapper objMapper = new ObjectMapper();

		Player player1 = new Player("vivin", "vivin");
		Player player2 = new Player("sundar", "sundar");
		Long gameId = 1L;
		Game gameOne = new Game(gameId, player1, null, player2, Game.State.WAIT_FOR_OPPONENT);

		MancalaBoard boardMock = mock(MancalaBoard.class);

		when(playerService.getPlayerByUsername("vivin")).thenReturn(player1);
		when(playerService.getPlayerByUsername("sundar")).thenReturn(player2);
		when(gameService.createNewGame(player1, player2)).thenReturn(gameOne);
		when(boardService.createNewBoard(gameOne)).thenReturn(boardMock);

		this.mockMvc.perform(post("/game/create")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(objMapper.writeValueAsString(gameOne))));

		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.LARGE), eq(7), eq(0));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.LARGE), eq(14), eq(0));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(1), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(2), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(3), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(4), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(5), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(6), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(8), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(9), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(10), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(11), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(12), eq(6));
		verify(pitService, times(1)).createPit(eq(boardMock), eq(Pit.PitType.SMALL), eq(13), eq(6));

	}
}