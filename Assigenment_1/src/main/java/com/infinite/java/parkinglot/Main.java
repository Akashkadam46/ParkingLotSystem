package com.infinite.java.parkinglot;

import java.util.Date;
import java.util.Scanner;

public class Main {
	    
	    public static void main(String[] args) {
			
	    	 
	    	  Registration_Entity re = new Registration_Entity();
	    	  Scanner sc = new Scanner(System.in);
	    	  System.out.println("Register here");
	    	  System.out.println("*************-----------*****************");
	    	  System.out.println();
	    	  
	    	  System.out.println("Enter Customer Name: ");
	    	  re.setName(sc.nextLine());
	    	  System.out.println("Enter the Email ");
	    	  re.setEmail(sc.next());
	    	
	    	  
		}

}
