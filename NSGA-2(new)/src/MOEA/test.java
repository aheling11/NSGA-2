package MOEA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

public class test {
	Population ppopulation = new Population();
	Population qpopulation = new Population();
	Population rpopulation = new Population();

	int popsize =100;
	public test() {
		ppopulation.inipop(5);
//		ppopulation.printCrowDis();
//		System.out.println("----------------");
//		crowding_distance_assignment(ppopulation);
		ppopulation.pop.get(0).gtemp = 3;
		ppopulation.pop.get(1).gtemp = 4;
		ppopulation.pop.get(2).gtemp = 2;
		ppopulation.pop.get(3).gtemp = 6;
		ppopulation.pop.get(4).gtemp = 1;
		
		ppopulation.printGtemp();
		Collections.sort(ppopulation.pop, new GtempComparator());
		ppopulation.printGtemp();


	}
	@SuppressWarnings("unchecked")
	private void Sort_F_(Population p) {
		Collections.sort(p.pop, new CrowdisComparator());

	}

	private Population makenewpop(Population qpopulation2) {
		// TODO Auto-generated method stub
		Population tPopulation = new Population();
		tPopulation.pop.add(new Individual());
		return tPopulation;
	}
	private void crowding_distance_assignment(Population p) {
		int N = p.pop.size();
		for (int i = 0; i < N; i++) {
			p.pop.get(i).crowDis = 0;
		}
		for (int i = 0; i < p.pop.get(0).fx.length; i++) {
			for (int j = 0; j < p.pop.size(); j++) {
				p.pop.get(j).gtemp = p.pop.get(j).fx[i];
			}
//			p.printGtemp();
			sortOfsubm(p);
//			p.printGtemp();
			for (int j = 1; j < N-1; j++) {
				p.pop.get(j).crowDis = p.pop.get(j).crowDis +(p.pop.get(j+1).fx[i] - p.pop.get(j-1).fx[i]);
			}
			p.pop.get(0).crowDis =  Double.MAX_VALUE;
			p.pop.get(N-1).crowDis =  Double.MAX_VALUE;
		}
	}
	private void sortOfsubm(Population p) {
		Collections.sort(p.pop, new GtempComparator());

	}


	public static void main(String[] args) {
		test test = new test();
	}
}
