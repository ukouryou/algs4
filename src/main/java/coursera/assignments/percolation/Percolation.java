package coursera.assignments.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	//used for percolates
	private WeightedQuickUnionUF uf;
	// used for isFull
	private WeightedQuickUnionUF wquUFTop;
	//used for isOpen
	private boolean[] matrix; 
	private int size;
	private int[] dx = {1,-1,0,0};
	private int[] dy = {0,0,1,-1};
	
	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Illegal argument exception!!");
		}
		wquUFTop = new WeightedQuickUnionUF(n*n + 1);
		uf = new WeightedQuickUnionUF(n*n + 2);
		matrix = new boolean[n*n + 1];
		size = n;
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		validate(row, col);
		int curIndex = (row-1)*size + col;
		matrix[curIndex] = true;
		if(1 == row) {
			wquUFTop.union(curIndex, 0);
			uf.union(curIndex, 0);
		}
		if (size == row) {
			uf.union(curIndex, size*size + 1);
		}
		
		for(int dir=0; dir < 4; dir++) {
			int posX = row + dx[dir];
			int posY = col + dy[dir];
			System.out.println(String.format("X:%s,Y:%s", posX,posY));
			if (posX<=size&&posX>=1&&posY<=size&&posY>=1&&isOpen(posX, posY)) {
				int posXY = (posX-1)*size + posY;
				wquUFTop.union(curIndex,posXY);
				uf.union(curIndex, posXY);
			}
		}
		
	}
	
	private void validate(int row, int col) {
		if (row < 1 || row > size) {
			throw new IndexOutOfBoundsException("row index out of boundary");
		}
		if (col < 1 || col > size) {
			throw new IndexOutOfBoundsException("col index out of boundary");
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		validate(row, col);
		int curIndex = (row-1)*size + col;
		return matrix[curIndex];
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		validate(row, col);
		int curIndex = (row-1)*size + col;
		return wquUFTop.connected(curIndex, 0);
	}

	// does the system percolate?
	public boolean percolates() {
		return uf.connected(0, size*size + 1);
	}

	// test client (optional)
	public static void main(String[] args) {
		Percolation perc = new Percolation(2);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 1);
        System.out.println(perc.percolates());
	}
}