package domain;

import java.util.ArrayList;

public class ContactGroup
{

	protected int groupId;
	protected String groupName;
	
	protected ArrayList<Contact> listContacts;
	
	public ContactGroup()
	{
	}
	
	public ContactGroup(int groupId, String groupName)
	{
		this.groupId = groupId;
		this.groupName = groupName;
	}
	
	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
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

	public ArrayList<Contact> getContacts()
	{
		return listContacts;
	}

	public void setContacts(ArrayList<Contact> listContacts)
	{
		this.listContacts = listContacts;
	}

	

}
