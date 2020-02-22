package hedgehoggame.gamelogic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

private Game game;
private Cell[][] field;
	
	@BeforeEach
	public void setUp() {
		game = new Game(5, 5);
		game.init();
		field = game.getField();
	}

	@Test
	public void initTest() {
		game.init();
		Cell[][] field = game.getField();
		assertEquals(Cell.HEDGEHOG, field[4][0]);
		
		int density = 0;
		for(int i = 0; i < 5; ++i) {
			for(int j = 0; j < 5; ++j) {
				if(field[i][j] == Cell.APPLE) {
					density++;
				}
			}
		}
		assertEquals(5, density);
		assertEquals(new Pair<Integer, Integer>(4, 0), game.getHedgehogPosition());
	}
	
	@Test
	public void moveTopTest() {
		game.moveTop();
		assertEquals(Cell.EMPTY, field[4][0]);
		assertEquals(Cell.HEDGEHOG, field[3][0]);
		assertEquals(new Pair<Integer, Integer>(3, 0), game.getHedgehogPosition());
		for(int i = 0; i < 4; ++i) {
			game.moveTop();
		}
		assertEquals(Cell.HEDGEHOG, field[0][0]);
	}
	
	@Test
	public void moveBottomTest() {
		game.moveBottom();
		assertEquals(Cell.HEDGEHOG, field[4][0]);
		assertEquals(new Pair<Integer, Integer>(4, 0), game.getHedgehogPosition());
		for(int i = 1; i <= 4; ++i) {
			game.moveTop();
		}
		game.moveBottom();
		assertEquals(Cell.HEDGEHOG, field[1][0]);
		assertEquals(new Pair<Integer, Integer>(1, 0), game.getHedgehogPosition());
	}
	
	@Test
	public void moveRightTest() {
		game.moveRight();
		assertEquals(Cell.EMPTY, field[4][0]);
		assertEquals(Cell.HEDGEHOG, field[4][1]);
		assertEquals(new Pair<Integer, Integer>(4, 1), game.getHedgehogPosition());
		for(int i = 1; i <= 5; ++i) {
			game.moveRight();
		}
		assertEquals(Cell.HEDGEHOG, field[4][4]);
		assertEquals(new Pair<Integer, Integer>(4, 4), game.getHedgehogPosition());
	}
	
	@Test
	public void moveLeftTest() {
		game.moveLeft();
		assertEquals(Cell.HEDGEHOG, field[4][0]);
		assertEquals(new Pair<Integer, Integer>(4, 0), game.getHedgehogPosition());
		for(int i = 1; i <= 5; ++i) {
			game.moveRight();
		}
		game.moveLeft();
		assertEquals(Cell.HEDGEHOG, field[4][3]);
		assertEquals(new Pair<Integer, Integer>(4, 3), game.getHedgehogPosition());
	}
}
