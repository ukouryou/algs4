package practice.uf.sort;

import java.util.Comparator;

public class Sorting {
	
	private static final int CUTOFF = 7;  // cutoff to insertion sort
	private static final int INSERTION_SORT_CUTOFF = 8;
	
	public static void selection(Object[] a, Comparator comparator) {
		int n = a.length;
		for(int i = 0; i < n; i++) {
			int min = i;
			for (int j = i; j < n; j++) {
				if (less(comparator,a[j],a[min])) {
					min = j;
				}
			}
			exch(a,i,min);
		}
	}
	
	public static void insertion(Object[] a, Comparator comparator) {
		int n = a.length;
		for(int i = 1; i < n; i++) {
			for(int j = i; j > 0 && less(comparator, a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
	}
	
	public static void insertion(Object[] a, int lo, int hi, Comparator comparator) {
		for(int i = lo; i <= hi; i++) {
			for(int j = i; j > lo && less(comparator, a[j], a[j-1]); j--) {
				exch(a, j, j-1);
			}
		}
	}
	
	public static void shell(Object[] a, Comparator comparator) {
		int n = a.length;
		// computer increment sequence
		int h = 1;
		while(h < n/3) {
			h = h*3 +1;
		}
		
		while(h >=1) {
			// h-sorting
			for(int i = h; i< n; i++) {
				for(int j = i;j >=h && less(comparator, a[j], a[j-h]);j-=h) {
					exch(a, j, j-h);
				}
			}
			h = h/3;
		}

	}
	
	public static void sortmerge(Object[] a, Comparator comparator) {
		Object[] aux = a.clone();
		sortmerge(aux,a,0,a.length-1,comparator);
	}

	private static void sortmerge(Object[] src, Object[] dst, int lo, int hi, Comparator comparator) {
		if (hi <= lo + CUTOFF) {
			insertion(dst, lo, hi, comparator);
			return;
		}
		int mid = lo + (hi-hi)/2;
		sortmerge(dst, src, lo, mid, comparator);
		sortmerge(dst, src, mid+1, hi, comparator);
		if (!less(comparator, src[mid+1], src[mid])) {
			System.arraycopy(src, lo, dst, lo, hi-lo+1);
			return;
		}
		merge(src, dst, lo, mid, hi, comparator);
	}

	private static void merge(Object[] src, Object[] dst, int lo, int mid, int hi, Comparator comparator) {
		int i = lo;
		int j = mid + 1;
		for(int k = i; k<=hi;k++) {
			if(i>mid) dst[k] = src[j++];
			else if(j>hi) dst[k] = src[i++];
			else if(less(comparator, src[j], src[i])) dst[k] = src[j++];
			else dst[k] = src[i++];
		}
	}
	
	private static void mergeBU(Object[] a , Object[] aux, int lo, int mid, int hi, Comparator comparator) {
		// copy to aux[]
		for(int k=lo; k<=hi ;k++) {
			aux[k] = a[k];
		}
		
		int i = lo;
		int j = mid + 1;
		for(int k = i; k<=hi;k++) {
			if(i>mid) a[k] = aux[j++];
			else if(j>hi) a[k] = aux[i++];
			else if(less(comparator, aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
	}
	
	public static void sortmergeBU(Object[] a, Comparator comparator) {
		int n = a.length;
		Object[] aux = new Object[n];
		for(int len=1;len<n;len+=len) {
			for(int lo=0;lo<n-len;lo+=len+len) {
				int mid = lo+len-1;
				int hi = Math.min(lo+len+len-1, n-1);
				mergeBU(a, aux, lo, mid, hi, comparator);
			}
		}
	}
	
	
	public static void sortquick(Object[]a, Comparator comparator) {
		sortquick(a, 0, a.length-1, comparator);
	}
	

	private static void sortquick(Object[] a, int lo, int hi, Comparator comparator) {
		int len = hi - lo + 1;
		/*if (len < INSERTION_SORT_CUTOFF) {
			insertion(a, lo, hi, comparator);
			return;
		}*/
		
		if (hi <= lo) return;
		int pivot = partiation(a, lo, hi, comparator);
		sortquick(a, lo, pivot, comparator);
		sortquick(a, pivot+1, hi, comparator);
	}

	private static int partiation(Object[] a, int lo, int hi, Comparator comparator) {
		int i = lo;
		int j = hi + 1;
		Object v = a[lo];
		
		while(true) {
			
			while(less(comparator, a[++i], v)) {
				if (i==hi) break;
			}
			
			while(less(comparator, v, a[--j])){}
			
			if (i>=j) break;
			exch(a, i, j);
		}
		
		exch(a, lo, j);
		return j;
	}

	private static void exch(Object[] a, int i, int min) {
		Object swap = a[i];
		a[i] = a[min];
		a[min] = swap;
	}

	private static boolean less(Comparator comparator, Object v, Object w) {
		return comparator.compare(v, w) < 0;
	}
	
	public static void print(Object[] a){
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + "	");
		}
		System.out.println();
	}
	
	public static class IntegerComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	}
	
	
	public static void main(String[] args) {
		Integer[] a = {5,2,10,7,6,9,3,1,0,8,4,11};
		Comparator<Integer> comparator = new IntegerComparator();
//		selection(a,new IntegerComparator());
//		insertion(a, comparator);
//		insertion(a, 0, 10,comparator);
//		shell(a, comparator);
//		sortmerge(a, comparator);
//		sortmergeBU(a, comparator);
		sortquick(a, comparator);
		print(a);
	}
	
	

}
