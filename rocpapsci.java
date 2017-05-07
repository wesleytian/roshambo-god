import java.lang.Math;
import java.util.Scanner;
import java.lang.*;
import java.util.*;

public class rocpapsci {

	public static void main(String args[]) {

		int k = Integer.parseInt(args[0]);
		int tableSize = (int)Math.pow(3, k + 1);

		Scanner scan = new Scanner(System.in);
		String str = "";

		// initialize probability table
		double[] table = new double[tableSize];

		double[] totalCountTable = new double[3];
		totalCountTable[0] = 0.1;
		totalCountTable[1] = 0.1;
		totalCountTable[2] = 0.1;
		double totalCount = 0;

		// return output as user enters input
		while(scan.hasNextLine()) {

			String newNum = scan.nextLine();
			str += newNum;

			int index = Integer.parseInt(newNum);
			totalCountTable[index]++;

			double a = totalCountTable[0] + totalCountTable[1] + totalCountTable[2];
			double rocProb = totalCountTable[0] / a;
			double papProb = totalCountTable[1] / a;
			double sciProb = totalCountTable[2] / a;

			// System.out.println("r: " + rocProb);
			// System.out.println("p: " + papProb);
			// System.out.println("s: " + sciProb);

			//calculate findStr.
			//findStr is the short-term history, lookback of k-values and given (current)
			// System.out.println("findstr(" + str + ", " + k);
			int findStrLen = k + 1;
			int strLen = str.length();

			if(strLen >= findStrLen) {
				String findStrRes = findStr(str, findStrLen);

				int key = calculateKey(findStrRes);
				double val = probabilityValue(str, findStrRes);

				table[key] = val;


				int rocKey = calculateKey(findStrRes.substring(0, findStrRes.length() - 1) + "0");
				int papKey = calculateKey(findStrRes.substring(0, findStrRes.length() - 1) + "1");
				int sciKey = calculateKey(findStrRes.substring(0, findStrRes.length() - 1) + "2");

				// System.out.println("rocKey: " + rocKey + " papKey: " + papKey + " sciKey: " + sciKey);

				for(int i = 0; i < (int)Math.pow(3, k + 1); i++) {
					if(table[i] == 0.0)
						table[i] += 0.1;
				}

				double rocVal = table[rocKey];
				double papVal = table[papKey];
				double sciVal = table[sciKey];
				// System.out.println("rocVal: " + rocVal + " papVal: " + papVal + " sciVal: " + sciVal);


				double denom = (rocVal * rocProb) + (papVal * papProb) + (sciVal * sciProb);
				// System.out.println("denom: " + denom);

				// System.out.println("numerators: " + (rocVal * rocProb) + " " + (papVal * papProb) + " " + (sciVal * sciProb));

				double rocFinal = (rocVal * rocProb) / denom;
				double papFinal = (papVal * papProb) / denom;
				double sciFinal = (sciVal * sciProb) / denom;
				// System.out.println("rocFinal: " + rocFinal + " papFinal: " + papFinal + " sciFinal: " + sciFinal);

				double[] finalRes = {rocFinal, papFinal, sciFinal};

				System.out.println(utilityFunction(index, finalRes));


			}

			// for(double j : table)
			// 	System.out.println(j);
			// TODO: calculate key for hash table 

			// TODO: determine number of occurences of sequence in total history, get value
			
			// TODO: insert value for respective key

			// TODO: calculate probability

			// TODO: calculate utility

			// TODO: return highest utility


			// int input = Integer.parseInt(scan.nextLine());
			// code here
		}
	}

	// static void updateTotalProbs(int num, double[] totalCountTable) {
	// 	totalCountTable[num]++;
	// 	for(int j : totalCountTable)
	// 		j += 0.1;

	// }
	// static int currentProb(int num, int totalCount) {
	// 	return totalCountTable[num] / totalCount;
	// }


	static double calcProb() {

		return 0.0;
	}

	// creates findStr from last two values of str
	// iff str is larger than k + 1 values
	//ex: "0201" gives "01"
	static String findStr(String str, int findStrLen) {
		String findStr = "";
		int strLen = str.length();

		findStr = str.substring(strLen - findStrLen, strLen);
		return findStr;
	}

	// seqCount / count 
	// seqCount = numerator (count includes all instances of the findStr sequence)
	// total count = denominator (count includes all instances of given (current))

	static double probabilityValue(String str, String findStr) {
		double val = count(str, findStr) / count(str, str.substring(str.length() - 1));
		// System.out.println("val: " + val);
		return val;
	}

	static double count(String str, String findStr) {
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1) {
		    lastIndex = str.indexOf(findStr,lastIndex);
		    if (lastIndex != -1) {
		        count++;
		        lastIndex += findStr.length();
		    }
		}
		return count;
	}

	// function to calculate the index or hash key for the table
	static int calculateKey(String findStr) {
		int key = 0;
		int n = findStr.length();
		int j = 0;

		for (int i = n; i > 0; i--) {
			key += ((int)Math.pow(3, i - 1) * Integer.parseInt("" + findStr.charAt(j)));
			j++;
		}
		return key;
	}

	static int utilityFunction(int choice, double[] finalRes) {
		// prob of loses to choice - prob of wins to choice
		// u(0) = p(2) - p(1)
		// u(1) = p(0) - p(2)
		// u(2) = p(1) - p (0)
		double u0, u1, u2;

		u0 = finalRes[2] - finalRes[1];
		u1 = finalRes[0] - finalRes[2];
		u2 = finalRes[1] - finalRes[0];
		double max = Math.max(u0, Math.max(u1, u2));

		Random random = new Random();

		if(u0 == u1 && u1 == u2) {
			return random.nextInt() % 3;
		}

		else if((u1 > u0 && u1 == u2) || (u2 > u0 && u2 == u1)) 
			return random.nextBoolean() ? 2 : 1;

		else if((u1 > u2 && u1 == u0)|| (u0 > u2 && u0 == u1)) 
			return random.nextBoolean() ? 0 : 1;

		else if((u2 > u1 && u2 == u0) || (u0 > u1 && u0 == u2)) 
			return random.nextBoolean() ? 2 : 0;

		else if(u0 > u1 && u0 > u2) return 0;
		else if(u1 > u2 && u1 > u0) return 1;
		//(u2 > u0 && u2 > u1) 
		else return 2;
		
	}

}
	