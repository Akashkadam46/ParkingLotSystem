package com.infinite.java.parkinglot;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class VehicleNumberValidator implements Validator {

	private static final String VEHICLE_NUMBER_PATTERN = "^[A-Za-z]{2}\\s?[0-9]{1,2}\\s?[A-Za-z]{0,3}\\s?[0-9]{4}$";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String vehicleNumber = value.toString();
		if (!vehicleNumber.matches(VEHICLE_NUMBER_PATTERN)) {
			FacesMessage msg = new FacesMessage("Vehicle number is not valid! eg:(MH 12 RF 3638)");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(component.getClientId(context), msg);
			throw new ValidatorException(msg);
		}
	}
}
