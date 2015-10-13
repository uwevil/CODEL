package domain;

public class Entreprise extends Contact
{
	
	protected long numSiret;
	
	public Entreprise()
	{
		super();
	}
	
	public long getNumSiret()
	{
		return numSiret;
	}

	public void setNumSiret(long numSiret)
	{
		this.numSiret = numSiret;
	}
	
}
