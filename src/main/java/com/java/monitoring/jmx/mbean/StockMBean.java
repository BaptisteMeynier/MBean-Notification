package com.java.monitoring.jmx.mbean;

import java.io.IOException;

/**
 * Il est important de que les methodes du Mbean jettent une IOException uniquement dans le cas ou votre client JMX est en remote !
 * En local c est inutile
 * @author baptiste
 *
 */
public interface StockMBean {
  
  public String getNom()  throws IOException; 

  public int getValeur()  throws IOException; 
  public void setValeur(int valeur)  throws IOException; 

  public void rafraichir()  throws IOException;

}

