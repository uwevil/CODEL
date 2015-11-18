package jsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")

public class ControlAccessJSF implements Serializable{
	
	private String login;
	private String password;
	
	private static boolean ok = false;
	
	public static boolean getOK(){
		return ok;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String checkAccess(){
		FacesContext context = FacesContext.getCurrentInstance();
	
		if (isMissing(login) || isMissing(password)){
			context.addMessage(null, new FacesMessage("UserID or Password required"));
		}else if (!login.equals(password)){
			context.addMessage(null, new FacesMessage("Password is wrong"));
		}
		
		if (context.getMessageList().size() > 0){
			return null;
		}else{
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			session.setAttribute("authenticated", "OK");
			session.setMaxInactiveInterval(60);
			
			ok = true;
			
			return "accueilJSF";
		}
	}
	
	public String logout(){
		ok = false;
		return "login.html";
	}

	private boolean isMissing(String value) {
		// TODO Auto-generated method stub
		return (value == null) || (value.trim().isEmpty());
	}
	
}
