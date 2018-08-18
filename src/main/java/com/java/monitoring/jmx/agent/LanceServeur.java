package com.java.monitoring.jmx.agent;

import java.lang.management.ManagementFactory;  
import javax.management.MBeanServer;  
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import com.java.monitoring.jmx.mbean.Stock;  

/**
 * Before to launch
 * rmiregistry 9000
 * @author baptiste
 *
 */
public class LanceServeur {  
  public static void main(final String[] args) {  
    System.out.println("Lancement de l'agent JMX");  
    final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();  
    ObjectName name = null;  

    try {  
      System.out.println("Instanciation et enregistrement du Mbean");  
      name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");  
      final Stock mbean = new Stock();  
      mbs.registerMBean(mbean, name);  
       
      // Creation et demarrage du connecteur RMI
      JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9000/jmxrmi");
      JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
      cs.start();
      
      System.out.println("Incrementation de la valeur du MBean ...");  
      int i = 0; 
      while (i < 6000) {  
        System.out.println("valeur = " + (mbean.getValeur() + 1));  
        mbean.setValeur(mbean.getValeur() + 1);  
        Thread.sleep(1000);  
        i++;  
      }  
      System.out.println("Arret de l'agent JMX");  
    } catch (final Exception e) {  
      e.printStackTrace();  
    }  
  }  
}
