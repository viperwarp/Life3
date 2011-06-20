/**
 * 
 */
package net.aeon.life3.slick;

import net.aeon.cuboid.Cell;
import net.aeon.cuboid.CuboidArray;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Main class for displaying the CuboidArray as a 2D net using
 * Slick2D as a simple 2D graphics layer
 * @author Affian
 */
public class CuboidNet extends BasicGame {
	
	private SpriteSheet tiles;
	private CuboidArray cube;
	private CuboidArray otherCube;
	private CuboidArray currentCube;
	private int timer;
	private boolean advance = false;
	private Cell currentCell = null;

	/**
	 * 
	 * @param name
	 */
	public CuboidNet(String name) {
		super(name);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		tiles = new SpriteSheet("res/cells.png", 16, 16);
		timer = 0;
		cube = new CuboidArray(10);
		otherCube = new CuboidArray(10);
		
//		/* The F-Pentomino */
//		cube.setCellValue(4, 4, 0, CuboidArray.FACE_Z, 1);
//		cube.setCellValue(3, 4, 0, CuboidArray.FACE_Z, 1);
//		cube.setCellValue(4, 5, 0, CuboidArray.FACE_Z, 1);
//		cube.setCellValue(4, 3, 0, CuboidArray.FACE_Z, 1);
//		cube.setCellValue(5, 3, 0, CuboidArray.FACE_Z, 1);
		
		/* Lightweight spaceship */
		cube.setCellValue(3, 2, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(2, 3, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(2, 4, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(2, 5, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(3, 5, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(4, 5, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(5, 5, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(6, 2, 0, CuboidArray.FACE_Z, 1);
		cube.setCellValue(6, 4, 0, CuboidArray.FACE_Z, 1);
		
		/* Walking the cube */
//		setCurrentCell(cube.getCell(4, 4, 0, CuboidArray.FACE_Z));
		
		currentCube = cube;
		
		otherCube = new CuboidArray(10);
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		timer += delta;
		if (timer > 50) {
			for (Cell cell:currentCube) {
				otherCube.setCellValue(cell.x, cell.y, cell.z, cell.face, newValueForCell(cell));
			}
			cube = currentCube;
			currentCube = otherCube;
			otherCube = cube;
			timer = 0;
			advance = false;
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		final int faceSize = 16 * currentCube.getSize();
		
		tiles.startUse();
		printFace(currentCube.getFace(CuboidArray.FACE_Z, true), faceSize, 0);
		printFace(currentCube.getFace(CuboidArray.FACE_X, true), 0, faceSize);
		printFace(currentCube.getFace(CuboidArray.FACE_Y, false), faceSize, faceSize);
		printFace(currentCube.getFace(CuboidArray.FACE_X, false), faceSize * 2, faceSize);
		printFace(currentCube.getFace(CuboidArray.FACE_Z, false), faceSize, faceSize * 2);
		printFace(currentCube.getFace(CuboidArray.FACE_Y, true), faceSize, faceSize * 3);
		tiles.endUse();
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.BasicGame#keyPressed(int, char)
	 */
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_SPACE) {
			advance = true;
		}
		if (currentCell != null) {
			switch(key) {
			case Input.KEY_LEFT:
				setCurrentCell(cube.getAdjacentCells(currentCell)[0]);
				break;
			case Input.KEY_RIGHT:
				setCurrentCell(cube.getAdjacentCells(currentCell)[4]);
				break;
			case Input.KEY_UP:
				setCurrentCell(cube.getAdjacentCells(currentCell)[2]);
				break;
			case Input.KEY_DOWN:
				setCurrentCell(cube.getAdjacentCells(currentCell)[6]);
				break;
			}
		}
		super.keyPressed(key, c);
	}
	
	private void setCurrentCell(Cell cell) {
		if (currentCell != null) {
			cube.setCellValue(currentCell.x, currentCell.y, currentCell.z, currentCell.face, 0);
			for (Cell i:cube.getAdjacentCells(currentCell)) {
				if (i != null)
					cube.setCellValue(i.x, i.y, i.z, i.face, 0);
			}
		}
		
		currentCell = cell;
		cube.setCellValue(currentCell.x, currentCell.y, currentCell.z, currentCell.face, 1);
		for (Cell i:cube.getAdjacentCells(currentCell)) {
			if (i != null)
				cube.setCellValue(i.x, i.y, i.z, i.face, 2);
		}
	}

	/**
	 * Renders a single face of the CuboidArray as Tiles
	 * @param face A 2D array of cells that make up one face of the CuboidArray
	 * @param x X offset to render face at
	 * @param y Y offset to render face at
	 */
	private void printFace(Cell[][] face, int x, int y) {
		for (int i = 0; i < currentCube.getSize(); i++) {
			for (int j = 0; j < currentCube.getSize(); j++ ) {
				int value = face[i][j].value;
				tiles.renderInUse(x + (16 * j), y + (16 * i), value, 0);
				//tiles.getSprite(value + 6, 1).draw(x + (16 * j), y + (16 * i));
			}
		}
		
	}
	
	/**
	 * calculates the state of the given cell in the current cube for the next
	 * generation of life
	 * @param cell the cell to set the value for
	 * @return either 0 or 1 as defined in the standard rules of Conway's Game of Life 
	 */
	private int newValueForCell(Cell cell) {
		Cell cells[] = currentCube.getAdjacentCells(cell);
		int pop = 0;
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] != null && cells[i].value == 1)pop++;
		}
		if(cell.value == 1) {
			if (pop < 2 || pop > 3)
				return 0;
		} else if(pop == 3) {
			return 1;
		}
		return cell.value;
	}

	/**
	 * Program entry point
	 * @param args not used
	 */
	public static void main(String[] args) {
		try { 
		    AppGameContainer container = new AppGameContainer(new CuboidNet("CuboidNet")); 
		    container.setDisplayMode(1024,768,false); 
		    container.start(); 
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
	
	}

}
