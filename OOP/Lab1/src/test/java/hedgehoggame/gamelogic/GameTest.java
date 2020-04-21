package hedgehoggame.gamelogic;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	private Game game;
	
	@Before
	public void setUp() {
		game = new Game(4, 3);
		game.init();
	}

	@Test
	public void testInit() {
		assertEquals(12, game.getField().length);
		assertEquals(new ImmutablePair<Integer, Integer>(2,  0), game.getHedgehogPosition());
		assertEquals(2, game.getAppleNum());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCellIndex() {
		assertEquals(0, game.getCellIndex(0, 0));
		assertEquals(5, game.getCellIndex(1, 1));
		assertEquals(11, game.getCellIndex(2, 3));
		assertEquals(11, game.getCellIndex(-2, 3));
		assertEquals(11, game.getCellIndex(20, 3));

	}

	@Test
	public void testMoveTop() {
		game.moveTop();
		assertEquals(Cell.EMPTY, game.getField()[8]);
		assertEquals(Cell.HEDGEHOG, game.getField()[4]);
		game.moveTop();
		game.moveTop();
		assertEquals(Cell.EMPTY, game.getField()[4]);
		assertEquals(Cell.HEDGEHOG, game.getField()[0]);
	}

	@Test
	public void testMoveBottom() {
		game.moveBottom();
		assertEquals(Cell.HEDGEHOG, game.getField()[8]);
		game.moveRight();
		game.moveBottom();
		game.moveBottom();
		assertEquals(Cell.EMPTY, game.getField()[8]);
		assertEquals(Cell.HEDGEHOG, game.getField()[9]);
	}

	@Test
	public void testMoveLeft() {
		game.moveLeft();
		assertEquals(Cell.HEDGEHOG, game.getField()[8]);
		game.moveTop();
		game.moveRight();
		game.moveRight();
		game.moveLeft();
		game.moveLeft();
		game.moveLeft();
		assertEquals(Cell.EMPTY, game.getField()[8]);
		assertEquals(Cell.HEDGEHOG, game.getField()[4]);
	}

	@Test
	public void testMoveRight() {
		game.moveRight();
		assertEquals(Cell.EMPTY, game.getField()[8]);
		assertEquals(Cell.HEDGEHOG, game.getField()[9]);
	}
	
	@Test
	public void testDecreaseAppleNum() {
		game.decreaseAppleNum();
		assertEquals(1, game.getAppleNum());
		assertFalse(game.getGameEnd());
		game.decreaseAppleNum();
		assertEquals(0, game.getAppleNum());
		assertTrue(game.getGameEnd());
	}

}
