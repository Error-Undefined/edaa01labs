package test;

import map.SimpleHashMap;

public class TestShowList {
	public static void main(String[] args) {
		SimpleHashMap<Integer, Integer> m = new SimpleHashMap<Integer, Integer>();
//		java.util.Random random = new java.util.Random(123456);
		
		for (int i = -1000; i <= 1000; i++) {
//			int r = random.nextInt(10000);	
			m.put(100*i, 100*i);
		}
		System.out.println(m.show());
	}
}
