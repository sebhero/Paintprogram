package com.uppgift.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import com.uppgift.utils.Pensel;

/**
 * The panel that is the drawing area. handles all the painting. Holds all the
 * painting
 * 
 * @author seb
 * 
 */
public class CanvasPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	// all drawn shapes
	ArrayList<ArrayList<Shape>> shapes = new ArrayList<ArrayList<Shape>>();
	// all colors used for drawing.
	ArrayList<Color> shapesColors = new ArrayList<Color>();
	// start and end point for the drawings.
	Point drawStart, drawEnd;
	// current type of pensel
	private Pensel currentPenselType = Pensel.FREEHAND;
	// current color
	private Color currentColor;
	private ArrayList<Shape> tempBrushList;

	public Point getDrawStart()
	{
		return drawStart;
	}

	public void setDrawStart(Point drawStart)
	{
		this.drawStart = drawStart;
	}

	public Point getDrawEnd()
	{
		return drawEnd;
	}

	public void setDrawEnd(Point drawEnd)
	{
		this.drawEnd = drawEnd;
	}

	/**
	 * Creates a rectangle
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return a 2d rectangel
	 */
	public Rectangle2D.Float drawRectangle(int x1, int y1, int x2, int y2)
	{
		// Get the top left hand corner for the shape
		// Math.min returns the points closest to 0

		final int x = Math.min(x1, x2);
		final int y = Math.min(y1, y2);

		// Gets the difference between the coordinates and

		final int width = Math.abs(x1 - x2);
		final int height = Math.abs(y1 - y2);

		return new Rectangle2D.Float(x, y, width, height);
	}

	/**
	 * Adds the newly created shape list to my shapes list
	 * 
	 * @param aShape
	 */

	public void addShape(ArrayList<Shape> aShape)
	{
		this.shapes.add(aShape);

	}

	public void addShapesColors(Color shapeFillColor)
	{

		this.shapesColors.add(shapeFillColor);
	}

	@Override
	public void paint(Graphics g)
	{
		// Class used to define the shapes to be drawn

		final Graphics2D graphSettings = (Graphics2D) g;

		// Antialiasing cleans up the jagged lines and defines rendering rules

		graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Defines the line width of the stroke

		graphSettings.setStroke(new BasicStroke(4));

		// Iterators created to cycle through strokes and fills
		final Iterator<Color> ColorsOfTheshape = shapesColors.iterator();

		if (tempBrushList != null)
		{
			for (final Shape sh : tempBrushList)
			{
				graphSettings.setPaint(currentColor);
				graphSettings.draw(sh);
				graphSettings.fill(sh);
			}
		}

		for (final ArrayList<Shape> sArr : shapes)
		{
			for (final Shape s : sArr)
			{

				// Grabs the next fill from the color arraylist
				graphSettings.setPaint(ColorsOfTheshape.next());

				graphSettings.draw(s);
				graphSettings.fill(s);

			}
		}

		// Guide shape used for drawing
		if (drawStart != null && drawEnd != null)
		{
			Shape myShape = null;

			if (currentPenselType == Pensel.RECT)
			{

				myShape = drawRectangle(drawStart.x, drawStart.y, drawEnd.x, drawEnd.y);
			}

			graphSettings.draw(myShape);
		}
	}

	public Ellipse2D.Float drawBrush(int x1, int y1, int brushStrokeWidth, int brushStrokeHeight)
	{

		return new Ellipse2D.Float(x1, y1, brushStrokeWidth, brushStrokeHeight);

	}

	/**
	 * @return the currentPenselType
	 */
	public Pensel getCurrentPenselType()
	{
		return currentPenselType;
	}

	/**
	 * @param currentPenselType
	 *          the currentPenselType to set
	 */
	public void setCurrentPenselType(Pensel currentPenselType)
	{
		this.currentPenselType = currentPenselType;
	}

	public void setCurrentColor(Color updateColor)
	{
		this.currentColor = updateColor;

	}

	/**
	 * returns the current color
	 * 
	 * @return
	 */
	public Color getCurrentColor()
	{
		return currentColor;
	}

	// clear the canvas and reset it
	public void clearCanvas()
	{

		this.drawEnd = null;
		this.drawStart = null;
		this.shapes.clear();
		this.shapesColors.clear();
		if (tempBrushList != null)
		{
			this.tempBrushList.clear();
		}
	}

	/**
	 * Undo last stroke on canvas
	 */
	public void undoLastDraw()
	{
		// if there is anything drawn
		if (shapes.size() > 0)
		{
			System.out.println("shapes size: " + shapes.size());
			this.shapes.remove(shapes.size() - 1);
			this.shapesColors.remove(shapesColors.size() - 1);
			this.drawEnd = null;
			this.drawStart = null;

			System.out.println("shapes size: " + shapes.size());

		}
		else
		{
			System.out.println("size is smaller than 0");
			System.out.println("shapes size: " + shapes.size());
		}
	}

	/**
	 * adds freestyle shapes to list im currently working with. When done need to
	 * call doneWithBrushList()
	 * 
	 * @param theShape
	 */
	public void addShapeToWorkingOnBrushList(Shape theShape)
	{
		if (tempBrushList == null)
		{
			tempBrushList = new ArrayList<Shape>();
		}
		this.tempBrushList.add(theShape);

	}

	/**
	 * Call when dones with freestyle drawings.
	 */
	public void doneWithBrushList()
	{

		System.out.println("add bustlist to shapes");
		this.shapes.add(this.tempBrushList);
		System.out.println("empty list for temp brush");
		// empty the temp brush list
		// this.currentBrushList.clear();
		tempBrushList = null;

	}
}
