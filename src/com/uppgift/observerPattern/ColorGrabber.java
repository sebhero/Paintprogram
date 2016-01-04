package com.uppgift.observerPattern;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Subject holds all the registered observers and notifies all the observers
 * with the new current color.
 * 
 * @author seb
 * 
 */
public class ColorGrabber implements Subject
{
	private final ArrayList<Observer> observers;
	private Color currentColor;

	public ColorGrabber()
	{
		observers = new ArrayList<Observer>();
	}

	@Override
	public void register(Observer o)
	{
		observers.add(o);

	}

	@Override
	public void unregister(Observer o)
	{
		final int observerIndex = observers.indexOf(o);
		System.out.println("remove : " + observerIndex + 1);
		observers.remove(observerIndex);

	}

	@Override
	public void notifyObserver()
	{

		for (final Observer observer : observers)
		{
			observer.update(currentColor);
		}
	}

	public void setCurrentColor(Color newColor)
	{
		this.currentColor = newColor;
		notifyObserver();
	}

}
