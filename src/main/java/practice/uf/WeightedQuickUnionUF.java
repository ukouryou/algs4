package practice.uf;

public class WeightedQuickUnionUF {
	
	private int[] size;
	private int[] parent;
	//component count in UF
	private int count;
	
	public WeightedQuickUnionUF(int n) {
		count = n;
		parent = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}
	
	public int find(int p) {
		while(p != parent[p]) {
//			parent[p] = parent[parent[p]];
			p = parent[p];
		}
		return p;
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) {
			return;
		}
		if (size[rootP] < size[rootQ]) {
			parent[rootP] = rootQ;
			size[rootQ] += size[rootP];
		} else {
			parent[rootQ] = rootP;
			size[rootP] += size[rootQ];
		}
		count--;
	}
	
	public int count() {
		return count;
	}
	
	public static void main(String[] args) {
		//test
	}

}
