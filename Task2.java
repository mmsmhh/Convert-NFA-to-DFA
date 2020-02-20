import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Task2 {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner();

		String test = "0,0;0,1;1,3#1,2;2,4;4,4#0,1;3,4#3,4";
		DFA dfa = new DFA(test);
//		System.out.println(dfa.run("0010"));
//		System.out.println(dfa.run("0111"));
//		System.out.println(dfa.run("1010"));
//		System.out.println(dfa.run("1111"));
//		System.out.println(dfa.run("0110"));
		// 0010 F
		// 0111 T
		// 1010 F
		// 1111 T
		// 0110 F

//		String test = "0,1;1,3;4,5;5,5#1,2;2,4;4,4#0,1;3,4#5";
//		DFA dfa = new DFA(test);
//		System.out.println(dfa.run("1011"));
//		System.out.println(dfa.run("01100"));
//		System.out.println(dfa.run("000111"));
//		System.out.println(dfa.run("010"));
//		System.out.println(dfa.run("1111"));
		// 1011 F
		// 01100 T
		// 000111 F
		// 010 T
		// 1111 F

//		String test = "0,2;1,0;2,1#2,1;2,2#0,1#1";
		// 10110 F
		// 01110 T
		// 100100 F
		// 0001 T
		// 010 T

//		String test = "0,0;0,1;0,4;4,4#0,0;1,2;2,3;4,5#3,4;3,1#3,5";
		// 001011
		// 011000
		// 1101001
		// 011011010
		// 110010

//		String test = "0,0;0,1#0,0;1,2#1,2#2";
		// 0000 T
		// 0101 T
		// 1111 F
		// 1000 T
		// 111111 F

//		String test = "0,0;1,2;3,3#0,0;0,1;2,3;3,3#1,2#3";
		// 1011 T
		// 1111 T
		// 1000 F
		// 001 F
		// 0110 T

//		String test = "0,1;1,2;2,3#1,3;3,4;4,2#0,2;3,1;2,4#2";
		// 0000 T
		// 011011 T
		// 01010 F
		// 101010 F
		// 11100 T

//		String test = "0,0;0,1;1,3#0,1;1,2;2,3#1,2;3,2#3";
		// 000111 T
		// 10110 F
		// 00110 F
		// 101010 F
		// 1111 T

//		String test = "0,1;1,3;3,3#0,2;2,3;3,3#1,2;3,2#3";
		// 0101100 T
		// 010101 T
		// 111010 T
		// 10100 F
		// 10101 F

//		String test = "0,1;1,2;2,3#0,0;1,1;2,3;3,3#1,0;2,1;3,2#1,2,3";
		// 0100 T
		// 1111 F
		// 01000 T
		// 00 T
		// 1101100 T

//		String test = "0,2;1,0;2,1#2,1;2,2#0,1#1";
		// 10110 F
		// 01110 T
		// 100100 F
		// 0001 T
		// 010 T

//		String test = "0,0;0,1;0,4;4,4#0,0;1,2;2,3;4,5#3,4;3,1#3,5";
		// 001011 T
		// 011000 F
		// 1101001 T
		// 011011010 F
		// 110010 F


	}

	static class Transition {

		String zero, one;

		public Transition(String zero, String one) {
			this.zero = zero;
			this.one = one;
		}

		public String getZero() {
			return zero;
		}

		public void setZero(String zero) {
			this.zero = zero;
		}

		public String getOne() {
			return one;
		}

		public void setOne(String one) {
			this.one = one;
		}

	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreElements())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws NumberFormatException, IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}
	}

	static class DFA {

		Hashtable<String, Transition> dfa;
		String[] acceptState;
		String first;

		public DFA(String NFA) {

			String numberString = NFA.replaceAll("[^\\d]", " ");

			numberString = numberString.trim();

			numberString = numberString.replaceAll(" ", "");

			int max = -1 * Integer.MAX_VALUE;

			for (int i = 0; i < numberString.length(); i++) {
				max = Math.max(max, Integer.parseInt(String.valueOf(numberString.charAt(i))));
			}

			String[] line = NFA.split("#");

			String[] zeroTransitions = line[0].split(";");

			String[] oneTransitions = line[1].split(";");

			String[] epsilonTransitions = line[2].split(";");

			acceptState = line[3].split(",");

			String[][] nfa = new String[max + 1][3];

			for (int i = 0; i < zeroTransitions.length; i++) {
				String[] transitions = zeroTransitions[i].split(",");

				if (nfa[Integer.parseInt(transitions[0])][0] != null)
					nfa[Integer.parseInt(transitions[0])][0] += "," + transitions[1];
				else
					nfa[Integer.parseInt(transitions[0])][0] = transitions[1];
			}

			for (int i = 0; i < oneTransitions.length; i++) {
				String[] transitions = oneTransitions[i].split(",");

				if (nfa[Integer.parseInt(transitions[0])][1] != null)
					nfa[Integer.parseInt(transitions[0])][1] += "," + transitions[1];
				else
					nfa[Integer.parseInt(transitions[0])][1] = transitions[1];
			}

			for (int i = 0; i < epsilonTransitions.length; i++) {
				String[] transitions = epsilonTransitions[i].split(",");

				if (nfa[Integer.parseInt(transitions[0])][2] != null)
					nfa[Integer.parseInt(transitions[0])][2] += "," + transitions[1];
				else
					nfa[Integer.parseInt(transitions[0])][2] = transitions[1];
			}

			for (int i = 0; i < nfa.length; i++) {
				for (int j = 0; j < nfa[i].length; j++) {
					System.out.print(nfa[i][j] + " ");
				}
				System.out.println();
			}

			dfa = new Hashtable<String, Task2.Transition>();

			Queue<String> queue = new LinkedList<String>();

			first = "0";

			String[] allStart = first.split(",");

			for (int i = 0; i < allStart.length; i++) {

				String epsilon = nfa[Integer.parseInt(String.valueOf(allStart[i]))][2];

				if (epsilon == null)
					continue;

				String[] allEpsilon = epsilon.split(",");

				for (int j = 0; j < allEpsilon.length; j++) {
					if (!first.contains(allEpsilon[j])) {
						first += "," + allEpsilon[j];
						allStart = first.split(",");
					}
				}

			}

			queue.add(first);

			while (!queue.isEmpty()) {

				String current = queue.poll();

				String[] allStates = current.split(",");

				String zero = "";
				String one = "";

				for (int i = 0; i < allStates.length; i++) {

					String state = allStates[i];

					String myZero = nfa[Integer.parseInt(state)][0];

					String myOne = nfa[Integer.parseInt(state)][1];

					if (myZero != null) {

						myZero = myZero.replaceAll(",", "");

						for (int j = 0; j < myZero.length(); j++) {

							if (!zero.contains(String.valueOf(myZero.charAt(j)))) {
								if (zero.length() > 0)
									zero += "," + myZero.charAt(j);
								else
									zero += myZero.charAt(j);
							}
						}
					}

					if (myOne != null) {

						myOne = myOne.replaceAll(",", "");

						for (int j = 0; j < myOne.length(); j++) {
							if (!one.contains(String.valueOf(myOne.charAt(j)))) {
								if (one.length() > 0)
									one += "," + myOne.charAt(j);
								else
									one += myOne.charAt(j);
							}
						}
					}

				}

				if (zero.length() > 0) {
					String[] allZeros = zero.split(",");

					for (int i = 0; i < allZeros.length; i++) {

						String epsilon = nfa[Integer.parseInt(String.valueOf(allZeros[i]))][2];

						if (epsilon == null)
							continue;

						String[] allEpsilon = epsilon.split(",");

						for (int j = 0; j < allEpsilon.length; j++) {
							if (!zero.contains(allEpsilon[j])) {
								zero += "," + allEpsilon[j];
								allZeros = zero.split(",");
							}
						}

					}
				}

				if (one.length() > 0) {
					String[] allOnes = one.split(",");

					for (int i = 0; i < allOnes.length; i++) {

						String epsilon = nfa[Integer.parseInt(String.valueOf(allOnes[i]))][2];

						if (epsilon == null)
							continue;

						String[] allEpsilon = epsilon.split(",");

						for (int j = 0; j < allEpsilon.length; j++) {
							if (!one.contains(allEpsilon[j])) {
								one += "," + allEpsilon[j];
								allOnes = one.split(",");
							}
						}

					}
				}

				zero = sortString(zero);

				one = sortString(one);

				System.out.println("____________");
				System.out.println(current);
				System.out.println(zero);
				System.out.println(one);
				System.out.println("____________");

				dfa.put(current, new Transition(zero, one));

				if (!dfa.containsKey(zero) && !queue.contains(zero) && zero != "")
					queue.add(zero);

				if (!dfa.containsKey(one) && !queue.contains(one) && one != "")
					queue.add(one);

			}

//			Set<String> keys = dfa.keySet();
//
//			for (String key : keys) {
//				System.out.println("Value of " + key + " is: " + dfa.get(key).zero + " and " + dfa.get(key).one);
//			}

		}

		public boolean run(String input) {

			String currentState = first;

			for (int i = 0; i < input.length(); i++) {

//				System.out.println(currentState);

				if (currentState.length() < 1)
					return false;

				currentState = getNewState(currentState, input.charAt(i));

			}

			for (int i = 0; i < acceptState.length; i++) {
				for (int k = 0; k < currentState.length(); k++) {
					if (acceptState[i].equals(String.valueOf(currentState.charAt(k)))) {
						return true;
					}
				}
			}

			return false;
		}

		private String getNewState(String currentState, char input) {
			if (input == '0') {
				return dfa.get(currentState).zero;
			} else {
				return dfa.get(currentState).one;
			}
		}

		private String sortString(String string) {

			String numberString = string.replaceAll("[^\\d]", " ");

			numberString = numberString.trim();

			numberString = numberString.replaceAll(" ", "");

			char[] chars = numberString.toCharArray();

			Arrays.sort(chars);

			String sorted = new String(chars);

			String last = "";

			for (int i = 0; i < sorted.length(); i++) {
				if (i != sorted.length() - 1)
					last += sorted.charAt(i) + ",";
				else
					last += sorted.charAt(i);

			}

			return last;

		}
	}

}
