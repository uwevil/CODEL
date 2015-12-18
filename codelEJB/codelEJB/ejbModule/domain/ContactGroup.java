package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="ContactGroup")
public class ContactGroup implements Serializable
{

	protected long groupId;
	protected String groupName;
	
	protected Set<Contact> contacts = new HashSet<Contact>();
	
	private long version;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CONTACTGROUPID")
	public long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}

	@Column(name="VERSION")
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public ContactGroup()
	{
	}
	
	public ContactGroup(String groupName)
	{
		this.groupName = groupName;
	}
	
	@Column(name="GROUPNAME")
	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	@ManyToMany
	public Set<Contact> getContacts()
	{
		return contacts;
	}

	public void setContacts(Set<Contact> contacts)
	{
		this.contacts = contacts;
	}

	

}
