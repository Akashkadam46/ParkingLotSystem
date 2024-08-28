package com.infinite.java.parkinglot;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.infinite.java.parkinglot.AnswerValidation")
public class AnswerValidation implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		String uniqueColumn = (String)context.getAttributes().get("uniqueColumn");
		
		String ans = (String)value;
		
		if(ans.length() < 4 )
		{
			FacesMessage msg = new FacesMessage("Not a valid answer it must be 4 char", uniqueColumn);
            context.addMessage(comp.getClientId(context), msg);
            throw new ValidatorException(msg);
		}
		
		else if(ans.length() >=4 )
		{
			boolean valid = true;
			for(int i = 0; i<ans.length(); i++)
			{
				if(!Character.isAlphabetic(ans.charAt(i)))
				{
					valid =false;
				}
			}
			if(!valid)
			{
				FacesMessage msg = new FacesMessage("Not a valid answer,it should be character..", uniqueColumn);
	            context.addMessage(comp.getClientId(context), msg);
	            throw new ValidatorException(msg);
			}
			
		}
	}

}
