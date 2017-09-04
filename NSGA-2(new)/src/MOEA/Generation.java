package MOEA;

public class Generation {
	private double pcross = 0.8;
	private double pmutation = 0.05;

	public Generation() {
		// TODO Auto-generated constructor stub
	}

	public Population make_new_pop(Population p) {
		int popsize = p.pop.size();
		Population tPopulation = new Population();
		tPopulation.inipop(popsize);
		int mate1 = select(p);
		int mate2 = select(p);
		while ( mate1 == mate2) {
			mate1 = select(p);
		}
		int i = 0;
		do {
			crossover(p.pop.get(mate1), p.pop.get(mate2), tPopulation.pop.get(i), tPopulation.pop.get(i+1));
			mutation(tPopulation.pop.get(i));
			mutation(tPopulation.pop.get(i+1));
//			if (check_dominate(tPopulation.pop.get(i), p.pop.get(mate1)) == 1) {
//				tPopulation.pop.set(i, p.pop.get(mate1).copyself() );
//			}
//
//			if (check_dominate(tPopulation.pop.get(i), p.pop.get(mate2)) == 1) {
//				tPopulation.pop.set(i+1, p.pop.get(mate2).copyself() );
//			}
			i += 2;
		} while (i<popsize-1);

		return tPopulation;
	}



	private void mutation(Individual individual) {
		Myrandom myrandom = new Myrandom();
		int mask = 0;
		int temp = 1;
		for (int i = 0; i < individual.lchrom-1; i++) {
			if (myrandom.flip(pmutation)) {
				mask = mask | (temp<<i);
			}
		}

		individual.chromarr[0] = individual.chromarr[0] ^ mask;
		individual.obj();
	}

	/**
	 *  @set 交叉操作
	 * */
	public void crossover(Individual parent1, Individual parent2, Individual child1, Individual child2) {
		int number_of_object = parent1.lchromarr;
		int lchrom = parent1.lchrom;
		Myrandom rMyrandom = new Myrandom();
		int icross = rMyrandom.rnd(1, lchrom-1);
		int mask = 1;
		int temp = 1;
		for (int i = 0; i < icross; i++) {
			temp = 1;
			mask = mask << 1;
			mask = mask | temp;
		}
		//概率
		if (rMyrandom.flip(pcross)) {
			for (int i = 0; i < number_of_object; i++) {
				child1.chromarr[i] = (parent1.chromarr[i]&mask | parent2.chromarr[i]&(~mask));
				child2.chromarr[i] = (parent1.chromarr[i]&(~mask) | parent2.chromarr[i]&mask);
			}
		} else {
			for (int i = 0; i < number_of_object; i++) {
				child1.chromarr[i] = parent1.chromarr[i];
				child2.chromarr[i] = parent2.chromarr[i];
			}
		}
		child1.obj();
		child2.obj();

	}

	private int select(Population p) {
		Myrandom myrandom = new Myrandom();
		int size = 2;
		int pick[] = new int[size];
		for (int i = 0; i < size; i++) {
			pick[i] = myrandom.rnd(0, p.pop.size()-1);
		}
		//排序pick
		int flag = check_dominate(p.pop.get(pick[0]), p.pop.get(pick[1]));
		if ( flag == 1) {
			return pick[0];
		} else if( flag == 0) {
			return pick[1];
		} else {
			return pick[0];
		}
	}

	/*
	 * @set    0 表示 a支配b
	 * 		   1 表示 b支配a
	 *		   -1表示互不支配
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

	public static void main(String[] args) {
		int popsize = 300;
		Population ppPopulation = new Population();
		Population tpPopulation = new Population();
		ppPopulation.inipop(popsize);
		ppPopulation.printPopChromx();
		Generation generation = new Generation();
		tpPopulation = generation.make_new_pop(ppPopulation);
		tpPopulation.printPopChromx();


	}
}
