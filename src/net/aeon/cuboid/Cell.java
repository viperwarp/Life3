package net.aeon.cuboid;

public class Cell {
	public int x;
	public int y;
	public int z;
	public int face;
	public int value;
	
	public Cell() {
	}

	public Cell(int x, int y, int z, int face) {
		this(x, y, z, face, 0);
	}
	
	public Cell(int x, int y, int z, int face, int value) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.face = face;
		this.value = value;
	}
	
	public String toString() {
		return String.format("X:%d Y:%d Z:%d Face:%d = %d", x, y, z, face, value);
	}
}
