package com.uppgift.utils;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.uppgift.observerPattern.ColorGrabber;
import com.uppgift.observerPattern.Observer;
import com.uppgift.swing.CanvasPanel;
import com.uppgift.swing.RitprogrammetMainFrame;

/**
 * Handles all the events. Holds also the main swing jframe
 * 
 * @author seb
 * 
 */
public class MyCustomListnerBus implements Observer
{

	private static int observerIDTracker = 0;
	private final int observerID;

	// select pensel in the combox listner
	private final ItemListener comboxListner;
	// my implemntation of observer pattern. everyone that listens to this get the
	// updated color.
	private final ColorGrabber colorGrabber;

	// mouse movement listner. for the canvas
	private final MouseMotionListener canvasMouseMovedListner;
	// cords label for displaying the mouse cordinats
	private JLabel cords;
	// click event listner for the canvas
	private final MouseListener canvasMouseClickListner;

	// the canvas
	private CanvasPanel canvas;

	// the jframe for updating paint. Had a bug that i got duplicated bars and
	// colors
	// if i didnt call update on the jframe.
	private RitprogrammetMainFrame theMain;
	private ItemListener myComboxListner;
	private JComboBox<String> comboxForms;

	/**
	 * Init all the values
	 */
	public MyCustomListnerBus()
	{
		colorGrabber = new ColorGrabber();

		this.observerID = ++observerIDTracker;

		colorGrabber.register(this);

		comboxListner = createComboxListner();

		canvasMouseMovedListner = createCanvasMouseMoveListner();

		canvasMouseClickListner = createCanvasMouseClickListner();

	}

	/**
	 * Creates the mouse click listner for canvas
	 * 
	 * @return mouselistner
	 */
	private MouseListener createCanvasMouseClickListner()
	{
		return new MouseListener()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{

				if (canvas.getCurrentPenselType() == Pensel.RECT)
				{

					// When the mouse is pressed get x & y position

					canvas.setDrawStart(new Point(e.getX(), e.getY()));
					canvas.setDrawEnd(canvas.getDrawStart());
					theMain.repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

				if (canvas.getCurrentPenselType() == Pensel.RECT)
				{

					final Shape theShape = canvas.drawRectangle(canvas.getDrawStart().x, canvas.getDrawStart().y, e.getX(),
							e.getY());
					// Add shapes, fills and colors to there ArrayLists

					canvas.addShape(new ArrayList<Shape>()
					{
						{
							add(theShape);
						}
					});
					canvas.addShapesColors(canvas.getCurrentColor());

					canvas.setDrawStart(null);
					canvas.setDrawEnd(null);

					// repaint everything.
					theMain.repaint();
				}

				if (canvas.getCurrentPenselType() == Pensel.FREEHAND)
				{
					System.out.println("Stop painting with freehand");
					canvas.doneWithBrushList();
					theMain.repaint();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
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

		};
	}

	/**
	 * Creates the combox listner
	 * 
	 * @return
	 */
	private ItemListener createComboxListner()
	{
		myComboxListner = new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				switch ((String) e.getItem())
				{
				default:
				case "frihand":
					canvas.setCurrentPenselType(Pensel.FREEHAND);
					break;
				case "rektangel":
					canvas.setCurrentPenselType(Pensel.RECT);
					break;

				}

			}
		};
		return myComboxListner;
	}

	/**
	 * creates mouse movement listner
	 * 
	 * @return
	 */
	private MouseMotionListener createCanvasMouseMoveListner()
	{
		return new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				cords.setText("Koordinater: " + e.getX() + ", " + e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				cords.setText("Koordinater: " + e.getX() + ", " + e.getY());

				if (canvas.getCurrentPenselType() == Pensel.FREEHAND)
				{

					final int x = e.getX();
					final int y = e.getY();

					Shape theShape = null;

					theShape = canvas.drawBrush(x, y, 5, 5);

					canvas.addShapeToWorkingOnBrushList(theShape);
					// canvas.addShape(theShape);
					canvas.addShapesColors(canvas.getCurrentColor());
				}

				// Get the final x & y position after the mouse is dragged

				canvas.setDrawEnd(new Point(e.getX(), e.getY()));
				theMain.repaint();

			}

		};
	}

	/**
	 * Shares the comboxlistner with the combox
	 * 
	 * @return
	 */
	public ItemListener getComboxListner()
	{
		return comboxListner;
	}

	/**
	 * a My observer implentation. so That the diffrent colors recivies the
	 * observer
	 * 
	 * @return
	 */
	public ColorGrabber getColorGrabber()
	{

		return colorGrabber;
	}

	@Override
	public void update(Color updateColor)
	{
		// update canvas current color
		canvas.setCurrentColor(updateColor);

	}

	/**
	 * returns the mouse motionlistner
	 * 
	 * @return
	 */
	public MouseMotionListener getMouseMovedListner()
	{
		return canvasMouseMovedListner;
	}

	/**
	 * adds the jlabel for cords to the listner
	 * 
	 * @param cords
	 */
	public void setCordsLabel(JLabel cords)
	{
		this.cords = cords;

	}

	/**
	 * REturn mouseclick listener for canvas.
	 * 
	 * @return
	 */
	public MouseListener getMouseCanvasListner()
	{
		return canvasMouseClickListner;
	}

	/**
	 * sets the canvas
	 * 
	 * @param theCanvasPanel
	 */
	public void addCanvas(CanvasPanel theCanvasPanel)
	{
		this.canvas = theCanvasPanel;
		// set start color
		colorGrabber.setCurrentColor(Color.BLACK);

	}

	/**
	 * adds the main for repaint()
	 * 
	 * @param ritprogrammetSwing
	 */
	public void addMain(RitprogrammetMainFrame ritprogrammetSwing)
	{
		theMain = ritprogrammetSwing;
	}

	public ActionListener getNewCanvasListner()
	{
		return new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("new canvas is called");
				// clear old canvas.
				// reset color and pensel.
				canvas.clearCanvas();
				// call my observer to set to default color which is black
				colorGrabber.setCurrentColor(Color.BLACK);
				comboxForms.setSelectedItem("frihand");
				// ville eg inte lägga till combox här utan bara anropa listnern men den
				// behöver en ItemSelectable source...
				// myComboxListner.itemStateChanged(new ItemEvent(comboxForms, 0,
				// "frihand", ItemEvent.ITEM_STATE_CHANGED));
				theMain.repaint();

			}
		};

	}

	/**
	 * Saves away the combox this is because when i clear the canvas i want to
	 * reset the combox. And i couldnt just use the listner cus it need a
	 * itemselectable source..
	 * 
	 * @param comboxForms
	 */
	public void setCombox(JComboBox<String> comboxForms)
	{
		this.comboxForms = comboxForms;

	}

	/**
	 * Undoes last action
	 * 
	 * @return
	 */
	public ActionListener getUndoCanvasListner()
	{
		return new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("undo is called");
				canvas.undoLastDraw();
				theMain.repaint();
			}
		};
	}

	/**
	 * Calls exit on quit menu button pressed
	 * 
	 * @return
	 */
	public ActionListener getQuitListner()
	{
		return new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);

			}
		};
	}
}
