/**
 * 
 */
package net.aeon.cuboid;

/**
 * @author Affian
 *
 */
public class CuboidRun {
	
	private CuboidArray cube;

	/**
	 * 
	 */
	public CuboidRun() {
		cube = new CuboidArray(3);
	}
	
	public int runTest() {
		
		
//		printFace(cube.getFace(CuboidArray.FACE_Z, true));
//		printFace(cube.getFace(CuboidArray.FACE_Z, false));
//		printFace(cube.getFace(CuboidArray.FACE_X, true));
//		printFace(cube.getFace(CuboidArray.FACE_X, false));
//		printFace(cube.getFace(CuboidArray.FACE_Y, true));
//		printFace(cube.getFace(CuboidArray.FACE_Y, false));
		

		
		
//		int i = 0;
//		for (Cell cell:cube) {
//			i++;
//			System.out.println(String.format("X:%d Y:%d Z:%d Face:%d = %d", cell.x, cell.y, cell.z, cell.face, cell.value));
//		}
//		
//		System.out.println("Total cells: " + i);
		
		
		 return 0;
	}

	@SuppressWarnings("unused")
	private void printFace(Cell[][] face) {
		for (int i = 0; i < cube.getSize(); i++) {
			String output = "";
			for (int j = 0; j < cube.getSize(); j++ ) {
				output += face[i][j].value;
			}
			System.out.println(output);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CuboidRun test = new CuboidRun();
		test.runTest();
	}

}
