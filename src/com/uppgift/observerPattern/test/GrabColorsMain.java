package com.uppgift.observerPattern.test;

import java.awt.Color;

import com.uppgift.observerPattern.ColorGrabber;

/**
 * test main of the observer pattern
 * 
 * @author seb
 * 
 */
public class GrabColorsMain
{
	public static void main(String[] args)
	{
		// get the color
		final ColorGrabber colorGrabber = new ColorGrabber();

		final ColorObserver observer1 = new ColorObserver(colorGrabber);
		final ColorObserver observer2 = new ColorObserver(colorGrabber);

		colorGrabber.setCurrentColor(Color.BLACK);
		colorGrabber.setCurrentColor(Color.BLUE);
		colorGrabber.setCurrentColor(Color.RED);

	}

}
