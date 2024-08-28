package com.infinite.java.parkinglot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;

public class StartDateValidator implements Validator, Serializable {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {

		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");

		try {

			Date startDate = (Date) value;

			Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			sessionMap.put("startDate", startDate);

		} catch (Exception e) {
			e.printStackTrace();
		}

	} // good! no result means unique validation was OK!

}