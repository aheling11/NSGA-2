package MOEA;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Myrandom {

	private Random tRandom;

	public Myrandom() {
		this.tRandom = new Random();
	}

	public int rnd(int low, int high) {
		return (int) (low + (high-low+1)*(tRandom .nextDouble()));

	}

	public boolean flip(double prob) {
		if(prob > tRandom.nextDouble()){
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
//		ArrayList<Integer> F = new ArrayList<>();
//		ArrayList<Integer> H = new ArrayList<>();
//		F.add(2);
//		F.add(22);
//		F.add(222);

//		H = F;
//		for (Iterator iterator = H.iterator(); iterator.hasNext();) {
//			Integer integer = (Integer) iterator.next();
//			System.out.println(integer);
//		}
//		System.out.println("------------------------");
//		F.set(0, 3);
//		for (Iterator iterator = H.iterator(); iterator.hasNext();) {
//			Integer integer = (Integer) iterator.next();
//			System.out.println(integer);
//		}
		ArrayList<Integer> H = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			ArrayList<Integer> F = new ArrayList<>();
			if (i == 0) {
				F.add(2);
				F.add(22);
				F.add(222);
				H = F;
			}
			if (i == 0) {
				System.out.println("i == 0");
				F.set(0, 3);
				for (Iterator iterator = H.iterator(); iterator.hasNext();) {
					Integer integer = (Integer) iterator.next();
					System.out.println(integer);
				}
			}
			if (i == 1) {
				System.out.println("i == 1");
				for (Iterator iterator = H.iterator(); iterator.hasNext();) {
					Integer integer = (Integer) iterator.next();
					System.out.println(integer);
				}
			}

		}


	}
}
