package MOEA;


import java.util.ArrayList;
import java.util.List;

public class Population {
	public ArrayList<Individual> pop = new ArrayList<Individual>();



	public void inipop(int popsize) {
		for (int i = 0; i < popsize ; i++) {
			Individual tempIndividual = new Individual();
			pop.add(tempIndividual);

		}
	}

	public Population(int popsize) {

	}

	public Population() {
		pop.clear();
	}

	public void printPopVarible() {
		System.out.println("population.varible:");
		for (int i = 0; i < pop.size(); i++) {
			System.out.println(pop.get(i).variable);
		}
	}

	public void printPopChromx() {
		System.out.println("population.Chromx:");
		for (int i = 0; i < pop.size(); i++) {
			System.out.println(pop.get(i).chromarr[0]);
		}
	}

	public void printCrowDis() {
		System.out.println("population.Crowdis:");
		for (int i = 0; i < pop.size(); i++) {
			System.out.println(pop.get(i).crowDis);
		}
	}

	public void printFx(int k) {
		System.out.println("population:fx["+k+"]");
		for (int i = 0; i < pop.size(); i++) {
			System.out.println(pop.get(i).fx[k]);
		}
	}

	public void printGtemp() {
		System.out.println("gtemp:");
		for (int i = 0; i < pop.size(); i++) {
			System.out.println(pop.get(i).gtemp);
		}
	}
	public void iniPopSiandNi() {
		for (int i = 0; i < pop.size(); i++) {
			pop.get(i).ni = 0;
			pop.get(i).si.clear();
		}
	}

	public void iniEmptyPop() {
		for (int i = 0; i < pop.size(); i++) {
			pop.set(i, null);
		}
	}
	public static void main(String[] args) {
		Population population = new Population(10);
		int cnt = 1;
		for (int i = 0; i < population.pop.size(); i++) {
			System.out.println(cnt++);
			for (int j = 0; j < 2; j++) {
				System.out.println(population.pop.get(i).chromarr[j]);

			}
			System.out.println();
		}
	}
}