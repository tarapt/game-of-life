/**
 * 
 * @author TaraPrasad
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
	 * row-1 column-1 row-1 row-1 column+1
	 * 
	 * column-1 column+1
	 * 
	 * row+ column+1 row+1 row+1 column+1
	 */
	private void calculateNeighbors() {
		int n = currentGeneration.length;
		int m = currentGeneration[0].length;
		for (int row = 0; row < n; row++) {
			for (int column = 0; column < m; column++) {
				neighborCount[row][column] = 0;
				// Row and Column Bounds should be checked first
				if (currentGeneration[(row - 1 + n) % n][(column - 1 + m) % m] == true)// up-left neighbor
					neighborCount[row][column]++;
				if (currentGeneration[(row - 1 + n) % n][column] == true)// up neighbor
					neighborCount[row][column]++;
				if (currentGeneration[row][(column - 1 + m) % m] == true)// left neighbor
					neighborCount[row][column]++;
				if (currentGeneration[(row + 1) % n][(column - 1 + m) % m] == true)// left-down neighbor
					neighborCount[row][column]++;
				if (currentGeneration[(row - 1 + n) % n][(column + 1) % m] == true)// right-up neighbor
					neighborCount[row][column]++;
				if (currentGeneration[(row + 1) % n][column] == true)// down neighbor
					neighborCount[row][column]++;
				if (currentGeneration[row][(column + 1) % m] == true)// right neighbor
					neighborCount[row][column]++;
				if (currentGeneration[(row + 1) % n][(column + 1) % m] == true) // right-down neighbor
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
