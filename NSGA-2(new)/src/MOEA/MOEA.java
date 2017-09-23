package MOEA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Handler;

import javax.swing.text.DefaultEditorKit.CutAction;
import javax.xml.ws.AsyncHandler;


public class MOEA {
	Population qPopulation = new Population();
	Population rPopulation = new Population();
	Population pPopulation = new Population();
	Population nPopulation = new Population();
	Population F_ = new Population();
	ArrayList<Population>  PF = new ArrayList<Population>();
	int popsize = 100;
	int maxgen = 100;
	int gen = 0;
	int i = 0;
	String aa= "";
	String bb= "";
	public MOEA() {
		Generation generation = new Generation();
		pPopulation.inipop(popsize);
//		qPopulation.printFx("qPop(ini):");
//		pPopulation.printFx("pPop(ini):");
		qPopulation = generation.make_new_pop(pPopulation);
//		qPopulation.printFx("qPop(ini):");
//		pPopulation.printFx("pPop(ini):");
		qPopulation.ifsomthingsame();
		for (int k = 0; k < maxgen; k++) {
			statistics();
			rPopulation.pop.clear();
			rPopulation = merge(pPopulation, qPopulation);
//			rPopulation.printFx("rPop");
			pPopulation.pop.clear();
			qPopulation.pop.clear();
			PF.clear();
			PF = fast_nondominated_sort(rPopulation);
			nPopulation.pop.clear();
			i = 0;
			F_.pop.clear();
			F_ = PF.get(i);
			System.out.println("PF.size:"+PF.size());
			while (true) {
				if (F_.pop.size() + nPopulation.pop.size() > popsize || (F_.pop.size()+nPopulation.pop.size() == popsize & F_.pop.size()==0)) {
					break;
				}
				crowding_distance_assignment(F_);
				Sort_F_(F_);
				nPopulation = merge(nPopulation, F_);
				F_.pop.clear();
				i++;
				F_ = PF.get(i);
			}
			crowding_distance_assignment(F_);
			Sort_F_(F_);
			nPopulation = merge(nPopulation, cutF_(F_));
			F_.pop.clear();
			qPopulation.pop.clear();
//			nPopulation.printFx("npopulation");
			qPopulation = generation.make_new_pop(nPopulation);
//			qPopulation.printFx("qpopulation");
			qPopulation.ifsomthingsame();
			pPopulation = merge(pPopulation, nPopulation);

		}
		nPopulation = pPopulation;
		for (int i = 0; i < nPopulation.pop.size(); i++) {
			System.out.println(nPopulation.pop.get(i).fx[0]+" "+nPopulation.pop.get(i).fx[1]);
			Double temDouble1 = new Double(nPopulation.pop.get(i).fx[0]);
			Double temDouble2 = new Double(nPopulation.pop.get(i).fx[1]);
			aa = aa+temDouble1.toString();
			aa += " ";
			aa += temDouble2.toString();
			aa +="\n";
		}


		byte[] buff=new byte[]{};
        try
        {
            buff=aa.getBytes();
            FileOutputStream out=new FileOutputStream("F://Users//Administrator//PycharmProjects//handledata//data.txt");
            out.write(buff,0,buff.length);



        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

	}
	private Population cutF_(Population p) {
		Population tPopulation = new Population();
		for (int i = 0; i < popsize-nPopulation.pop.size(); i++) {
			tPopulation.pop.add(p.pop.get(i));
		}
		return tPopulation;
	}
	private void statistics() {
		gen++;
		System.out.println("第"+gen+"代");

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
	@SuppressWarnings("unchecked")
	private void Sort_F_(Population p) {
		Collections.sort(p.pop, new CrowdisComparator());

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

	@SuppressWarnings("unchecked")
	private void sortOfsubm(Population p) {
		Collections.sort(p.pop, new GtempComparator());

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
			PF.add(new Population());
			PF.set(k, hPopulation);
			F_ = PF.get(k);


		}

		return PF;
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

	private void make_new_pop(Population pPopulation2, Population qPopulation2) {
		// TODO Auto-generated method stub

	}
	public static void main(String[] args) {
		MOEA moea = new MOEA();
	}
}
