package com.infinite.java.parkinglot;

import java.util.Map;

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
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

public class OldPasswordValidation implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub

		Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String name = (String) map.get("nameUser");
		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes()

				.get("currentEntity");

		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		boolean isValid = false;
		System.out.println("username---------"+name);

		try {

			SessionFactory sf = SessionHelper.getConnection();

			Session session = sf.openSession();

			Transaction t = session.beginTransaction();

			Criteria cr = session.createCriteria(Registration_Entity.class);

			cr.add(Restrictions.eq("userName", name));

			// cr.add(Restrictions.eq("passWord", value));

			cr.setProjection(Projections.property("passWord"));

			String oldPass = (String) cr.uniqueResult();
			System.out.println("-----------------------------------------");
			System.out.println(oldPass);
			System.out.println(value.toString());
			
			String newPassEncr=EncryptedPassword.getCode(value.toString().trim());
			System.out.println(newPassEncr);

			if (newPassEncr.equals(oldPass)) {

				FacesMessage msg = new FacesMessage("Its existing password use different password..", uniqueColumn);

				context.addMessage(comp.getClientId(context), msg);

				throw new ValidatorException(msg);
			}

		}

		catch (NoResultException ex) {

			isValid = true;
		}

	} // good! no result means unique validation was OK!

}
