package assesment4;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class UserMainCode1 {

	public static String output1;

	public static void JumbleWords(String input1, int input2) {
		// Write code here
		Map<Integer, String> map = new HashMap<Integer, String>();
		String p = "";
		String q = "";
		String pq = "";
		String output = "";
		String s[] = input1.split(" ");
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
			char[] c = s[i].toCharArray();

			for (int j = 0; j < c.length; j++) {

				if (j % 2 != 0) {
					p += c[j];
				} else {
					q += c[j];

				}

			}
			if (input2 == 1) {
				p = new StringBuffer(p).reverse().toString();
				pq = q + p;
			}
			if (input2 == 2) {
				pq = q + p;
			}
			p = "";
			q = "";
			System.out.println(pq);
			map.put(i, pq);

		}
		Iterator it = map.entrySet().iterator();

		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<Integer, String> en = (Entry<Integer, String>) it.next();

			output = output + en.getValue();
			if (it.hasNext())
				output += " ";

		}

		System.out.println(output);

	}

	public static void main(String[] args) {

		JumbleWords("project  Based learning", 1);
	}
}