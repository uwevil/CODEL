package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorColumn(name="DiscriminatorE", discriminatorType=DiscriminatorType.CHAR)
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
