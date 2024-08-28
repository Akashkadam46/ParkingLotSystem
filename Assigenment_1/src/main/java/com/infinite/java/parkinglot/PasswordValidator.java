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
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

public class PasswordValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub

		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes()

				.get("currentEntity");

		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		boolean isValid = false;
		SessionFactory sf = null;
		Session session = null;

		try {

			sf = SessionHelper.getConnection();

			session = sf.openSession();

			Transaction t = session.beginTransaction();

			Criteria cr = session.createCriteria(Registration_Entity.class);

			cr.add(Restrictions.eq("passWord", value));

			cr.setProjection(Projections.rowCount());

			Long count = (Long) cr.uniqueResult();

			if (count != 1) {

				FacesMessage msg = new FacesMessage("you have entered wrong password ", uniqueColumn);

				context.addMessage(comp.getClientId(context), msg);

				throw new ValidatorException(msg);
			}

		}

		catch (NoResultException ex) {

			isValid = true;
		} finally {
			session.close();
			sf.close();

		}

	} // good! no result means unique validation was OK!

}
