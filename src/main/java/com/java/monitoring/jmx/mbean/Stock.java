package com.java.monitoring.jmx.mbean;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Stock extends NotificationBroadcasterSupport implements StockMBean {

	  private static String nom = "StockMBean";
	  private int valeur = 100;
	  
	  private static long   numeroSequence = 0l;
	  
	  public String getNom() {
	    return nom;
	  }

	  public int getValeur() {
	    return valeur;
	  }

	  public synchronized void setValeur(int valeur) {
		    numeroSequence++;
		    Notification notif = new AttributeChangeNotification(this,
		        numeroSequence, System.currentTimeMillis(),
		        "Modification de la valeur", "Valeur", "int", this.valeur, valeur);

		    this.valeur = valeur;

		    sendNotification(notif);
		  }

		  public void rafraichir() {
		    System.out.println("Rafraichir les donnees");
		  }

		  @Override 
		  public MBeanNotificationInfo[] getNotificationInfo() { 
		      String[] types = new String[] { 
		          AttributeChangeNotification.ATTRIBUTE_CHANGE 
		      }; 
		      String name = AttributeChangeNotification.class.getName(); 
		      String description = "Un attribut du MBean a ete modifie"; 
		      MBeanNotificationInfo info = 
		          new MBeanNotificationInfo(types, name, description); 
		      return new MBeanNotificationInfo[] {info}; 
		  } 
	}
