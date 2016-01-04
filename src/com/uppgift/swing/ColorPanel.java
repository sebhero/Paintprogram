package com.uppgift.swing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.uppgift.observerPattern.ColorGrabber;
import com.uppgift.observerPattern.Observer;

/**
 * A custom panel class for color handling. the panel has a color and when
 * clicked on sends through the observer its color.
 * 
 * @author seb
 * 
 */
public class ColorPanel extends JPanel implements MouseListener, Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object observerID;
	private static int observerIDTracker = 0;
	private final ColorGrabber colorGrabber;

	public ColorPanel(Color theColor, ColorGrabber colorGrabber)
	{
		this.setOpaque(true);
		this.setBackground(theColor);
		addMouseListener(this);
		this.colorGrabber = colorGrabber;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// updates current color to this color
		this.colorGrabber.setCurrentColor(getBackground());

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	// register this as a observer
	public void registerObserver()
	{
		this.observerID = ++observerIDTracker;

		colorGrabber.register(this);
	}

	@Override
	public void update(Color updateColor)
	{
		// updates this background color. I use this for the selected
		// currentcolorPanel
		this.setBackground(updateColor);
	}

}
