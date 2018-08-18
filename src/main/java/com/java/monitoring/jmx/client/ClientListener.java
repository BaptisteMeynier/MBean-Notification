package com.java.monitoring.jmx.client;


import java.io.IOException;
import javax.management.AttributeChangeNotification;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.java.monitoring.jmx.mbean.StockMBean;

public class ClientListener implements NotificationListener {
private String port = "9000";
private String host = "localhost";
private JMXConnector connector;
private MBeanServerConnection mbsc;
private ObjectName name = null;

public void handleNotification(final Notification notification, final Object handback) {
 System.out.println("\nNotification recue:");
 System.out.println("\tClassName: " + notification.getClass().getName());
 System.out.println("\tSource: " + notification.getSource());
 System.out.println("\tType: " + notification.getType());
 System.out.println("\tMessage: " + notification.getMessage());

 if (notification instanceof AttributeChangeNotification) {
   final AttributeChangeNotification acn = (AttributeChangeNotification) notification;
   System.out.println("\tAttributeName: " + acn.getAttributeName());
   System.out.println("\tAttributeType: " + acn.getAttributeType());
   System.out.println("\tNewValue: " + acn.getNewValue());
   System.out.println("\tOldValue: " + acn.getOldValue());
 }
}

public void deconnecter() {
 try {
   System.out.println("Desabonner le NotificationListener");
   mbsc.removeNotificationListener(name, this);
 }
 catch (final Exception e) {
   e.printStackTrace();
 }
 try {
   System.out.println("Deconnexion du server JMX");
   connector.close();
 }
 catch (final IOException e) {
   e.printStackTrace();
 }
}
public void connecter() throws Exception {
 final JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" 
   + host + ":" + port + "/jmxrmi");
 name = new ObjectName("com.java.monitoring.jmx:type=StockMBean");
 
 System.out.println("Connexion au server JMX");
 connector = JMXConnectorFactory.connect(address, null);
 mbsc = connector.getMBeanServerConnection();
 
 System.out.println("Connection JMX etablie a la JVM " + host + " sur le port " + port);
 final StockMBean mbean = (StockMBean) MBeanServerInvocationHandler
   .newProxyInstance(mbsc, name, StockMBean.class, false);
 final int valeur = mbean.getValeur();
 
 System.out.println("Valeur courante du mbean = " + valeur);
 mbean.rafraichir();
 System.out.println("Abonnement du NotificationListener pour " + name.toString());
 mbsc.addNotificationListener(name, this, null, null);
}

public String getHost() {
 return host;
}

public void setHost(final String host) {
 this.host = host;
}

public String getPort() {
 return port;
}

public void setPort(final String port) {
 this.port = port;
}
}
