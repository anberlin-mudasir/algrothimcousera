//No. 14466
//Division 2, level 2
//2017-01-07

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ConnectedComponentConstruction {
	
	public static class ArrayIndexComparator implements Comparator<Integer>
	{
	    private final int[] array;

	    public ArrayIndexComparator(int[] x)
	    {
	        this.array = x;
	    }

	    public Integer[] createIndexArray()
	    {
	    	Integer[] indexes = new Integer[array.length];
	        for (Integer i = 0; i < array.length; i++)
	        {
	            indexes[i] = i; // Autoboxing
	        }
	        return indexes;
	    }

	    @Override
	    public int compare(Integer index1, Integer index2)
	    {
	         // Autounbox from Integer to Integer to use as array indexes
	    	if (array[index1] == array[index2])
	    		return 0;
	    	if (array[index1] > array[index2])
	    		return 1;
	    	return -1;
	    }
	}
	
	public static String []construct(int []x) {
		ArrayIndexComparator comparator = new ArrayIndexComparator(x);
		Integer[] indexes = comparator.createIndexArray();
		Arrays.sort(indexes, comparator);
		boolean [][]resultBool = new boolean[x.length][x.length];
		for (int i = 0; i < x.length; ) {
			int componentSize = x[indexes[i]];
			if (componentSize > 1) {
				for (int j = 0; j < componentSize; j++) {
					if (i+j >= x.length || x[indexes[i+j]] != componentSize) {
						return new String[]{};
					}
					if (j > 0) {
						int ind1 = indexes[i+j-1];
						int ind2 = indexes[i+j];
						resultBool[ind1][ind2] = true;
						resultBool[ind2][ind1] = true;
					}
				}
				i += componentSize;
			} else {
				i++;
			}
		}
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < x.length; i++) {
			StringBuffer tempString = new StringBuffer();
			for (int j = 0; j < x.length; j++)
				tempString.append(resultBool[i][j] ? "Y":"N");
			temp.add(tempString.toString());
		}
		return temp.toArray(new String[temp.size()]);
	}
	
	public static void main(String[] args) {
		String [] temp = construct(new int []{4,4,4,4,4});
		System.out.println(Arrays.deepToString(temp));
	}
}
