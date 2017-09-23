package MOEA;
import java.util.ArrayList;

public class Individual {
	public int lchromarr = 1;
	public int lchrom = 28;
	long[] chromarr = new long[lchromarr];
	public double variable = 0;
	double gtemp = 0;
	int ni = 0;
	ArrayList<Individual> si = new ArrayList<Individual>();
	int msize = 2;
	double[] fx = new double[msize];
	double crowDis = 0;


	public Individual() {
		initindividual();
	}

	/*
	 * 决策变量
	 * 初始化个体，每个个体中有一串关于x的数组，数组中的每一个变量都要初始化
	 * */
	private void initindividual() {
		for (int i = 0; i < chromarr.length; i++) {
			chromarr[i] = 0;
		}
		Myrandom random = new Myrandom();
		for (int i = 0; i < lchromarr; i++) {
			int mask = 1;
			int temp = 0;
			for (int j = 0; j < lchrom-1; j++) {
				if (random.flip(0.5)) {
					temp = temp | mask;
				}
				temp = temp << 1;
			}
			chromarr[i] = temp;
		}
		obj();
		gtemp = fx[0];
	}

	/*
	 * 个体的适应度计算，也就是目标函数
	 * */
	public void obj() {


		for (int i = 0; i < lchromarr; i++) {
			variable = -100000 + (chromarr[0] * 200000)/(Math.pow(2, lchrom)-1);
		}

		fx[0] = variable*variable;
		fx[1] = (variable-2)*(variable-2);
	}
//	public Individual copyself() {
//		Individual tIndividual = new Individual();
//		tIndividual.variable = variable;
//		tIndividual.gtemp = gtemp;
//		tIndividual.ni = ni;
//		for (int i = 0; i < si.size(); i++) {
//			tIndividual.si.add(si.get(i));
//		}
//		for (int i = 0; i < chromarr.length; i++) {
//			tIndividual.chromarr[i] = chromarr[i];
//		}
//		for (int i = 0; i < fx.length; i++) {
//			tIndividual.fx[i] = fx[i];
//		}
//		tIndividual.crowDis = crowDis;
//		return tIndividual;
//	}

	public void writtenChrom() {
		for (int i = 0; i < chromarr.length; i++) {
			int mask = 1;
			long temp = 0;
			int[] invertArr = new int[lchrom];
			temp = chromarr[i];
			for (int j = 0; j < lchrom; j++) {
				if ((temp&mask) == 1) {
					invertArr[lchrom-j-1] = 1;
				} else {
					invertArr[lchrom-j-1] = 0;
				}
				temp = temp >> 1;
			}
			for (int j = 0; j < invertArr.length; j++) {
				System.out.print(invertArr[j]);
			}
			System.out.println();
		}
	}

	public void printChromOfDec() {
		for (int i = 0; i < chromarr.length; i++) {

			System.out.printf("x%d = %d",i+1,chromarr[i]);
			if (i!=chromarr.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println();
	}
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {

			Individual iniIndividual = new Individual();
		}
	}
}
