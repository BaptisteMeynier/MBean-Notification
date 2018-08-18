package com.java.monitoring.jmx.client;

/**
 * 
 * @author baptiste
 *
 */
public class LanceClient {
	public static void main(final String[] args) {
		ClientListener listener = null;
		try {
			listener = new ClientListener();
			listener.connecter();
			while (true) {
				;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (listener != null) {
				listener.deconnecter();
			}
		}
	}
}
