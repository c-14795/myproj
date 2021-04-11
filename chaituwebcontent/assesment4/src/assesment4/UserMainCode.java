package assesment4;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class UserMainCode {

	public static int output1;
	

	public static void findStringCode(String input1) {
		// Write code here
		int sum=0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		input1 = input1.toUpperCase();
		String output="";
		System.out.println(input1);
		String s[] = input1.split(" ");
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
			char[] c = s[i].toCharArray();
			if (c.length>2&c.length!=4) {
				
			for (int j = 0, k = c.length - 1; j <= c.length / 2
					&& k >= c.length / 2; j++, k--) {
		

				 {
					
					System.out.println("value 0f j" + j);
					System.out.println("value 0f k" + k);
					int a = Math.abs(65 - (int) c[j]);
					int b = Math.abs(65 - (int) c[k]);
					System.out.println(c[j]+"-"+c[k]);
					System.out.println(a + "-" + b);
					sum = Math.abs(a - b) + sum;
				}

			}
			if(c.length%2!=0)
			{
			System.out.println(c[c.length / 2]);
			int a = Math.abs(65 - (int) c[c.length / 2]);
			sum = sum + a + 1;
			}

			}	
			if(c.length==2)
			{
				int a = Math.abs(65 - (int) c[0]);
				int b = Math.abs(65 - (int) c[1]);
				sum=Math.abs(a-b);
				
			}
			if(c.length==4)
			{
				int a = Math.abs(65 - (int) c[0]);
				int b = Math.abs(65 - (int) c[1]);
				int b1 = Math.abs(65 - (int) c[2]);
				int b2 = Math.abs(65 - (int) c[3]);
				sum=Math.abs(a-b2)+Math.abs(b-b1);
				
			}
			
			
			
			int sum1 = sum;
			sum = 0;
			map.put(i, sum1);
			
		}
		System.out.println(s.length);
		@SuppressWarnings("rawtypes")
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<Integer, Integer> en = (Entry<Integer, Integer>) it.next();
			if(en.getValue()!=0)
			{
			output=output+String.valueOf(en.getValue());
			}
			System.out.println(output);
			
		}

	}
	public static void main(String[] args) {
		findStringCode("world wide web");
	}

	
}
