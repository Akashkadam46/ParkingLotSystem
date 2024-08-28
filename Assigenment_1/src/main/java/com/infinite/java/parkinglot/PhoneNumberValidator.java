package com.infinite.java.parkinglot;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public class PhoneNumberValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		String phone = (String) value;
		char ch = phone.charAt(0);

		int foundCount = 0;
		
		SessionFactory sf = SessionHelper.getConnection();

        Session session = sf.openSession();

        Transaction t = session.beginTransaction();

        Criteria cr = session.createCriteria(Registration_Entity.class);

        cr.add(Restrictions.eq("phoneNumber", value));

        cr.setProjection(Projections.rowCount());

        Long count = (Long) cr.uniqueResult();

        if (count == 1) {

             FacesMessage msg = new FacesMessage("PhoneNumber already registered ", uniqueColumn);

             context.addMessage(comp.getClientId(context), msg);

             throw new ValidatorException(msg);}



           

              
		
		
		

		// ITERATION OVER PHONE NUMBER.
		for (int i = 0; i < phone.length(); i++) {
			if (!Character.isDigit(phone.charAt(i))) {
				foundCount++;
			}
		}

		if (foundCount == 0) {
			if (phone.length() > 10) {
				FacesMessage msg = new FacesMessage("Phone number must not be greater than 10 digits...!",
						uniqueColumn);
				context.addMessage(comp.getClientId(context), msg);
				throw new ValidatorException(msg);
			}

			else if (phone.length() < 10) {
				FacesMessage msg = new FacesMessage("Phone number must not be less than 10 digits...!", uniqueColumn);
				context.addMessage(comp.getClientId(context), msg);
				throw new ValidatorException(msg);
			}
		}
		// TO CHECK IF ANYTHING EXCEPT DIGITS EXISTS.
		if (foundCount > 0) {
			FacesMessage msg = new FacesMessage("Letters, special characters or white spaces are not allowed",
					uniqueColumn);
			context.addMessage(comp.getClientId(context), msg);
			throw new ValidatorException(msg);
		}

		// TO CHECK IF THE NUMBER STARTS FROM ZERO.
		if (ch == '0') {
			FacesMessage msg = new FacesMessage("Phone number cannot start with zero", uniqueColumn);
			context.addMessage(comp.getClientId(context), msg);
			throw new ValidatorException(msg);

		}

		// TO CHECK IF THE NUMBER STARTS FROM ONE.
		if (ch == '1') {
			FacesMessage msg = new FacesMessage("Phone number cannot start with 1", uniqueColumn);
			context.addMessage(comp.getClientId(context), msg);
			throw new ValidatorException(msg);

		}

	}

}
