package com.infinite.java.parkinglot;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CreateSlotAvailabilityMain {

	
	public static void main(String[] args) {
		
		SlotAvailability slotAvailability=new SlotAvailability();
		
		int [][] staffAvailability =new int[12][18];
		int [][] adminAvailability =new int[12][2];
		
		for(int monthIdx=0;monthIdx<12;monthIdx++){
			for (int slotIdx = 0; slotIdx < 18; slotIdx++) {
				staffAvailability[monthIdx][slotIdx]=1;
			}
		}
		
		for(int monthIdx=0;monthIdx<12;monthIdx++){
			for (int slotIdx = 0; slotIdx < 18; slotIdx++) {
				System.out.print(staffAvailability[monthIdx][slotIdx]+" ");
			}
			System.out.println();
		}
		
		for(int monthIdx=0;monthIdx<12;monthIdx++){
			for (int slotIdx = 0; slotIdx < 2; slotIdx++) {
				adminAvailability[monthIdx][slotIdx]=1;
			}
		}
		
		for(int monthIdx=0;monthIdx<12;monthIdx++){
			for (int slotIdx = 0; slotIdx < 2; slotIdx++) {
				System.out.print(adminAvailability[monthIdx][slotIdx]+" ");
			}
			System.out.println();
		}
		
		
//		slotAvailability.setStaffAvailability(staffAvailability);
//		slotAvailability.setAdminAvailability(adminAvailability);
//		
		SessionFactory sf= SessionHelper.getConnection();
		Session session=sf.openSession();
		Transaction tr=session.beginTransaction();
		
		session.save(slotAvailability);
		
		tr.commit();
		session.close();
		sf.close();
		
		System.out.println("Saved success");
	}
}
