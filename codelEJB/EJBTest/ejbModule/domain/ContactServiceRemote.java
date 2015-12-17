package domain;

import javax.ejb.Remote;

@Remote
public interface ContactServiceRemote {
	public String welcome();
}
