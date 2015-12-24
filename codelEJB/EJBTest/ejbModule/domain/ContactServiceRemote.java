package domain;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Remote
@WebService(name="ContactService", targetNamespace="http://localhost/serviceContact")
public interface ContactServiceRemote {
	
	@WebMethod(action="urn:welcome1", operationName="welcome1")
	public @WebResult(name="Result") String welcome();
	
	@WebMethod(action="urn:welcome2", operationName="welcome2")
	public @WebResult(name="Result") String welcome(@WebParam(name="Param1") String param1,
				@WebParam(name="Param2") String param2);

}
