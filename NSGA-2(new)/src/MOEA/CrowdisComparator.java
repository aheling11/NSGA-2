package MOEA;

import java.util.Comparator;

public class CrowdisComparator implements Comparator {

	@Override
	public  int compare(Object  obj1, Object obj2){
		Individual u1 = (Individual)obj1;
		Individual u2 = (Individual)obj2;
		if(u1.crowDis > u2.crowDis){
			return -1;
		}else if ( u1.crowDis<u2.crowDis ) {
			return 1;
		}else {
			return 0;
		}



	}

}
