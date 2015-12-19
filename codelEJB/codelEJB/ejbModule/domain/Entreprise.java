package domain;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("E")
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
	
	@Column(name="Siret")
	public long getNumSiret()
	{
		return numSiret;
	}

	public void setNumSiret(long numSiret)
	{
		this.numSiret = numSiret;
	}
	
}
