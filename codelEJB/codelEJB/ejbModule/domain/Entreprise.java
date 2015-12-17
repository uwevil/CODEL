package domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Entreprise extends Contact implements Serializable
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
