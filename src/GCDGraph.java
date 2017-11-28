/**
 * <h1>GCDGraph</h1>
 * <ul>
 * <li>No. 14461</li>
 * <li>Division 2, level 2</li>
 * </ul>
 * @author chaonan99
 * @since 2017-01-07/8
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.naming.directory.InvalidAttributeValueException;

public class GCDGraph {

	private static boolean[] mSieve;

	// Fast sieve algorithm finding all prime numbers smaller than `size`.
	public static void SieveEraFast(int size) {
		int m = (size - 1)/2;
		mSieve = new boolean[m];
		Arrays.fill(mSieve, true);
		int p = 3;
		for (int i = 0; p*p < size; i++, p += 2) {
			if (mSieve[i])
				for (int j = (p*p - 3)/2; j < m; j += p)
					mSieve[j] = false;
		}
	}

	// Just a entry function of different factor method
	public static List<Integer> Factorization(int src, char method) throws InvalidAttributeValueException {
		switch (method) {
		case 's':
			return factorSieve(src);
		case 'e':
			return factorEnum(src);
		default:
			throw new InvalidAttributeValueException("Invalid method sign: " + method);
		}
	}

	// Used as method to find all factors of a number.
	private static List<Integer> factorEnum(int src) {
		List<Integer> factors = new ArrayList<Integer>();
		factors.add(1);
		for (int i = 2; i <= Math.sqrt(src); i++)
			if (src % i == 0)
				factors.add(i);
		List<Integer> reverseFactors = new ArrayList<Integer>(factors);
		Collections.reverse(reverseFactors);
		Iterator<Integer> iterator = reverseFactors.iterator();
		while (iterator.hasNext())
			factors.add(src / iterator.next());
		return factors;
	}

	// NOT FINISHED!!! This function is not used in the solution
	private static List<Integer> factorSieve(int src) {
		int dSrc = src / 2 - 1;
		List<Integer> primeFactors = new ArrayList<Integer>();
		if (mSieve.length <= dSrc) {
			SieveEraFast(dSrc);
		}
		int currSrc = dSrc;
		for (int i = 0; i <= dSrc; i++) {
			if (mSieve[i]) {
				while (currSrc % i == 0) {
					currSrc /= i;
				}
				primeFactors.add(i);
			}
		}
		return primeFactors;
	}

	// Check if x, y can be connected directly or with a **bridge number**
	private static boolean canConnectOne(int n, int k, int x, int y) {
		int g = gcd(x, y);
		int b = (x > y) ? x / g : y / g;
		return n / b > k;
	}

	public static String possible(int n, int k, int x, int y) {
		if (x == y || k <= 0) return "Possible";
		List<Integer> factorsX = factorEnum(x);
		factorsX.removeIf(p -> !canConnectOne(n, k, x, p));
		List<Integer> factorsY = factorEnum(y);
		factorsY.removeIf(p -> !canConnectOne(n, k, y, p));
		Integer[] arrayX = factorsX.toArray(new Integer[factorsX.size()]);
		Integer[] arrayY = factorsY.toArray(new Integer[factorsY.size()]);
		for (int i = 0; i < arrayX.length; i++)
			for (int j = 0; j < arrayY.length; j++)
				if (canConnectOne(n,k,arrayX[i], arrayY[j]))
					return "Possible";
		return "Impossible";
	}

	// Compute greatest commen divider of two numbers.
	public static int gcd(int x, int y) {
		int reminder;
		do {
			reminder = x % y;
			x = y;
			y = reminder;
		} while (reminder > 0);
		return x;
	}

	// All system tests
	public static void main(String[] args) {
		System.out.println(possible(12, 2, 8, 9) == "Possible");
		System.out.println(possible(12, 2, 11, 12) == "Impossible");
		System.out.println(possible(12, 2, 11, 11) == "Possible");
		System.out.println(possible(10, 2, 8, 9) == "Impossible");
		System.out.println(possible(1000000, 1000, 12345, 54321) == "Possible");
		System.out.println(possible(1000000, 2000, 12345, 54321) == "Impossible");
		System.out.println(possible(2, 0, 1, 2) == "Possible");
		System.out.println(possible(1000000, 100, 123456, 111111) == "Possible");
		System.out.println(possible(1000000, 10000, 111111, 123456) == "Impossible");
		System.out.println(possible(523532, 333, 1213, 42823) == "Possible");
		System.out.println(possible(523532, 444, 1213, 42823) == "Impossible");
		System.out.println(possible(917463, 910, 31503, 119431) == "Impossible");
		System.out.println(possible(896657, 8, 715200, 104300) == "Possible");
		System.out.println(possible(728443, 2, 56844, 659814) == "Possible");
		System.out.println(possible(506671, 79, 255568, 258666) == "Impossible");
		System.out.println(possible(333171, 80, 311297, 226873) == "Impossible");
		System.out.println(possible(837677, 6357, 215805, 412920) == "Impossible");
		System.out.println(possible(326813, 81990, 131976, 67062) == "Impossible");
		System.out.println(possible(100033, 2583, 37951, 78039) == "Impossible");
		System.out.println(possible(758382, 931, 622425, 69311) == "Impossible");
		System.out.println(possible(741771, 586669, 294181, 45502) == "Impossible");
		System.out.println(possible(517876, 302, 508937, 244308) == "Impossible");
		System.out.println(possible(698521, 1, 35534, 96176) == "Possible");
		System.out.println(possible(595003, 91, 305339, 224349) == "Impossible");
		System.out.println(possible(696023, 42745, 519341, 107582) == "Impossible");
		System.out.println(possible(997896, 0, 776983, 581034) == "Possible");
		System.out.println(possible(99164, 42303, 63427, 828) == "Impossible");
		System.out.println(possible(914825, 8, 749795, 849827) == "Possible");
		System.out.println(possible(773946, 44, 184283, 14444) == "Possible");
		System.out.println(possible(927108, 1678, 857255, 222980) == "Impossible");
		System.out.println(possible(79041, 58, 75594, 23300) == "Possible");
		System.out.println(possible(805597, 2, 197716, 101927) == "Possible");
		System.out.println(possible(943228, 894427, 116788, 460042) == "Impossible");
		System.out.println(possible(110693, 9, 90490, 90668) == "Possible");
		System.out.println(possible(236960, 68, 226377, 224483) == "Impossible");
		System.out.println(possible(395719, 241878, 22265, 116152) == "Impossible");
		System.out.println(possible(541911, 3, 471050, 475494) == "Possible");
		System.out.println(possible(493515, 31608, 288206, 28910) == "Impossible");
		System.out.println(possible(183008, 7, 70437, 57007) == "Possible");
		System.out.println(possible(260471, 804, 174820, 228078) == "Impossible");
		System.out.println(possible(259126, 835, 134591, 148583) == "Impossible");
		System.out.println(possible(147198, 114, 44121, 127576) == "Possible");
		System.out.println(possible(824285, 3084, 516875, 308884) == "Impossible");
		System.out.println(possible(438629, 94553, 41188, 258085) == "Impossible");
		System.out.println(possible(268564, 45449, 4188, 161482) == "Impossible");
		System.out.println(possible(889639, 644228, 92884, 846436) == "Impossible");
		System.out.println(possible(25284, 9601, 22385, 14597) == "Impossible");
		System.out.println(possible(1707, 726, 738, 1231) == "Impossible");
		System.out.println(possible(144530, 74, 118538, 135947) == "Impossible");
		System.out.println(possible(352476, 85, 175272, 68008) == "Impossible");
		System.out.println(possible(647523, 8993, 553861, 112237) == "Impossible");
		System.out.println(possible(565485, 52109, 4863, 207394) == "Impossible");
		System.out.println(possible(418094, 843, 266825, 90476) == "Impossible");
		System.out.println(possible(439816, 2882, 211557, 123216) == "Impossible");
		System.out.println(possible(344019, 94970, 93886, 339051) == "Impossible");
		System.out.println(possible(92099, 4885, 28105, 60323) == "Impossible");
		System.out.println(possible(282236, 137739, 13944, 26094) == "Impossible");
		System.out.println(possible(795883, 9, 722956, 96560) == "Possible");
		System.out.println(possible(719981, 10, 309724, 40678) == "Impossible");
		System.out.println(possible(196501, 62053, 41308, 148739) == "Impossible");
		System.out.println(possible(805556, 8985, 734397, 178909) == "Impossible");
		System.out.println(possible(931536, 218, 508781, 306464) == "Impossible");
		System.out.println(possible(930383, 5, 820764, 736413) == "Impossible");
		System.out.println(possible(623964, 12905, 433894, 150563) == "Impossible");
		System.out.println(possible(178058, 127790, 148488, 67978) == "Impossible");
		System.out.println(possible(527813, 15040, 9712, 268075) == "Impossible");
		System.out.println(possible(732476, 248738, 202543, 652393) == "Impossible");
		System.out.println(possible(337393, 211285, 128440, 55733) == "Impossible");
		System.out.println(possible(612992, 92, 579197, 522840) == "Impossible");
		System.out.println(possible(608227, 1, 528470, 536506) == "Possible");
		System.out.println(possible(848338, 125, 795480, 631091) == "Impossible");
		System.out.println(possible(320215, 8461, 183795, 151464) == "Impossible");
		System.out.println(possible(303944, 914, 169675, 79638) == "Impossible");
		System.out.println(possible(875821, 10, 110200, 415206) == "Possible");
		System.out.println(possible(478068, 1910, 302310, 390315) == "Impossible");
		System.out.println(possible(338575, 842, 299442, 266993) == "Impossible");
		System.out.println(possible(665858, 270, 345505, 424137) == "Possible");
		System.out.println(possible(279194, 0, 122647, 194890) == "Possible");
		System.out.println(possible(515336, 423, 432623, 274521) == "Impossible");
		System.out.println(possible(961258, 1, 341913, 510452) == "Possible");
		System.out.println(possible(620575, 68275, 581091, 264897) == "Impossible");
		System.out.println(possible(973688, 356, 141920, 118088) == "Possible");
		System.out.println(possible(439005, 98097, 380545, 9935) == "Impossible");
		System.out.println(possible(687457, 953, 124219, 606007) == "Impossible");
		System.out.println(possible(137451, 26581, 129040, 11834) == "Impossible");
		System.out.println(possible(541430, 87628, 312224, 171079) == "Impossible");
		System.out.println(possible(99299, 37045, 46065, 81262) == "Impossible");
		System.out.println(possible(478645, 2497, 316463, 278260) == "Impossible");
		System.out.println(possible(956904, 418, 503010, 180945) == "Impossible");
		System.out.println(possible(818079, 137613, 244388, 106677) == "Impossible");
		System.out.println(possible(436802, 510, 384322, 54866) == "Impossible");
		System.out.println(possible(48312, 26388, 11950, 45848) == "Impossible");
		System.out.println(possible(94202, 69, 9414, 44243) == "Possible");
		System.out.println(possible(89502, 77571, 53750, 37208) == "Impossible");
		System.out.println(possible(519715, 6955, 85580, 211153) == "Impossible");
		System.out.println(possible(795316, 4, 52075, 97762) == "Possible");
		System.out.println(possible(879952, 162, 807238, 393385) == "Impossible");
		System.out.println(possible(411144, 369, 266955, 22326) == "Possible");
		System.out.println(possible(116724, 8, 68260, 10263) == "Possible");
		System.out.println(possible(497822, 6, 482888, 143696) == "Possible");
		System.out.println(possible(760104, 9, 41890, 121866) == "Possible");
		System.out.println(possible(588812, 33019, 123386, 96379) == "Impossible");
		System.out.println(possible(910316, 68782, 621773, 418642) == "Impossible");
		System.out.println(possible(105270, 98411, 99497, 15074) == "Impossible");
		System.out.println(possible(788634, 42012, 736334, 726682) == "Impossible");
		System.out.println(possible(86589, 9, 56012, 7457) == "Possible");
		System.out.println(possible(874876, 908, 333947, 15632) == "Impossible");
		System.out.println(possible(960476, 2978, 560598, 316832) == "Impossible");
		System.out.println(possible(284772, 293, 17688, 162825) == "Possible");
		System.out.println(possible(393023, 272067, 346007, 392973) == "Impossible");
		System.out.println(possible(447100, 915, 43471, 220685) == "Impossible");
		System.out.println(possible(10, 5, 2, 2) == "Possible");
		System.out.println(possible(3, 1, 1, 3) == "Impossible");
		System.out.println(possible(10, 10, 9, 9) == "Possible");
		System.out.println(possible(1000000, 100, 500000, 500001) == "Impossible");
		System.out.println(possible(10, 10, 7, 7) == "Possible");
		System.out.println(possible(891264, 205744, 187866, 857333) == "Impossible");
	}
}
