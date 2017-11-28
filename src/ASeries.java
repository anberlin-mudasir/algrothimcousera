import java.util.Arrays;

public class ASeries {
	public static int longest(int []inputs) {
		// if (inputs == null || inputs.length == 0) return 0;
		// if (inputs.length == 1) return 1;
		if (inputs.length == 2) return 2;
		Arrays.sort(inputs);
		int maxLen = findLongestSame(inputs);
		int []uniInputs = justUniques(inputs);
		boolean []arrayHash = hashIntArray(uniInputs);
		int total = uniInputs.length;
		int offset = uniInputs[0];
		int currLen = 2;
		boolean []pairUsedHash = new boolean[total*total];
		for (int i = 0; i < total - maxLen; i ++) {
			for (int j = i+1; j < total - maxLen + 1; j++) {
				int span = uniInputs[j] - uniInputs[i];
				int front = uniInputs[j] - offset;
				if (!pairUsedHash[i*total+j-1]) {
					for (front += span; front < arrayHash.length && arrayHash[front]; front += span) {
						currLen += 1;
						pairUsedHash[i*total+j-1] = true;
					}
					if (currLen > maxLen) {
						maxLen = currLen;
					}
				}
			}
		}
		return maxLen;
	}
	
	private static int[] justUniques(int[] arr) { 
	    if (arr == null || arr.length == 0) return arr;
	    Arrays.sort(arr);
	    int n = 1;
	    for (int i = 1; i < arr.length; i++) {
	        if (arr[i] != arr[i-1]) n++;
	    }
	    int[] res = new int[n];
	    res[0] = arr[0];
	    n = 1;
	    for (int i = 1; i < arr.length; i++) {
	        if (arr[i] != arr[i-1]) res[n++] = arr[i];
	    }
	    return res;
	}
	
	private static int findLongestSame(int []inputs) {
		if (inputs.length == 0) {
			return 0;
		}
		int currLen = 1;
		int maxLen = 1;
		for (int i = 1; i < inputs.length; i ++) {
			if (inputs[i] != inputs[i-1]) {
				if (currLen > maxLen) {
					maxLen = currLen;
				}
				currLen = 1;
			} else {
				currLen += 1;
			}
		}
		if (currLen > maxLen) {
			maxLen = currLen;
		}
		return maxLen;
	}
	
	private static boolean []hashIntArray(int []inputs) {
		int maxNum = inputs[inputs.length - 1];
		int minNum = inputs[0];
		int span = maxNum - minNum + 1;
		boolean []hashResult = new boolean[span];
		for (int i = 0; i < inputs.length; i ++) {
			hashResult[inputs[i] - minNum] = true;
		}
		return hashResult;
	}
	
	public static void testHashIntArray() {
		System.out.println(Arrays.toString(hashIntArray(new int[]{1,2,4})));  // [true, true, false, true]
		System.out.println(Arrays.toString(hashIntArray(new int[]{-1,2,4}))); // [true, false, false, true, false, true]
		System.out.println(Arrays.toString(hashIntArray(new int[]{1,1,3})));  // [true, false, true]
	}
	
	public static boolean testFindLongestSame() {
		assert findLongestSame(new int[]{}) == 0 : "Empty array should return 0";
		assert findLongestSame(new int[]{2}) == 1;
		assert findLongestSame(new int[]{2, 2, 2}) == 3;
		assert findLongestSame(new int[]{0, 2, 5, 5, 7}) == 2;
		System.out.println("Test findLongestSame successful!");
		return true;
	}
	
	public static void testLongest() {
		System.out.println(longest(new int[]{1,2,3,4,1}));  // 4
		System.out.println(longest(new int[]{1,1,2,-1,1})); // 3
		System.out.println(longest(new int[]{-1,-5,1,3}));  // 3
		System.out.println(longest(new int[]{3,8,4,5,6,2,2}));  // 5
	}
	
	public static void main(String []args) {
		// testHashIntArray();
		// testFindLongestSame();
		testLongest();
    }
}
