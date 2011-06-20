package net.aeon.cuboid;

import java.util.ArrayList;
import java.util.Iterator;

import net.aeon.cuboid.Cell;


/**
 * @author Affian
 *
 */

public class CuboidArray implements Iterable<Cell> {
	
	public static final int FACE_X = 0;
	public static final int FACE_Y = 1;
	public static final int FACE_Z = 2;

	private int[][][][] data;
	private int size;
	
	/**
	 * 
	 * @param size
	 */
	public CuboidArray(int size) {
		this.size = size;
		this.data = new int[size][size][size][3];
		initArray();
	}
	
	/**
	 * 
	 */
	private void initArray() {
		int s = size - 1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				/*   X  Y  Z   */
				data[i][j][0][FACE_Z] = 0;//1;
				data[i][j][s][FACE_Z] = 0;//2;
				
				data[0][j][i][FACE_X] = 0;//3;
				data[s][j][i][FACE_X] = 0;//4;
				
				data[i][0][j][FACE_Y] = 0;//5;
				data[i][s][j][FACE_Y] = 0;//6;
			}
		}
	}
	
	/**
	 * 
	 * @param face
	 * @param front
	 * @return a 2D array containing the cells on the selected face
	 */
	public Cell[][] getFace(int face, boolean front) {
		Cell faceData[][] = new Cell[size][size];
		int side = (front)?0:size-1;
		switch (face) {
		case FACE_Z:
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (front)
						faceData[i][j] = getCell(j,i,side,FACE_Z);
					else
						faceData[j][i] = getCell(i,j,side,FACE_Z);
				}
			}
			break;
		case FACE_X:
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (front)
						faceData[i][j] = getCell(side,j,i,FACE_X);
					else
						faceData[i][j] = getCell(side,j,i,FACE_X);	
				}
			}
			break;
		case FACE_Y:
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					faceData[i][j] = getCell(i,side,j,FACE_Y);
				}
			}
			break;
		}
		
		return faceData;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param face
	 * @return value of the cell at the given location
	 * @throws IndexOutOfBoundsException
	 */
	public int getValueForCell(int x, int y, int z, int face) throws IndexOutOfBoundsException {
		return data[x][y][z][face];
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param face
	 * @param value
	 * @throws IndexOutOfBoundsException
	 */
	public void setCellValue(int x, int y, int z, int face, int value) throws IndexOutOfBoundsException {
		//if (isLocationValid(x, y, z, face)) {
			data[x][y][z][face] = value;
		//}
		//throw new IndexOutOfBoundsException(
		//	String.format("Cell at location x:%d y:%d z:%d face:%d is invalid", x, y, z, face)
		//);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param face
	 * @return Cell at the location given
	 */
	public Cell getCell(int x, int y, int z, int face) {
		if (isLocationValid(x, y, z, face)) {
			Cell cell = new Cell(x, y, z, face, data[x][y][z][face]);
			return cell;
		}
		throw new IndexOutOfBoundsException(
			String.format("Cell at location x:%d y:%d z:%d face:%d is invalid", x, y, z, face)
		);
	}
	
	/**
	 * 
	 * @param cell
	 */
	public void setCell(Cell cell) {
		if (isLocationValid(cell.x, cell.y, cell.z, cell.face)) {
			data[cell.x][cell.y][cell.z][cell.face] = cell.value;
		}
		throw new IndexOutOfBoundsException(
			String.format("Cell at location x:%d y:%d z:%d face:%d is invalid", cell.x, cell.y, cell.z, cell.face)
		);
	}
	
	/**
	 * 
	 * @param cell
	 * @return an array of size 8 containing all adjacent cells including diagonals
	 */
	public Cell[] getAdjacentCells(Cell cell) {
		Cell cells[] = new Cell[8];
		switch(cell.face) {
		case FACE_X:
			cells[0] = getZYDirectionalCell(cell, -1, 0);	//left
			cells[1] = getZYDirectionalCell(cell, -1, -1);	//left-up
			cells[2] = getZYDirectionalCell(cell, 0, -1);	//up
			cells[3] = getZYDirectionalCell(cell, 1, -1);	//right-up
			cells[4] = getZYDirectionalCell(cell, 1, 0);	//right
			cells[5] = getZYDirectionalCell(cell, 1, 1);	//right-down
			cells[6] = getZYDirectionalCell(cell, 0, 1);	//down
			cells[7] = getZYDirectionalCell(cell, -1, 1);	//left-down
			break;
		case FACE_Y:
			cells[0] = getXZDirectionalCell(cell, -1, 0);
			cells[1] = getXZDirectionalCell(cell, -1, -1);
			cells[2] = getXZDirectionalCell(cell, 0, -1);
			cells[3] = getXZDirectionalCell(cell, 1, -1);
			cells[4] = getXZDirectionalCell(cell, 1, 0);
			cells[5] = getXZDirectionalCell(cell, 1, 1);
			cells[6] = getXZDirectionalCell(cell, 0, 1);
			cells[7] = getXZDirectionalCell(cell, -1, 1);
			break;
		case FACE_Z:
			cells[0] = getXYDirectionalCell(cell, -1, 0);
			cells[1] = getXYDirectionalCell(cell, -1, -1);
			cells[2] = getXYDirectionalCell(cell, 0, -1);
			cells[3] = getXYDirectionalCell(cell, 1, -1);
			cells[4] = getXYDirectionalCell(cell, 1, 0);
			cells[5] = getXYDirectionalCell(cell, 1, 1);
			cells[6] = getXYDirectionalCell(cell, 0, 1);
			cells[7] = getXYDirectionalCell(cell, -1, 1);
			break;
		}
		return cells;
	}

	private Cell getXYDirectionalCell(Cell cell, int x, int y) {
		if (cell.x + x < size && cell.x + x >= 0 && cell.y + y < size && cell.y + y >= 0) {
			return getCell(cell.x + x, cell.y + y, cell.z, cell.face);
		} else if ((cell.x + x >= size || cell.x + x < 0) && (cell.y + y < size && cell.y + y >= 0)) {
			return getCell(cell.x, cell.y + y, cell.z, FACE_X);
		} else if ((cell.y + y >= size || cell.y + y < 0) && (cell.x + x < size && cell.x + x >= 0)) {
			return getCell(cell.x + x, cell.y, cell.z, FACE_Y);
		}
		return null; //should return null if the diagonal cell falls off the corner of the cube
	}
	
	private Cell getZYDirectionalCell(Cell cell, int z, int y) {
		if (cell.z + z < size && cell.z + z >= 0 && cell.y + y < size && cell.y + y >= 0) {
			return getCell(cell.x, cell.y + y, cell.z + z, cell.face);
		} else if ((cell.z + z >= size || cell.z + z < 0) && (cell.y + y < size && cell.y + y >= 0)) {
			return getCell(cell.x, cell.y + y, cell.z, FACE_Z);
		} else if ((cell.y + y >= size || cell.y + y < 0) && (cell.z + z < size && cell.z + z >= 0)) {
			return getCell(cell.x, cell.y, cell.z + z, FACE_Y);
		}
		return null; //should return null if the diagonal cell falls off the corner of the cube
	}
	
	private Cell getXZDirectionalCell(Cell cell, int x, int z) {
		if (cell.x + x < size && cell.x + x >= 0 && cell.z + z < size && cell.z + z >= 0) {
			return getCell(cell.x + x, cell.y, cell.z + z, cell.face);
		} else if ((cell.x + x >= size || cell.x + x < 0) && (cell.z + z < size && cell.z + z >= 0)) {
			return getCell(cell.x, cell.y, cell.z + z, FACE_X);
		} else if ((cell.z + z >= size || cell.z + z < 0) && (cell.x + x < size && cell.x + x >= 0)) {
			return getCell(cell.x + x, cell.y, cell.z, FACE_Z);
		}
		return null; //should return null if the diagonal cell falls off the corner of the cube
	}
	
	/**
	 * 
	 * @return width of cube
	 */
	public int getSize() {
		return size;
	}
	
	private boolean isLocationValid(int x, int y, int z, int face) {
		switch(face) {
		case FACE_X:
			if (x == 0 || x == size - 1) return true;
		case FACE_Y:
			if (y == 0 || y == size - 1) return true;
		case FACE_Z:
			if (z == 0 || z == size - 1) return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return data.toString();
	}

	/**
	 * 
	 */
	@Override
	public Iterator<Cell> iterator() {
		
		ArrayList<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				list.add(getCell(i,j,0,FACE_Z));
				list.add(getCell(i,j,size-1,FACE_Z));
				
				list.add(getCell(0,j,i,FACE_X));
				list.add(getCell(size-1,j,i,FACE_X));
				
				list.add(getCell(i,0,j,FACE_Y));
				list.add(getCell(i,size-1,j,FACE_Y));
			}
		}
		return list.iterator();
	}


}
