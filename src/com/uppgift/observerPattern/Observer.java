package com.uppgift.observerPattern;

import java.awt.Color;

/**
 * Used for observering color changes
 * 
 * @author seb
 * 
 */
public interface Observer
{

	/**
	 * Update the current color when this is called all observers update with the
	 * updateColor
	 * 
	 * @param updateColor
	 */
	public void update(Color updateColor);

}
