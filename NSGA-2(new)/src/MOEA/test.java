package MOEA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class test {
	Population ppopulation = new Population();
	Population qpopulation = new Population();
	Population rpopulation = new Population();
	Population F_ = new Population();
	ArrayList<Population>  PF = new ArrayList<Population>();
	int popsize =100;
	public test() {

		ppopulation.inipop(5);
		ppopulation.printFx();
		PF = fast_nondominated_sort(ppopulation);


	}
	@SuppressWarnings("unchecked")
	private void Sort_F_(Population p) {
		Collections.sort(p.pop, new CrowdisComparator());

	}

	private Population merge(Population p, Population q) {
		Population tPopulation = new Population();
		for (int i = 0; i < p.pop.size(); i++) {
			tPopulation.pop.add(p.pop.get(i));
		}
		for (int i = 0; i < q.pop.size(); i++) {
			tPopulation.pop.add(q.pop.get(i));
		}
		return tPopulation;
	}
	private Population makenewpop(Population qpopulation2) {
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
			for (int j = 0; j < N; j++) {
				p.pop.get(j).gtemp = p.pop.get(j).fx[i];
			}
			sortOfsubm(p);
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
	/*
	 *   0表示a支配b
	 *   1表示b支配a
	 *   -1表示互不支配
	 * */
	private int check_dominate(Individual a, Individual b) {
		int flag;

		flag = 0;
		for (int i = 0; i < a.fx.length; i++) {
			if (a.fx[i] > b.fx[i]) {
				flag = 1;
			}
		}
		if (flag == 0) {
			return 0;
		}

		flag = 0;
		for (int i = 0; i < a.fx.length; i++) {
			if (a.fx[i] < b.fx[i]) {
				flag = 1;
			}
		}
		if (flag == 0) {
			return 1;
		}

		return -1;

	}
	private ArrayList<Population> fast_nondominated_sort(Population p) {
		int k = 0;

		Population F_ = new Population();
		ArrayList<Population> PF = new ArrayList<Population>();
		PF.add(new Population());
		p.iniPopSiandNi(); //初始化si和si
		for (int i = 0; i < p.pop.size(); i++) {
			for (int j = 0; j < p.pop.size(); j++) {
				int flag = check_dominate(p.pop.get(i), p.pop.get(j));
				if (flag == 1) {
					p.pop.get(i).ni++;
				} else if ( flag == 0) {
					p.pop.get(i).si.add(p.pop.get(j));
				}
			}
			if (p.pop.get(i).ni == 0) {
				PF.get(0).pop.add(p.pop.get(i));
			}
		}

		F_ = PF.get(0);
		while (!F_.pop.isEmpty()) {
			Population hPopulation = new Population();
			for (int i = 0; i < F_.pop.size(); i++) {
				Individual individual = F_.pop.get(i);

				for (int j = 0; j < individual.si.size(); j++) {
					individual.si.get(j).ni--;
					if (individual.si.get(j).ni == 0) {
						hPopulation.pop.add(individual.si.get(j));
					}
				}
			}
			k++;
			PF.add(hPopulation);
//			PF.set(k, hPopulation);
			F_ = PF.get(k);


		}

		return PF;
	}
	public static void main(String[] args) {
		test test = new test();
	}
}
