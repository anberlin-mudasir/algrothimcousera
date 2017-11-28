//No. 14465
//Division 2, level 1
//2017-01-07

import java.util.Arrays;

public class SwapAndArithmetic {
	public static String able(int []x) {
		Arrays.sort(x);
		int diff = x[1] - x[0];
		for (int i = 1; i < x.length; i++) {
			if (x[i] - x[i-1] != diff) {
				return "Impossible";
			}
		}
		return "Possible";
	}
}
