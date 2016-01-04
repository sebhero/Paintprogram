package com.uppgift.observerPattern;

/**
 * controller for the observer interface Registers all the observesr and
 * notifies them if update is needed
 * 
 * @author seb
 * 
 */
public interface Subject
{

	/**
	 * Register new observer/listner
	 * 
	 * @param o
	 *          object that implements the observer interface
	 */
	public void register(Observer o);

	/**
	 * Remove observer/listner from the notify list
	 * 
	 * @param o
	 */
	public void unregister(Observer o);

	/**
	 * Notify all listners about the update
	 */
	public void notifyObserver();
}
