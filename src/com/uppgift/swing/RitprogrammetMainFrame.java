package com.uppgift.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.uppgift.utils.MyCustomListnerBus;

public class RitprogrammetMainFrame extends JFrame
{

	private final MyCustomListnerBus myBus;

	public RitprogrammetMainFrame()
	{
		// frame
		getContentPane().setLayout(new BorderLayout());
		this.setSize(512, 422);

		this.setLocationRelativeTo(null);

		this.setTitle("Ritprogram");

		// set close button to exit
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// my custom listner bus. events
		myBus = new MyCustomListnerBus();

		final Dimension MainminDim = new Dimension(280, 200);
		this.setMinimumSize(MainminDim);
		final GridLayout topLayout = new GridLayout(1, 2);
		final JPanel theTopPanel = new JPanel(topLayout);
		getContentPane().add(theTopPanel, BorderLayout.NORTH);
		theTopPanel.setBorder(null);
		theTopPanel.setLayout(new GridLayout(0, 2, 0, 0));

		// panels
		final GridLayout gl_theColorsPanel = new GridLayout(1, 6);
		final JPanel theColorsPanel = new JPanel(gl_theColorsPanel);
		theTopPanel.add(theColorsPanel);

		// creat color panels

		// get the color

		final ColorPanel panelGreen = new ColorPanel(Color.GREEN, myBus.getColorGrabber());

		final ColorPanel panelBlue = new ColorPanel(Color.BLUE, myBus.getColorGrabber());
		final ColorPanel panelBlack = new ColorPanel(Color.BLACK, myBus.getColorGrabber());
		final ColorPanel panelRED = new ColorPanel(Color.RED, myBus.getColorGrabber());
		final ColorPanel panelYELLOW = new ColorPanel(Color.YELLOW, myBus.getColorGrabber());

		// add to colorpanel
		theColorsPanel.add(panelGreen);
		theColorsPanel.add(panelBlue);
		theColorsPanel.add(panelBlack);
		theColorsPanel.add(panelRED);
		theColorsPanel.add(panelYELLOW);

		final JComboBox<String> comboxForms = new JComboBox<String>();
		theTopPanel.add(comboxForms);
		comboxForms.setModel(new DefaultComboBoxModel<String>(new String[]
		{ "frihand", "rektangel" }));

		myBus.setCombox(comboxForms);

		// listners
		comboxForms.addItemListener(myBus.getComboxListner());

		// end of top header.
		final JPanel theBottomPanel = new JPanel();
		final FlowLayout fl_theBottomLeftPanel = new FlowLayout(FlowLayout.LEFT);
		final FlowLayout fl_theBottomRightPanel = new FlowLayout(FlowLayout.RIGHT);

		final JPanel theBottomLeftPanel = new JPanel(fl_theBottomLeftPanel);

		theBottomPanel.setLayout(new GridLayout(1, 3, 0, 0));
		fl_theBottomLeftPanel.setHgap(0);
		fl_theBottomRightPanel.setHgap(2);
		final JPanel theBottomRightPanel = new JPanel(fl_theBottomRightPanel);
		final JLabel theCurrentColorLabel = new JLabel("F\u00E4rgval: f\u00E4rgen");// färgval
																																								// färgen
		// add label to right
		theBottomRightPanel.add(theCurrentColorLabel);

		final JLabel cords = new JLabel("Koordinater: " + this.getX() + ", " + this.getY());
		myBus.setCordsLabel(cords);

		// add to left
		theBottomLeftPanel.add(cords);

		// add panels to bottom,footer
		theBottomPanel.add(theBottomLeftPanel);
		theBottomPanel.add(theBottomRightPanel);

		// curent selected color
		final ColorPanel theCurrentColorPanel = new ColorPanel(Color.BLACK, myBus.getColorGrabber());
		// custom event handling using the observer pattern
		theCurrentColorPanel.registerObserver();

		// canvas, paint area
		final CanvasPanel theCanvasPanel = new CanvasPanel();
		final FlowLayout flowLayout = (FlowLayout) theCanvasPanel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		theCanvasPanel.setBackground(Color.WHITE);
		getContentPane().add(theCanvasPanel, BorderLayout.CENTER);

		// add mouse motion listner
		theCanvasPanel.addMouseMotionListener(myBus.getMouseMovedListner());
		// add mouse down and up listner
		theCanvasPanel.addMouseListener(myBus.getMouseCanvasListner());

		myBus.addCanvas(theCanvasPanel);
		// only because i need to do repaint on everything.
		myBus.addMain(this);

		// end canvas

		final FlowLayout fl_currentColorPanel = (FlowLayout) theCurrentColorPanel.getLayout();
		fl_currentColorPanel.setVgap(8);
		fl_currentColorPanel.setHgap(20);
		theCurrentColorPanel.setBorder(null);
		theBottomRightPanel.add(theCurrentColorPanel);

		// end of footer

		getContentPane().add(theBottomPanel, BorderLayout.PAGE_END);

		createMenuBar();

	}

	/**
	 * Create a menubar
	 */
	private void createMenuBar()
	{
		final JMenuBar menuBar = new JMenuBar();
		// getContentPane().add(menuBar, BorderLayout.WEST);
		this.setJMenuBar(menuBar);

		final JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mnFile.setMnemonic(KeyEvent.VK_F);

		final JMenuItem mntmnew = new JMenuItem("New");
		mnFile.add(mntmnew);
		mntmnew.setMnemonic(KeyEvent.VK_N);
		mntmnew.addActionListener(myBus.getNewCanvasListner());

		final JMenuItem mntmundo = new JMenuItem("Undo");
		mnFile.add(mntmundo);
		mntmundo.setMnemonic(KeyEvent.VK_U);
		mntmundo.addActionListener(myBus.getUndoCanvasListner());

		mnFile.addSeparator();
		final JMenuItem mntmquit = new JMenuItem("Quit");
		mnFile.add(mntmquit);
		mntmquit.setMnemonic(KeyEvent.VK_Q);
		mntmquit.addActionListener(myBus.getQuitListner());
	}

	public void run()
	{
		this.setVisible(true);
	}
}
