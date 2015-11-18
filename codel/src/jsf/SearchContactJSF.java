package jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Contact;
import domain.ContactGroup;

@SuppressWarnings("serial")
public class SearchContactJSF implements Serializable {

	private String firstName;
	private String lastName;
	private String numSiret;
	private String email;
	private String home;
	private String fax;

	private String mobile;
	private String street;
	private String zip;
	private String city;
	private String country;
	
	private List<String> groups = new ArrayList<>();
	private List<String> newGroups = new ArrayList<>();
	
	private List<Object> result = new ArrayList<>();
	
	private Object contact;
	
	private ServiceJSF service;
	
	public ServiceJSF getService() {
		return service;
	}

	public void setService(ServiceJSF service) {
		this.service = service;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNumSiret() {
		return numSiret;
	}

	public void setNumSiret(String numSiret) {
		this.numSiret = numSiret;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Object getContact() {
		return contact;
	}

	public void setContact(Object contact) {
		this.contact = contact;
	}
	
	public SearchContactJSF() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getNewGroups() {
		return newGroups;
	}

	public void setNewGroups(List<String> newGroups) {
		this.newGroups = newGroups;
	}

	public String init(){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		List<Object> list = service.groups();
		groups = new ArrayList<>();
		
		if (list != null && !list.isEmpty()){
			for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();){
				ContactGroup g = (ContactGroup) iterator.next();
				groups.add(g.getGroupName());
			}
		}
		return "searchContactJSF";
	}

	public String search(){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		
		result = new ArrayList<>();
		
		boolean ok = true;
		
		if (firstName != null && lastName != null && firstName.length() > 0 && lastName.length() > 0){
			result.add(service.searchContactName(firstName, lastName));
			ok = false;
		}else{
			if (firstName != null && firstName.length() >0){
				result = service.searchFirstName(firstName);
				ok = false;
			}
			
			if (lastName != null && lastName.length() >0){
				result = service.searchLastName(lastName);
				ok = false;
			}
		}
				
		if ((result == null || result.isEmpty()) && !ok)
			return "searchContactResultJSF";
		
		ok = true;
		
		for (int i = 0; i < result.size(); i++){
			Contact c = (Contact) result.get(i);
			System.out.println(c.getFirstName() + " " + c.getLastName());
		}

		if (ok)
		{
			List<Object> list = service.searchEmail(email);
			List<Object> list_result = result;
			
			result = new ArrayList<Object>();
			
			if (list == null || list.isEmpty())
				return "searchContactResultJSF";
			
			if (list_result.isEmpty()){
				result = list;
			}else{
				ok = false;

				if (list.size() < list_result.size()){
					List<Object> tmp = list_result;
					list_result = list;
					list = tmp;
				}
				
				for (int i = 0; i < list_result.size(); i++){
					Contact c = (Contact) list_result.get(i);
									
					for (int j = 0; j < list.size(); j++){
						Contact c_tmp = (Contact) list.get(j);
						
						if (c.getEmail().equalsIgnoreCase(c_tmp.getEmail()) 
								&& (c.getFirstName().equalsIgnoreCase(c_tmp.getFirstName())
								&& c.getLastName().equalsIgnoreCase(c_tmp.getLastName()))){
							result.add(c_tmp);
						}
					}
				}
			}
		}
		
		if ((result == null || result.isEmpty()) && !ok)
			return "searchContactResultJSF";
		
		ok = true;
		
		if (ok){			
			if (!((street == null || street.length() < 1)
					&&(zip == null || zip.length() < 1)
					&&(city == null || city.length() < 1)
					&&(country == null || country.length() < 1))){
				ok = false;

				List<Object> list = service.searchAddress(street, zip, city, country);
				List<Object> list_result = result;
				
				result = new ArrayList<Object>();
				
				if (list == null || list.isEmpty())
					return "searchContactResultJSF";
				
				if (list.size() < list_result.size()){
					List<Object> tmp = list_result;
					list_result = list;
					list = tmp;
				}
				
				for (int i = 0; i < list_result.size(); i++){
					Contact c = (Contact) list_result.get(i);
									
					for (int j = 0; j < list.size(); j++){
						Contact c_tmp = (Contact) list.get(j);
						
						if (c.getEmail().equalsIgnoreCase(c_tmp.getEmail()) 
								&& (c.getFirstName().equalsIgnoreCase(c_tmp.getFirstName())
								&& c.getLastName().equalsIgnoreCase(c_tmp.getLastName()))){
							result.add(c_tmp);
						}
					}
				}
			}
		}
		
		if ((result == null || result.isEmpty()) && !ok)
			return "searchContactResultJSF";
		
		ok = true;
		
		for (int i = 0; i < result.size(); i++){
			Contact c = (Contact) result.get(i);
			System.out.println(c.getFirstName() + " " + c.getLastName());
		}
		

		return null;
	}
	
	public String reset(){
		if (!ControlAccessJSF.getOK())
			return "loginJSF.jsf";
		return null;
	}
}
