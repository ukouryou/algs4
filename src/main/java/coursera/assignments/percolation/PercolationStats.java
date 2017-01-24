package coursera.assignments.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private int trails;
	private double[] means;
	
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		if (trials < 1) {
			throw new IllegalArgumentException();
		}
		
		this.trails = trials;
		means = new double[trails];
		
		for(int i=0; i<trials; i++){
			Percolation percolation = new Percolation(n);
			while (true) {
				int posX, posY;
				do {
					posX = StdRandom.uniform(n) + 1;
					posY = StdRandom.uniform(n) + 1;
				} while (percolation.isOpen(posX, posY));
				percolation.open(posX, posY);
				means[i] += 1;
				if (percolation.percolates()) {
					break;
				}
			}
			means[i] = means[i]/(double)(n*n);
			
		}
		
	}

	// sample mean of percolation threshold
	public double mean() {
		double sum = 0.0;
		for(int i = 0; i < trails; i++) {
			sum += means[i];
		}
		
		return sum/(double)trails;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		double sigma = 0.0;
		double mu = mean();
		for(int i=0; i<trails; i++) {
			sigma += Math.pow(means[i] - mu,2);  
		}
		return Math.sqrt(sigma/(double)(trails -1));
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		double mu = mean();
		double sigma = stddev();
		return mu - 1.96 * sigma / Math.sqrt(trails);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		double mu = mean();
        double sigma = stddev();
        return mu + 1.96*sigma / Math.sqrt(trails);
	}

	// test client (described below)
	public static void main(String[] args) {
		int N = 200;
        int T = 1000;
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.printf("mean = %f\n", percStats.mean());
        StdOut.printf("stddev = %f\n", percStats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", 
                      percStats.confidenceLo(), percStats.confidenceHi());
	}
}