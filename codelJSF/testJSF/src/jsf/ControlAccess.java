package jsf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("serial")
@ManagedBean(name="control")
@SessionScoped

public class ControlAccess implements Serializable{
	
	private String login = "Enter your login";
	private String password;
	
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
		if (isMissing(login) || isMissing(password)){
			return "missing-login-pass";
		}else if (login.equals(password)){
			return "welcome-page";
		}else{
			return "bad-login";
		}
	}

	private boolean isMissing(String value) {
		// TODO Auto-generated method stub
		return (value == null) || (value.trim().isEmpty());
	}
}
