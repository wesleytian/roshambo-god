import java.lang.*;
import java.util.*;

public class rocpapsci {

	public static void main(String args[]) {

		int k = Integer.parseInt(args[0]);
		int tableSize = (int)Math.pow(3, k + 1);

		Scanner scan = new Scanner(System.in);

		// Keep track of history as an ongoing string
		String str = "";

		// Initialze table values to have pseudocounts of 0.1
		double[] table = new double[tableSize];

		double[] totalCountTable = new double[3];
		totalCountTable[0] = 0.1;
		totalCountTable[1] = 0.1;
		totalCountTable[2] = 0.1;

		// While user inputs
		while(scan.hasNextLine()) {

			// Save next input as temp newNum var
			String newNum = scan.nextLine();
			// Add newNum to history
			str += newNum;
			// Convert newNum into int for convenience later on
			int index = Integer.parseInt(newNum);
			// Increase respective count by 1
			totalCountTable[index]++;

			// Total count
			double totalCount = totalCountTable[0] + totalCountTable[1] + totalCountTable[2];
			// P(In)
			double rocProb = totalCountTable[0] / totalCount;
			double papProb = totalCountTable[1] / totalCount;
			double sciProb = totalCountTable[2] / totalCount;

			// Calculates findStr
			// findStr is the short-term history, lookback of k-values and given (current)
			int findStrLen = k + 1;
			int strLen = str.length();

			if(strLen >= findStrLen) {
				String findStrRes = findStr(str, findStrLen);

				int key = calculateKey(findStrRes);
				double val = probabilityValue(str, findStrRes);

				// Insert value for respective key
				table[key] = val;

				// i∈{paper, rock, scissors}
				int rocKey = calculateKey(findStrRes.substring(0, findStrRes.length() - 1) + "0");
				int papKey = calculateKey(findStrRes.substring(0, findStrRes.length() - 1) + "1");
				int sciKey = calculateKey(findStrRes.substring(0, findStrRes.length() - 1) + "2");

				// Add pseudocount of 0.1 to all counts to avoid issues with 0
				for(int i = 0; i < (int)Math.pow(3, k + 1); i++) {
					if(table[i] == 0.0)
						table[i] += 0.1;
				}

				// P(In-1, In-2,..., In-k | In) * P(In)
				double rocVal = table[rocKey];
				double papVal = table[papKey];
				double sciVal = table[sciKey];

				// Σi∈{paper, rock, scissors}(P(In-1, In-2,..., In-k | In = i) * P(In = i))
				// Denominator of result of Bayes's Rule
				double denom = (rocVal * rocProb) + (papVal * papProb) + (sciVal * sciProb);

				// Calculate P(In | In-1, In-2,..., In-k) using Bayes's Rule
				double rocFinal = (rocVal * rocProb) / denom;
				double papFinal = (papVal * papProb) / denom;
				double sciFinal = (sciVal * sciProb) / denom;

				// Store results in array for function passing convenience
				double[] finalRes = {rocFinal, papFinal, sciFinal};
				// Print result
				System.out.println(utilityFunction(index, finalRes));
			}
			else {
				Random random = new Random();
				System.out.println(random.nextInt() % 3);
			}
		}
	}

	// Creates findStr from the last two values of str
	// iff str is larger than k + 1 values
	// Case 1: "0" returns ""
	// Case 2 "0201" returns "01"
	static String findStr(String str, int findStrLen) {
		String findStr = "";
		int strLen = str.length();
		findStr = str.substring(strLen - findStrLen, strLen);
		return findStr;
	}

	// P(In-1, In-2,..., In-k | In) * P(In)
	// Ex: str = "02021"
	// = count("020212", "02") / count("02021", "2")
	// = 2 / 3
	static double probabilityValue(String str, String findStr) {
		double val = count(str, findStr) / count(str, str.substring(str.length() - 1));
		return val;
	}

	// Returns number of times findStr occurs in str
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

	// Calculates the index or hash key for the table
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

	// Calculates and returns the choice with highest utility
	static int utilityFunction(int choice, double[] finalRes) {
		// U(choice) = P(In loses to choice | history) - P(In wins to choice | history)
		// U(0) = P(2 | history) - P(1 | history)
		// U(1) = P(0 | history) - P(2 | history)
		// U(2) = P(1 | history) - P(0 | history)
		double u0, u1, u2;
		u0 = finalRes[2] - finalRes[1];
		u1 = finalRes[0] - finalRes[2];
		u2 = finalRes[1] - finalRes[0];
		Random random = new Random();
		// If all three utilities are the same, randomize between the three
		if(u0 == u1 && u1 == u2)
			return random.nextInt() % 3;
		// If two choices' utilities are the same, randomize between the two
		else if((u1 > u0 && u1 == u2) || (u2 > u0 && u2 == u1)) 
			return random.nextBoolean() ? 2 : 1;
		else if((u1 > u2 && u1 == u0)|| (u0 > u2 && u0 == u1)) 
			return random.nextBoolean() ? 0 : 1;
		else if((u2 > u1 && u2 == u0) || (u0 > u1 && u0 == u2)) 
			return random.nextBoolean() ? 2 : 0;
		// If one choice's utility is greater than the rest
		else if(u0 > u1 && u0 > u2) 
			return 0;
		else if(u1 > u2 && u1 > u0) 
			return 1;
		//(u2 > u0 && u2 > u1) 
		else return 2;
	}

}
	