package MOEA;

import java.util.Comparator;

public class GtempComparator implements Comparator {

	@Override
	public  int compare(Object  obj1, Object obj2){
		Individual u1 = (Individual)obj1;
		Individual u2 = (Individual)obj2;
		if(u1.gtemp > u2.gtemp){
			return -1;
		}else if (u1.gtemp < u2.gtemp) {
			return 1;
		} else if (u1.gtemp == u2.gtemp) {
			return 0;
		}
		return 0;

	}
}
