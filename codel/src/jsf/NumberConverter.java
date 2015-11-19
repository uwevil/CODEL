package jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class NumberConverter implements Converter {

	public NumberConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		String number = (String) arg2;
		if (number != null 
				&& number.length() > 0
				&& !number.matches("^[0-9.+ ]*$")){
			FacesMessage msg = new FacesMessage("Number validation failed.", 
							"Invalid Number format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);		
		}
		
		return number;
	}

}
