package com.uppgift.observerPattern.test;

import java.awt.Color;

import com.uppgift.observerPattern.Observer;
import com.uppgift.observerPattern.Subject;

/**
 * Test class for testing the observer pattern
 * 
 * @author seb
 * 
 */
public class ColorObserver implements Observer
{

	private Color currentColor;

	private static int observerIDTracker = 0;

	private final int observerID;

	private final Subject colorGraber;

	public ColorObserver(Subject colorGrabber)
	{
		this.colorGraber = colorGrabber;

		this.observerID = ++observerIDTracker;

		System.out.println("New Observer " + this.observerID);

		colorGrabber.register(this);
	}

	@Override
	public void update(Color updateColor)
	{
		this.currentColor = updateColor;

		printTheColor();

	}

	private void printTheColor()
	{

		System.out.println(observerID + "\nCurrent Color: " + this.currentColor + "\n");
	}

}
