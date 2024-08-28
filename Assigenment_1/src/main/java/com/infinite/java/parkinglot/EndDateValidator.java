package com.infinite.java.parkinglot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

public class EndDateValidator implements Validator, Serializable {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {

		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		try {

			Date endDate = (Date) value;

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessionMap.put("endDate", endDate);
			Date startDate = (Date) sessionMap.get("startDate");

			System.out.println("StartDate" + startDate);
			System.out.println("EdtDate" + endDate);

			if (startDate.compareTo(endDate) > 0) {
				FacesMessage msg = new FacesMessage("Start date cannot be grater than End date !", uniqueColumn);

				context.addMessage(comp.getClientId(context), msg);

				throw new ValidatorException(msg);
			}
			loadAvailableSlots();

		} catch (Exception e) {
			e.printStackTrace();
		}

	} // good! no result means unique validation was OK!
	
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

			for(Slot slot:slotObjList){
				System.out.print("1 ");
				System.out.println(slot);
			}
			
			Date startDateForm = (Date) sessionMap.get("startDate");
			Date endDateForm = (Date) sessionMap.get("endDate");

			for (Slot slotDB : slotObjList) {

				Date startDateDb = slotDB.getStartDate();
				Date endDateDb = slotDB.getEndDate();
				

				System.out.println("startDateDb"+startDateDb);
				System.out.println("endDateDb"+endDateDb);
				System.out.println("startDateForm"+startDateForm);
				System.out.println("endDateForm"+endDateForm);

				if ((startDateForm.compareTo(startDateDb) <= 0 && endDateForm.compareTo(startDateDb) > 0)
						|| (startDateForm.compareTo(endDateDb) < 0 && endDateForm.compareTo(endDateDb) >= 0)
						|| (startDateForm.compareTo(startDateDb) >= 0 && endDateForm.compareTo(endDateDb) <= 0)
						|| (startDateForm.compareTo(startDateDb) < 0 && endDateForm.compareTo(endDateDb) > 0)) {

					slotNumberExclude.add(slotDB.getSlotNumber());
				}

			}

			slotList.removeAll(slotNumberExclude);

		}
		
		sessionMap.put("parkingSlotList", slotList);

		return slotList;
	}

}