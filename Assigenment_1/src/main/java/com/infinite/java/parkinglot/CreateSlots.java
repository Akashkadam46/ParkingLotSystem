package com.infinite.java.parkinglot;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CreateSlots {

	public static void main(String[] args) {

		List<SlotAvailability> slotAvlList = new ArrayList<SlotAvailability>();
		slotAvlList.add(new SlotAvailability(1, 1, "ADMIN"));
		slotAvlList.add(new SlotAvailability(2, 1, "ADMIN"));
		slotAvlList.add(new SlotAvailability(3, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(4, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(5, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(6, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(7, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(8, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(9, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(10, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(11, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(12, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(13, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(14, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(15, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(16, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(17, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(18, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(19, 1, "STAFF"));
		slotAvlList.add(new SlotAvailability(20, 1, "STAFF"));

		// int flag = 0;
		// System.out.println("Going to initialise");
		// for (SlotAvailability sl : slotAvlList) {
		// sl.setAvailable(true);
		// System.out.println(flag);
		// if (flag < 2) {
		// sl.setReservedFor("ADMIN");
		// flag++;
		// } else {
		// sl.setReservedFor("STAFF");
		// }
		// System.out.println("1");
		// }
		System.out.println("creating connection to db");
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		for (SlotAvailability sl : slotAvlList) {

			session.save(sl);

		}
		tr.commit();
		session.close();
		sf.close();
		System.out.println("Done");

	}
}
