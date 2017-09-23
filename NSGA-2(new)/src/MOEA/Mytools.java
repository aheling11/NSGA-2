package MOEA;

import java.util.Scanner;

public class Mytools {

	/**
     * Use  java.util.Scanner to read data from console
     *
     * @param prompt
     *
     * @return input string
     */
    public static void pauseTheProgram(String prompt) {
    	while (true) {
    		Scanner scanner = new Scanner(System.in);
    		System.out.print(prompt);
    		if (scanner.nextLine().equals("n")) {
    			break;
    		}

		}
    }
    public static void main(String[] args) {
		pauseTheProgram("11");
	}
}
