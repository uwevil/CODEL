package jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.*;

public class EmailValidator implements Validator {
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String email = (String) arg2;
		if (email != null 
				&& email.length() > 0
				&& !email.matches("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[_a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)+$")){
			FacesMessage msg = new FacesMessage("E-mail validation failed.", 
							"Invalid E-mail format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);		
		}
	}

}
