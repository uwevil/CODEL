package domain;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * Session Bean implementation class ContactService
 */
@Stateless(mappedName="ServiceBean")
@WebService(targetNamespace="http://localhost/serviceContact", 
	endpointInterface="domain.ContactServiceRemote")	
public class ContactService implements ContactServiceRemote {

	public String welcome() {
		return "OKKKKK";
	}

	public String welcome(String param1, String param2) {
		return "Param1 = " + param1 + "\nParam2 = " + param2;
	}

}
