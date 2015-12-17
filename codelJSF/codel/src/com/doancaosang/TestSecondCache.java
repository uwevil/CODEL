package com.doancaosang;

import java.util.List;

import org.hibernate.Session;

import domain.Contact;
import util.HibernateUtil;

public class TestSecondCache {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//session1 envoie un requete SQL vers le base des donnees
		Session session1 = HibernateUtil.getSessionFactory().openSession();		
		session1.beginTransaction();	
		@SuppressWarnings("unchecked")
		List<Contact> list = session1.createQuery("from Contact").list();
		session1.getTransaction().commit();
		
		System.out.println("-------------------------------------------------");
		//session2 n'envoie pas de requete vers le base des donnees car il recupere les donnees depuis le cache secondaire
		//donc on ne peut pas voir la sortie de requete SQL apres "------"
		Session session2 = HibernateUtil.getSessionFactory().openSession();		
		session2.beginTransaction();
		Contact c = (Contact)session2.load(Contact.class, 1L);
		System.out.println(c.getFirstName()+" "+c.getLastName());
		session2.getTransaction().commit();
	}

}
