package domain;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class ContactService
 */
@Stateless(mappedName="ServiceBean")
public class ContactService implements ContactServiceRemote {

	public String welcome() {
		return "OKKKKK";
	}

}
