/**
 * 
 * @author TaraPrasad, Aneesh, Sourabh
 *
 */
public class Generation {
	private boolean[][] currentGeneration;
	private boolean[][] nextGeneration;
	private int[][] neighborCount;// For the current generation

	public Generation(boolean[][] cells) {
		currentGeneration = cells;
		neighborCount = new int[currentGeneration.length][currentGeneration[0].length];
		nextGeneration = new boolean[currentGeneration.length][currentGeneration[0].length];
	}

	private void calculateNextGeneration() {
		for (int row = 0; row < currentGeneration.length; row++) {
			for (int column = 0; column < currentGeneration[0].length; column++) {
				switch (neighborCount[row][column]) {
				case 0:
					nextGeneration[row][column] = false;
					break;
				case 1:
					nextGeneration[row][column] = false;
					break;
				case 2:
					nextGeneration[row][column] = currentGeneration[row][column];
					break;
				case 3:
					nextGeneration[row][column] = true;
					break;
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					nextGeneration[row][column] = false;
					break;
				}
			}
		}
	}
	/**
	 * row-1 column-1	row-1		row-1 column+1
	 * 
	 * column-1							  column+1
	 * 
	 * row+ column+1	row+1		row+1 column+1
	 */
	private void calculateNeighbors() {
		for (int row = 0; row < currentGeneration.length; row++) {
			for (int column = 0; column < currentGeneration[0].length; column++) {
				neighborCount[row][column] = 0;
				// Row and Column Bounds should be checked first
				if (row > 0 && column > 0 && currentGeneration[row - 1][column - 1] == true)// up-left
																							// neighbor
					neighborCount[row][column]++;
				if (row > 0 && currentGeneration[row - 1][column] == true)// up
																			// neighbor
					neighborCount[row][column]++;
				if (column > 0 && currentGeneration[row][column - 1] == true)// left
																				// neighbor
					neighborCount[row][column]++;
				if (row < (currentGeneration.length - 1) && column > 0 && currentGeneration[row + 1][column - 1] == true)// left-down
																												// neighbor
					neighborCount[row][column]++;
				if (row > 0 && column < (currentGeneration[0].length - 1) && currentGeneration[row - 1][column + 1] == true)// right-up
																												// neighbor
					neighborCount[row][column]++;
				if (row < (currentGeneration.length - 1) && currentGeneration[row + 1][column] == true)// down
																							// neighbor
					neighborCount[row][column]++;
				if (column < (currentGeneration[0].length - 1) && currentGeneration[row][column + 1] == true)// right
																									// neighbor
					neighborCount[row][column]++;
				if (row < (currentGeneration.length - 1) && column < (currentGeneration[0].length - 1)// right-down
																				// neighbor
						&& currentGeneration[row + 1][column + 1] == true)
					neighborCount[row][column]++;
			}
		}
	}

	public boolean[][] getCurrentGeneration() {
		return currentGeneration;
	}

	public void setCurrentGeneration(boolean[][] currentGeneration) {
		this.currentGeneration = currentGeneration;
	}

	public boolean[][] getNextGeneration() {
		calculateNeighbors();
		calculateNextGeneration();// next generation is initialized
		currentGeneration = nextGeneration;
		return nextGeneration;
	}

	public void setNextGeneration(boolean[][] nextGeneration) {
		this.nextGeneration = nextGeneration;
	}
}
