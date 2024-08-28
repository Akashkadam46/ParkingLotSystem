package com.infinite.java.parkinglot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class SlotDaoImpl {

	private boolean isSlotAvailableForLoggedUser;
	private List<SlotAvailability> currentSlotAvl;
	private boolean isDuplicateUser;

	private Date restrictionDate;

	public Date getRestrictionDate() {
		return restrictionDate;
	}

	public void setRestrictionDate(Date restrictionDate) {
		this.restrictionDate = restrictionDate;
	}

	public boolean getIsDuplicateUser() {
		return isDuplicateUser;
	}

	public void setIsDuplicateUser(boolean isDuplicateUser) {
		this.isDuplicateUser = isDuplicateUser;
	}

	public boolean getIsSlotAvailableForLoggedUser() {
		return isSlotAvailableForLoggedUser;
	}

	public void setIsSlotAvailableForLoggedUser(boolean isSlotAvailableForLoggedUser) {
		this.isSlotAvailableForLoggedUser = isSlotAvailableForLoggedUser;
	}

	public List<SlotAvailability> getCurrentSlotAvl() {
		return currentSlotAvl;
	}

	public void setCurrentSlotAvl(List<SlotAvailability> currentSlotAvl) {
		this.currentSlotAvl = currentSlotAvl;
	}

	public boolean isDuplicateUserForSlot() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Registration_Entity loggedUser = (Registration_Entity) sessionMap.get("loggedInUser");
		SessionFactory sf = null;
		Session session = null;
		long countOfDuplicacy = 0;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(Slot.class);
			cr.add(Restrictions.eq("rId", loggedUser.getrId()));
			cr.setProjection(Projections.rowCount());
			countOfDuplicacy = (long) cr.uniqueResult();

			if (countOfDuplicacy > 0) {
				isDuplicateUser = true;
			} else {
				isDuplicateUser = false;
			}
		} finally {
			session.close();
			sf.close();
		}
		return isDuplicateUser;

	}

	public boolean isEmtyMessegeShowable() {

		if (isDuplicateUserForSlot() == true) {
			isSlotAvailableForLoggedUser = false;
			return false;
		}

		// currentSlotAvl = showAllAvailabilities();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Registration_Entity loggedUser = (Registration_Entity) sessionMap.get("loggedInUser");
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(SlotAvailability.class);
			cr.add(Restrictions.eq("reservedFor", loggedUser.getCategory().toString()));
			cr.add(Restrictions.eq("availability", 1));
			currentSlotAvl = cr.list();
		} finally {
			session.close();
			sf.close();
		}

		if (currentSlotAvl == null || currentSlotAvl.size() == 0) {
			isSlotAvailableForLoggedUser = false;
			return true;
		} else {
			isSlotAvailableForLoggedUser = true;
			return false;
		}
	}

	// private static HashMap<Integer, String> map;
	//
	// static {
	// map = new HashMap<Integer, String>();
	// map.put(1, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(2, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(3, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(4, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(5, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(6, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(7, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(8, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(9, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(10, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(11, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(12, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(13, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(14, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(15, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(16, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(17, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(18, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(19, "yyyy/MM/dd : yyyy/MM/dd");
	// map.put(20, "yyyy/MM/dd : yyyy/MM/dd");
	//
	// }
	//
	// List<String> slots = new ArrayList<String>();
	//
	// public Date sdate;
	// public Date edate;
	//
	// public Date getEdate() {
	// return edate;
	// }
	//
	// public void setEdate(Date edate) {
	// this.edate = edate;
	// }
	//
	// public List<String> getSlots() {
	// return slots;
	// }
	//
	// public void setSlots(List<String> slots) {
	// this.slots = slots;
	// }
	//
	// public Date getSdate() {
	// return sdate;
	// }
	//
	// public void setSdate(Date sdate) {
	// this.sdate = sdate;
	// }
	//
	// public List<String> slots(int sNo) {
	//
	// SessionFactory sf = SessionHelper.getConnection();
	// Session se = sf.openSession();
	// Criteria cr = se.createCriteria(Slot.class);
	// cr.add(Restrictions.eq("slotNumber", sNo));
	// cr.add(Restrictions.eq("startDate", this.getSdate()));
	// cr.add(Restrictions.eq("endDate", this.getEdate()));
	// return slots;
	//
	// }
	//
	// public List<Slot> viewscheduleAppointment(String sdate) throws
	// ParseException {
	//
	// Date updateDate = new SimpleDateFormat("yyyy/MM/dd").parse(sdate);
	//
	// SessionFactory sf = SessionHelper.getConnection();
	// Session session = sf.openSession();
	// Criteria cr = session.createCriteria(Slot.class);
	// cr.add(Restrictions.eq("startDate", sdate));
	// List<Slot> list = cr.list();
	// return list;
	// }
	//
	// public String viewAvailableSlotAndBook(String sdate, String edate) throws
	// ParseException {
	//
	// HashMap<Integer, String> localmap = new HashMap<Integer, String>();
	// localmap.putAll(map);
	//
	// List<Slot> list = viewscheduleAppointment(sdate);
	//
	// if (list.size() == 0) {
	// System.out.println("***Available Slots***");
	// for (Map.Entry m : map.entrySet()) {
	//
	// System.out.println(m.getKey() + "] " + m.getValue());
	// }
	// return "No Slot is Booked . You can schedule appointment by using option
	// 1. Add Appointment. ";
	// } else {
	//
	// for (Map.Entry m : map.entrySet()) {
	//
	// for (Slot appointment : list) {
	// if (m.getKey() == (Integer) appointment.getSlotNumber()) {
	// localmap.remove(m.getKey());
	// }
	// }
	//
	// }
	//
	// // ====================================
	// System.out.println("***Available Slots***");
	// for (Map.Entry m : localmap.entrySet()) {
	//
	// System.out.println(m.getKey() + "] " + m.getValue());
	// }
	// System.out
	// .println("You can book appointment for above available slots.choose
	// option 1 to Book Appointment.");
	// }
	//
	// return " ";
	// }
	//
	// private List<Slot> parkingSlots;
	//
	// private LocalDate startDate;
	// private LocalDate endDate;
	// private boolean booked;
	//
	// public boolean isBooked() {
	// return booked;
	// }
	//
	// public void setBooked(boolean booked) {
	// this.booked = booked;
	// }
	//
	// // public void generateSlots() {
	// // parkingSlots = new ArrayList<>();
	// // int slotCount = 20;
	// // LocalDate currentDate = startDate;
	// // while (!currentDate.isAfter(endDate) && slotCount > 0) {
	// // parkingSlots.add(new Slot(slotCount, currentDate, currentDate));
	// // currentDate = currentDate.plusDays(1);
	// // slotCount--;
	// // }
	// // }
	//
	// // public void bookParkingSlots() {
	// // for (Slot slot : parkingSlots) {
	// // if (slot.getStartDate().equals(startDate) ||
	// // slot.getEndDate().equals(endDate)) {
	// // slot.setBooked(true);
	// // }
	// // }
	// // }
	//
	// private static final double DAILY_RATE = 10.0;
	// private static final double TAX_RATE = 0.05;
	// private static final double DISCOUNT_RATE = 0.1;
	// private static final int MINIMUM_DISCOUNT_DAYS = 7;
	//
	// public double calculateSlotPrice(LocalDate startDate, LocalDate endDate)
	// {
	// if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
	// throw new IllegalArgumentException("Invalid booking dates");
	// }
	//
	// // Calculate the number of days between start and end dates (inclusive)
	// long bookingDuration = ChronoUnit.DAYS.between(startDate, endDate) + 1;
	//
	// // Calculate the total slot price before tax and discounts
	// double totalAmount = DAILY_RATE * bookingDuration;
	//
	// // Calculate taxes
	// double taxes = totalAmount * TAX_RATE;
	//
	// // Apply discounts for long-term bookings
	// double discount = 0.0;
	// if (bookingDuration > MINIMUM_DISCOUNT_DAYS) {
	// discount = totalAmount * DISCOUNT_RATE;
	// }
	//
	// // Calculate the final price after applying taxes and discounts
	// double finalPrice = totalAmount + taxes - discount;
	//
	// return finalPrice;
	// }

	private final double DAILY_RATE_TWO_WHEELER = 10.0;
	private final double DAILY_RATE_FOUR_WHEELER = 20.0;
	private final double PANALTY_RATE_TWO_WHEELER = 100.0;
	private final double PANALTY_RATE_FOUR_WHEELER = 200.0;
	private final double TAX_RATE = 1.02;

	public double calculateTotalBill(Date startDate, Date endDate, SCategory vehicleType) throws ParseException {

		double bill = 0;
		System.out.println(startDate + "Start Date");
		System.out.println(endDate + " End Date");
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(startDate);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);

		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(endDate);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);

		long diffInMillis = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
		long noOfDays = diffInMillis / (24 * 60 * 60 * 1000);
		System.out.println(noOfDays + " No of days");
		System.out.println("Vehicle category while calculating payment :" + vehicleType);

		if (vehicleType.toString().equals(SCategory.FOUR_wheeler.toString())) {
			bill = noOfDays * DAILY_RATE_FOUR_WHEELER * TAX_RATE;
		} else if (vehicleType.toString().equals(SCategory.TWO_Wheeler.toString())) {
			bill = noOfDays * DAILY_RATE_TWO_WHEELER * TAX_RATE;
		}
		System.out.println("Bill ______" + bill);
		return bill;

	}

	public boolean validateSlotDetails(Slot slot) {
		boolean isValid = true;
		List<Slot> slotList = new ArrayList<>();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Slot.class);

		slotList = cr.list();

		if (slotList.size() == 0 || slotList == null) {
			return isValid;
		} else {

			Date startDateForm = slot.getStartDate();
			Date endDateForm = slot.getEndDate();

			for (Slot slotDB : slotList) {

				Date startDateDb = slotDB.getStartDate();
				Date endDateDb = slotDB.getEndDate();

				// if ((startDateForm.compareTo(startDateDb) <= 0 &&
				// endDateForm.compareTo(startDateDb) > 0)
				// || (startDateForm.compareTo(endDateDb) < 0 &&
				// endDateForm.compareTo(endDateDb) >= 0)
				// || (startDateForm.compareTo(startDateDb) >= 0 &&
				// endDateForm.compareTo(endDateDb) <= 0)
				// || (startDateForm.compareTo(startDateDb) < 0 &&
				// endDateForm.compareTo(endDateDb) > 0)) {
				//
				// if(slot.getSlotNumber())
				//
				// }

			}

		}

		return isValid;

	}

	public List<Integer> loadAvailableSlots() {
		System.out.println("in load available slots");
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		// Registration_Entity loggedUser = (Registration_Entity)
		// sessionMap.get("loggedInUser");
		// Slot slot=(Slot)
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("slot");
		// slot.setTotalBill(0);

		List<Integer> slotList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
		List<Integer> slotNumberExclude = new ArrayList<>();

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Slot.class);
		List<Slot> slotObjList = cr.list();
		session.close();
		sf.close();

		System.out.println("records in slots table : " + slotObjList == null + " " + slotObjList.size());

		if (slotObjList == null || slotObjList.size() == 0) {
			return slotList;
		} else {

			for (Slot slot : slotObjList) {
				System.out.print("1 ");
				System.out.println(slot);
			}

			Date startDateForm = (Date) sessionMap.get("startDate");
			Date endDateForm = (Date) sessionMap.get("endDate");

			for (Slot slotDB : slotObjList) {

				Date startDateDb = slotDB.getStartDate();
				Date endDateDb = slotDB.getEndDate();

				System.out.println("startDateDb" + startDateDb);
				System.out.println("endDateDb" + endDateDb);
				System.out.println("startDateForm" + startDateForm);
				System.out.println("endDateForm" + endDateForm);

				if ((startDateForm.compareTo(startDateDb) <= 0 && endDateForm.compareTo(startDateDb) > 0)
						|| (startDateForm.compareTo(endDateDb) < 0 && endDateForm.compareTo(endDateDb) >= 0)
						|| (startDateForm.compareTo(startDateDb) >= 0 && endDateForm.compareTo(endDateDb) <= 0)
						|| (startDateForm.compareTo(startDateDb) < 0 && endDateForm.compareTo(endDateDb) > 0)) {

					slotNumberExclude.add(slotDB.getSlotNumber());
				}

			}

			slotList.removeAll(slotNumberExclude);

		}

		return slotList;
	}

	// public String bookParkingSlot(Slot slot) {
	// Map<String, Object> sessionMap =
	// FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	//
	// Registration_Entity loggedInUser = (Registration_Entity)
	// sessionMap.get("loggedInUser");
	//
	// slot.setrId(loggedInUser.getrId());
	//
	// if (loggedInUser.getCategory().equals("ADMIN")) {
	// slot.setBookedBy("ADMIN");
	//
	// } else {
	// slot.setBookedBy("STAFF");
	//
	// }
	//
	// slot.setRegister(loggedInUser);
	// slot.setTotalBill(calculateTotalBill(slot.getStartDate(),
	// slot.getEndDate(), slot.getsCategory()));
	//
	// SessionFactory sf = SessionHelper.getConnection();
	// Session session = sf.openSession();
	// Transaction tr = session.beginTransaction();
	// session.persist(slot);
	// tr.commit();
	//
	// return "Thanks.xhtml";
	// }

	public List<SlotAvailability> showAllAvailabilities() {
		System.out.println("Checking the availabilities");
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Registration_Entity loggedUser = (Registration_Entity) sessionMap.get("loggedInUser");
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(SlotAvailability.class);
			cr.add(Restrictions.eq("reservedFor", loggedUser.getCategory().toString()));
			cr.add(Restrictions.eq("availability", 1));
			currentSlotAvl = cr.list();

		} catch (HibernateException e) {
			System.out.println("Exception is there dud");
			e.printStackTrace();
		} finally {
			System.out.println("closing the session");
			session.close();
			sf.close();
		}
		return currentSlotAvl;

	}

	public boolean isButtonShowable(int avl) {
		return avl == 1 ? true : false;
	}

	public String getButtonValue(int avl) {
		return avl == 1 ? "AVAILABLE" : "BOOKED";
	}

	public String goForBooking(SlotAvailability sAvl) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		System.out.println(sAvl.getReservedFor());
		sessionMap.put("selectedAvailability", sAvl);

		return "Booking.xhtml?faces-redirect=true";
	}

	public Date loadStartDateForBooking() throws ParseException {
		Date todaysDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			todaysDate = sdf.parse(todaysDate.toString());
			// this.restrictionDate=DateUtils.addDays(todaysDate,15);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return todaysDate;
	}

	public Date loadEndDateForBooking() throws ParseException {
		Date todaysDate = new Date();

		Date endDate = DateUtils.addDays(todaysDate, 28);

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			endDate = sdf.parse(endDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}

	public String generatePaymentSave(Slot slot) throws ParseException {

		double totalBill = calculateTotalBill(slot.getStartDate(), slot.getEndDate(), slot.getsCategory());
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		// sessionMap.put("OwnerName", register.getName());
		sessionMap.put("generatedtotalBill", totalBill);
		sessionMap.put("forVehicleNumber", slot.getVehicleNumber());
		sessionMap.put("billVehicleType", slot.getsCategory());
		sessionMap.put("billOwner", "aa");
		slot.setTotalBill(totalBill);
		sessionMap.put("confirmSlot", slot);

		return "ConfirmPayment.xhtml?faces-redirect=true";
	}

	public String confirmParkingSlot() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		Slot confirmSlot = (Slot) sessionMap.get("confirmSlot");
		SlotAvailability slotAvl = (SlotAvailability) sessionMap.get("selectedAvailability");
		Registration_Entity loggedUser = (Registration_Entity) sessionMap.get("loggedInUser");

		System.out.println(confirmSlot);
		System.out.println(slotAvl);
		System.out.println(loggedUser);

		confirmSlot.setRegister(loggedUser);
		slotAvl.setAvailability(0);
		confirmSlot.setBookedBy(loggedUser.getCategory().toString());
		confirmSlot.setrId(confirmSlot.getRegister().getrId());
		confirmSlot.setSlotNumber(slotAvl.getSlotNumber());

		System.out.println(confirmSlot.getBookedBy());
		System.out.println(confirmSlot.getrId());
		System.out.println(confirmSlot.getSlotNumber());

		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction tr = session.beginTransaction();
		session.update(slotAvl);
		session.save(confirmSlot);
		tr.commit();
		session.close();
		sf.close();

		return "Thanks.xhtml?faces-redirect=true";

	}

	public String getBookingOwnerName() {
		Map<String, Object> sesMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Registration_Entity loggedUser = (Registration_Entity) sesMap.get("loggedInUser");
		if (loggedUser != null) {
			return loggedUser.getName();
		}
		return "";
	}

	public Slot getCurrentSlot() {
		Map<String, Object> sesMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Registration_Entity loggedUser = (Registration_Entity) sesMap.get("loggedInUser");
		SessionFactory sf = null;
		Session session = null;
		Slot foundSlot = null;
		try {
			sf = SessionHelper.getConnection();

			session = sf.openSession();

			Criteria cr = session.createCriteria(Slot.class);
			Restrictions.eq("rId", loggedUser.getrId());

			foundSlot = (Slot) cr.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return foundSlot;

	}

	public String renewSlotDetails() {
		Slot updatingSlot = getCurrentSlot();
		Map<String, Object> sesMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sesMap.put("updatingSlot", updatingSlot);
		return "RenewBookedSlot.xhtml?faces-redirect=true";
	}

	public List<Integer> loadAvailableSlotsForLoggedUser() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Registration_Entity loggedUser = (Registration_Entity) sessionMap.get("loggedInUser");
		SessionFactory sf = null;
		Session session = null;
		List<Integer> avlSlots = null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			Criteria cr = session.createCriteria(SlotAvailability.class);
			cr.add(Restrictions.eq("reservedFor", loggedUser.getCategory().toString()));
			cr.add(Restrictions.eq("availability", 1));
			cr.setProjection(Projections.property("slotNumber"));
			avlSlots = cr.list();

		} catch (HibernateException e) {
			System.out.println("Exception is there dud");
			e.printStackTrace();
		} finally {
			System.out.println("closing the session");
			session.close();
			sf.close();
		}

		if (avlSlots.size() > 0) {
			return avlSlots;
		} else {
			return null;
		}
	}

	public String renewPaymentDetails(Slot renewedSlot, int newSlotNum) {
		if (renewedSlot != null) {
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("oldSlotNumber", renewedSlot.getSlotNumber());
			sessionMap.put("newSlotNumber", newSlotNum);
			double penaltyCharge = 0;
			renewedSlot.setSlotNumber(newSlotNum);
			if (renewedSlot.getsCategory().equals("TWO_Wheeler")) {
				penaltyCharge = PANALTY_RATE_TWO_WHEELER * TAX_RATE;
			} else {
				penaltyCharge = PANALTY_RATE_FOUR_WHEELER * TAX_RATE;
			}
			System.out.println("penalty charge " + penaltyCharge);
			sessionMap.put("paidBefore", renewedSlot.getTotalBill());
			sessionMap.put("penaltyCharge", penaltyCharge);

			double newTotalBill = 0;
			try {
				newTotalBill = calculateTotalBill(renewedSlot.getStartDate(), renewedSlot.getEndDate(),
						renewedSlot.getsCategory());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sessionMap.put("newTotalBill", newTotalBill);

			System.out.println("New total bill :  " + newTotalBill);

			double updatedFinalBill = newTotalBill - renewedSlot.getTotalBill();
			updatedFinalBill += penaltyCharge;
			renewedSlot.setTotalBill(updatedFinalBill < 0 ? updatedFinalBill * -1 : updatedFinalBill);

			System.out.println("FinalUpdatedBill :" + updatedFinalBill);

			sessionMap.put("updatedFinalBill", updatedFinalBill);

			sessionMap.put("renewedFinalSlot", renewedSlot);

			return "ConfirmRenewwalPay.xhtml?faces-redirect=true";
		} else {
			return "RenewBookedSlot.xhtml?faces-redirect=true";
		}
	}

	public String renewSlotConfirm(Slot renewedSlot) {
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = SessionHelper.getConnection();

			session = sf.openSession();

			Transaction tr = session.beginTransaction();
			session.update(renewedSlot);
			tr.commit();
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			int slotNumber = (int) sessionMap.get("oldSlotNumber");

			SlotAvailability slotAvl1 = getSlotAvailabilityFromSlotNumber(slotNumber);

			if (slotAvl1 != null) {
				slotAvl1.setAvailability(1);
			}

			tr = session.beginTransaction();
			session.update(slotAvl1);
			tr.commit();

			SlotAvailability slotAvl2 = getSlotAvailabilityFromSlotNumber(renewedSlot.getSlotNumber());

			if (slotAvl2 != null) {
				slotAvl2.setAvailability(0);
			}

			tr = session.beginTransaction();
			session.update(slotAvl2);
			tr.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}

		return "Thanks.xhtml?faces-redirect=true";
	}

	public String initiateRefundProcess() {

		System.out.println("initiating refund");
		Slot currentSlot = getCurrentSlot();
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.put("refundInitiatedSlot", currentSlot);
		double totalBil = currentSlot.getTotalBill();
		double cancelationFee = totalBil * 0.2;
		double refundableAmmount = totalBil - cancelationFee;

		sessionMap.put("cancelationFee", cancelationFee);
		sessionMap.put("refundableAmmount", refundableAmmount);

		return "initiatingRefund.xhtml?faces-redirect=true";

	}

	public SlotAvailability getSlotAvailabilityFromSlotNumber(int slotNo) {
		SessionFactory sf = null;
		Session session = null;
		SlotAvailability slotAvl = null;

		try {
			sf = SessionHelper.getConnection();

			session = sf.openSession();

			Criteria cr = session.createCriteria(SlotAvailability.class);
			cr.add(Restrictions.eq("slotNumber", slotNo));

			slotAvl = (SlotAvailability) cr.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return slotAvl;
	}

	public String refundTrigering() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Slot refundInitiatedSlot = (Slot) sessionMap.get("refundInitiatedSlot");
		SlotAvailability cancelingAvl=getSlotAvailabilityFromSlotNumber(refundInitiatedSlot.getSlotNumber());
		cancelingAvl.setAvailability(1);
		
		SessionFactory sf = null;
		Session session = null;
		Transaction tr=null;
		try {
			sf = SessionHelper.getConnection();
			session = sf.openSession();
			tr=session.beginTransaction();
			session.delete(refundInitiatedSlot);
			session.update(cancelingAvl);
			tr.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
			sf.close();
		}
		return "Thanks.xhtml?faces-redirect=true";
	}
}
