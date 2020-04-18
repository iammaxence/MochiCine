package test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import tools.ApiTools;

public class TestApiExterne {

	public static void main(String[] args) {
		
//		Integer[] v= {63247, 72750, 60735, 456, 46952, 60059, 1433, 1434, 1407, 87393, 4194, 56570, 71886, 48891, 83631, 1021, 32573, 71789, 502, 2224};
//		ArrayList<Integer> ids= new ArrayList<Integer>();
//		for(Integer va : v)
//			ids.add(va);
//		
//		try {
//			ApiTools.moreInfosSeries(ids);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
		try {
			ApiTools.recherche("a3be1be132d237a0716cc27bdae1b2f0", "avengers");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
