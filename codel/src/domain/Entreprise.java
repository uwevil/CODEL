package domain;

public class Entreprise extends Contact
{
	
	protected long numSiret;
	
	public Entreprise()
	{
		super();
	}
	
	public Entreprise(long numSiret)
	{
		super();
		this.numSiret = numSiret;
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
