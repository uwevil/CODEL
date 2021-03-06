package domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class ContactGroup implements Serializable
{

	protected long groupId;
	protected String groupName;
	
	protected Set<Contact> contacts = new HashSet<Contact>();
	
	private long version;

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
	
	public long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public Set<Contact> getContacts()
	{
		return contacts;
	}

	public void setContacts(Set<Contact> contacts)
	{
		this.contacts = contacts;
	}

	

}
