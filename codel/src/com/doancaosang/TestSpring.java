package com.doancaosang;

import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.Contact;
import domain.ContactGroup;
import domain.DAOContact;
import domain.Entreprise;
import domain.PhoneNumber;

public class TestSpring {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(
						"applicationContextTest.xml");
		
		DAOContact daoContact = (DAOContact) context.getBean("springDAOContactID");
		
		for (int i = 0; i < 2; i++){
			String name = "springContactID" + i;
			System.out.print(name + " ");
			Contact c = (Contact) context.getBean(name);
			
			daoContact.addContact(c);
			
			System.out.println(c);
			System.out.println("First name : " + c.getFirstName());
			System.out.println("Last name : " + c.getLastName());
			System.out.println("Email : " + c.getEmail());
			
			for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
				PhoneNumber p = iterator.next();
				System.out.println(p.getPhoneKind() + " : " + p.getPhoneNumber());
			}
			
			System.out.println("Street : " + c.getAddress().getStreet());
			System.out.println("Zip : " + c.getAddress().getZip());
			System.out.println("City : " + c.getAddress().getCity());
			System.out.println("Country : " + c.getAddress().getCountry());
			System.out.println("Group : ");
			for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
				ContactGroup g = iterator.next();
				System.out.println("  " + g.getGroupName() + " " + g);
				
				for (Iterator<Contact> iterator2 = g.getContacts().iterator(); iterator2.hasNext();){
					Contact c1 = iterator2.next();
					System.out.println("   Contact:     " + c1);
				}
			}
			System.out.println();
		}
		
		String name = "springEntrepriseID";
		System.out.print(name + " ");
		Entreprise c = (Entreprise) context.getBean(name);
		
		daoContact.addContact(c);
		
		System.out.println(c);
		System.out.println("First name : " + c.getFirstName());
		System.out.println("Last name : " + c.getLastName());
		System.out.println("SIRET : " + c.getNumSiret());
		System.out.println("Email : " + c.getEmail());
		
		for (Iterator<PhoneNumber> iterator = c.getPhoneNumbers().iterator(); iterator.hasNext();){
			PhoneNumber p = iterator.next();
			System.out.println(p.getPhoneKind() + " : " + p.getPhoneNumber());
		}
		
		System.out.println("Street : " + c.getAddress().getStreet());
		System.out.println("Zip : " + c.getAddress().getZip());
		System.out.println("City : " + c.getAddress().getCity());
		System.out.println("Country : " + c.getAddress().getCountry());
		System.out.println("Group : ");
		for (Iterator<ContactGroup> iterator = c.getBooks().iterator(); iterator.hasNext();){
			ContactGroup g = iterator.next();
			System.out.println("  " + g.getGroupName() + " " + g);
			
			for (Iterator<Contact> iterator2 = g.getContacts().iterator(); iterator2.hasNext();){
				Contact c1 = iterator2.next();
				System.out.println("   Contact:     " + c1);
			}
		}
		System.out.println();
	}
	
}
