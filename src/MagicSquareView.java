import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Formatter;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.prism.Graphics;


/**
 * Class that controls the JApplet that
 * Initialize the view 
 * 
 * @author Diego Orozco & Cristian Rodriguez
 *
 */
public class MagicSquareView extends JApplet implements ActionListener 
{
	public MagicSquareView() {
	}
	// A reference to a matrix with the JButtons
	private JButton gameMatrix[][] = null;

	// A reference to a ComboBox, that contains the levels 
	private JComboBox sizesComboBox = null;

	private JComboBox unknownComboBox = null;

	// An array with the levels of the game
	private String gameLevels[]= {"-","3","4","5","6", "7", "8"};

	private String unknowLevels[]= {"-","Yes", "No"};

	private boolean unknow = false;

	// A JPanel that will be located at the center
	private JPanel centerPosition = null;

	//
	private JLabel picLabel  = null;

	// The Jbutton, to star the game
	private JButton startButton = null;

	//
	private JButton summit = null;

	// Contents the magic constant number
	private JLabel magicConstat = null;

	//
	private JLabel magicTime = null;

	//
	private JLabel bestMagicTime = null;

	// The main lower panel
	private JPanel lowerPanel = null;

	//
	private JPanel indicatorPanel = null;


	//
	MagicSquereModel magicSquareModel = null;

	//
	private Timer elapsedTime = null;

	//
	private long elapsedSeconds = 0;

	//
	private int level = 1;

	//
	private int dimension = 0;

	//
	private boolean valuesSelect = false;

	//
	private Formatter textFormatter = null;

	/**
	 * This methods calls the JApplet to run 
	 * 
	 */
	public void init()
	{
		getParent().setSize(1350, 780);
		this.getParent().getParent().setLocation(300, 50);

		createView();

		gameStarted();
	}


	/**
	 * 
	 */
	public void createViewWelcome()
	{
		BufferedImage myPicture = null;
		try 
		{
			myPicture = ImageIO.read(new File("menu.png"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setSize(centerPosition.getWidth(), centerPosition.getHeight());
		centerPosition.add(picLabel, BorderLayout.CENTER);;
		repaint();
	}

	/**
	 * This methods makes the view of the game
	 * call other methods in order to make his 
	 * center with the buttons, and the lower panel
	 * with some additional information
	 */
	public void createView()
	{

		// Makes the center JPanel
		centerPosition = new JPanel();

		// Esthetic change
		changeButtonsSkin();

		// 
		createLowerPanel();

		//
		createViewWelcome();

		//
		createIndicatorPanel();

		//
		sizesComboBoxButtons();

		//
		unknownBoxButtons();

		//
		summitButtons();

		//
		addComponents();
	}

	/**
	 * 
	 */
	public void addComponents()
	{
		// Adds the created elements to the entire view
		getContentPane().add(lowerPanel ,BorderLayout.NORTH);
		getContentPane().add(indicatorPanel ,BorderLayout.SOUTH);
		getContentPane().add(centerPosition,BorderLayout.CENTER);
	}

	/**
	 * Creates the necessary elements of the 
	 * lower panel, it includes the comboBox with
	 * the levels and the square for the magic 
	 * constant
	 */
	public void createLowerPanel()
	{
		// Makes a JPanel
		lowerPanel = new JPanel();

		//
		createLevelBox();
		JLabel label = new JLabel("Size: ");
		label.setForeground(new Color(255, 140, 0));
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lowerPanel.add(label);
		lowerPanel.add(sizesComboBox);

		//
		createunknownBox();

		//
		startButton = new JButton("Create");
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		startButton.setForeground(new Color(0, 128, 128));
		startButton.setBackground(new Color(0, 139, 139));
		startButton.setIcon(new ImageIcon(MagicSquareView.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		lowerPanel.add(startButton);
		JLabel lblUnknown = new JLabel("Enable Unknowns: ");
		lblUnknown.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnknown.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnknown.setForeground(new Color(255, 127, 80));
		lowerPanel.add(lblUnknown);
		lowerPanel.add(unknownComboBox);


		createBestsTime();

		lowerPanel.add(bestMagicTime, BorderLayout.EAST);

	}

	/**
	 * 
	 */
	public void createIndicatorPanel()
	{
		// Makes a JPanel
		indicatorPanel = new JPanel();

		//
		indicatorPanel.setLayout( new BorderLayout() );

		//
		createMagicConstantSquare();
		indicatorPanel.add(magicConstat, BorderLayout.WEST);

		//
		createElapsedTime();
		indicatorPanel.add(magicTime, BorderLayout.EAST);

		//
		summit = new JButton("Summit");
		summit.setForeground(new Color(255, 0, 51));
		summit.setFont(new Font("Nirmala UI Semilight", Font.BOLD, 16));
		summit.setBackground(new Color(255, 0, 51));
		indicatorPanel.add(summit);
		summit.setVisible(false);
	}

	/**
	 * Makes a combo box, that displays the levels 
	 * that the user wanna choose 
	 */
	public void createLevelBox()
	{
		sizesComboBox= new JComboBox<Object>(gameLevels);
		sizesComboBox.setForeground(new Color(255, 140, 0));
	}

	/**
	 * 
	 */
	public void createunknownBox()
	{	
		unknownComboBox = new JComboBox<Object>(unknowLevels);
		unknownComboBox.setForeground(new Color(255, 140, 0));
		unknownComboBox.setBackground(new Color(255, 140, 0));
	}


	/**
	 * Creates the Label for the magic square
	 * and changes some of their features
	 */
	public void createMagicConstantSquare()
	{
		magicConstat = new JLabel("All rows and columns must add: ");
		magicConstat.setForeground(new Color(32, 178, 170));
		magicConstat.setBackground(new Color(0, 0, 255));
		magicConstat.setFont(new Font("Tahoma", Font.PLAIN, 20));
	}

	/**
	 * Creates the Label for the magic square
	 * and changes some of their features
	 */
	public void createElapsedTime()
	{
		magicTime = new JLabel();
		magicTime.setForeground(new Color(32, 178, 170));
		magicTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.elapsedTime = new Timer(1000, this);
		this.elapsedTime.stop();
	}

	/**
	 * 
	 */
	public void createBestsTime()
	{
		bestMagicTime = new JLabel();
		bestMagicTime.setHorizontalAlignment(SwingConstants.LEFT);
		bestMagicTime.setForeground(new Color(30, 144, 255));
		bestMagicTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		//this.elapsedTime = new Timer(1000, this);
		bestMagicTime.setVisible(false);
	}


	/**
	 * 
	 * @return
	 */
	public int getLevel()
	{
		return this.level;	
	}

	/**
	 * 
	 */
	public void unknownBoxButtons()
	{
		this.unknownComboBox.addItemListener(new ItemListener()
		{
			@Override public void itemStateChanged(ItemEvent event) 
			{
				// If the item was selected
				if(event.getStateChange() == ItemEvent.SELECTED)
				{	
					if (unknownComboBox.getSelectedItem().toString().equals("Yes"))
					{
						unknow = true;		
						valuesSelect = true;
					}
					else if (unknownComboBox.getSelectedItem().toString().equals("No"))
					{
						unknow = false;		
						valuesSelect = true;						
					}

				}	
			}
		} );
	}

	/**
	 * Makes an item listener in order to know which
	 * level options was choose and reacts according
	 * to the user desire
	 */	
	public void sizesComboBoxButtons()
	{
		sizesComboBox.addItemListener(new ItemListener()
		{
			@Override public void itemStateChanged(ItemEvent event) 
			{
				// If the item was selected
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					dimension = Integer.parseInt(sizesComboBox.getSelectedItem().toString());
				}
			}
		} );
	}

	/**
	 * 
	 */
	public void summitButtons()
	{
		summit.addActionListener(new ActionListener()
		{
			// When the start button is pressed
			@Override public void actionPerformed(ActionEvent event)
			{
				if ( magicSquareModel.isMagicSquare() )
				{
					String ruta = null;
					if ( unknow )
						ruta = "Record-0" + (dimension-2) + ".txt";
					else
						ruta = "Record-" + (dimension-2)  + ".txt";

					elapsedTime.stop();
					JOptionPane.showMessageDialog(null, "You win, is a Magic Square");
					if (isBest(ruta))
					{
						overWriteBestScore(ruta);
					}

				}
			}
		} );
	}


	public boolean isBest(String ruta)	
	{
		BufferedReader textReader = null;
		File file = null;
		String temp[] = null;

		try
		{
			file = new File(this.getClass().getResource(ruta).toURI());
		} 
		catch (URISyntaxException e)
		{

		}
		catch (NullPointerException ne)
		{

			overWriteBestScore(ruta);
			return false;
		}

		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}

		try 
		{
			temp = textReader.readLine().split(" ");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Integer.valueOf(temp[0]) > (elapsedSeconds))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getBestTimeRecord(String ruta)
	{
		BufferedReader textReader = null;
		File file = null;
		String temp[] = null;

		try
		{
			file = new File(this.getClass().getResource(ruta).toURI());
		} 
		catch (URISyntaxException e)
		{

		}
		catch (NullPointerException ne)
		{
			return "No best time register";
		}

		try 
		{
			textReader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}

		try 
		{
			temp = textReader.readLine().split(" ");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int temporal = Integer.valueOf(temp[0]);
		StringBuilder name = new StringBuilder();

		for (int index = 1; index < temp.length; index++)
		{
			name.append(temp[index]);
			name.append(" ");
		}

		long minutes = temporal / 60;
		long seconds = temporal % 60;

		String text = String.format("Best time is from %sand it is %02d:%02d min"
				, name.toString()
				, minutes
				, seconds);

		return text;
	}


	public void overWriteBestScore(String ruta)
	{	
		//**
		try 
		{			
			textFormatter = new Formatter(ruta);
		}
		catch(Exception e)
		{
			System.out.print("cosito");
		}


		String message = JOptionPane.showInputDialog("Insert your name");

		textFormatter.format( "%d %s",elapsedSeconds,  message );

		textFormatter.close();

	}

	/**
	 * When the start button is pressed
	 * the listener, start the gameplay of the user
	 */
	public void gameStarted()
	{
		startButton.addActionListener(new ActionListener()
		{
			// When the start button is pressed
			@Override public void actionPerformed(ActionEvent event)
			{
				summit.setVisible(true);
				bestMagicTime.setVisible(true);
				if (dimension != 0 && valuesSelect)
				{
					elapsedTime.start();
					elapsedTime.setInitialDelay(1);
					elapsedSeconds = 0;

					//
					createTheButtons();

					//
					fillTheMatrix();
					//
					updateElapsedTime();

					// Get the number of the level
					int constant =  (dimension * (dimension*dimension + 1) / 2);
					// Calculates the magic constant
					magicConstat.setText("All rows and columns must add: " + constant);

					// Creates a reference to the model
					magicSquareModel = new MagicSquereModel(gameMatrix, dimension, unknow, constant );
					//
					addActionListenerToButtons();

					// See the class javadoc for more information
					magicSquareModel.read();

					String ruta = null;
					if ( unknow )
						ruta = "Record-0" + (dimension-2) + ".txt";
					else
						ruta = "Record-" + (dimension-2)  + ".txt";
					bestMagicTime.setText(getBestTimeRecord(ruta));
				}
			}
		} );
	}

	/**
	 * 
	 */
	public void fillTheMatrix()
	{
		for(int rows=0;rows<gameMatrix.length;rows++)
		{
			for(int cols=0;cols<gameMatrix.length;cols++)
			{
				gameMatrix[rows][cols].setText("");
			}
		}

	}

	/**
	 * 
	 */
	public void addActionListenerToButtons()
	{
		for(int rows=0;rows<gameMatrix.length;rows++)
		{
			for(int cols=0;cols<gameMatrix.length;cols++)
			{
				ActionListener myButtonListener = new ButtonListener();
				gameMatrix[rows][cols].addActionListener(myButtonListener);
			}
		}
	}


	/**
	 * 
	 */
	public void createTheButtons()
	{
		centerPosition.removeAll();

		gameMatrix = new JButton[dimension][dimension];
		// Makes a gridLayout of the matrix length
		centerPosition.setLayout(new GridLayout(dimension,dimension));

		// Fills the gridLayout with the buttons 
		for(int rows=0;rows<gameMatrix.length;rows++)
		{
			for(int cols=0;cols<gameMatrix.length;cols++)
			{
				gameMatrix[rows][cols] =  new JButton();
				gameMatrix[rows][cols].setFont(new Font("Serif", Font.PLAIN, 30));
				centerPosition.add(gameMatrix[rows][cols]);	
			}
		}
		centerPosition.updateUI();
		repaint();
	}



	/**
	 * Its a merely esthetic change, that modifies
	 * the form and the color of the buttons, watch 
	 * java 8 api for more information about the 
	 * try catch estructure
	 */
	public void changeButtonsSkin()
	{
		try 
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
		} 
		catch (ClassNotFoundException invalidClassE) 
		{
			invalidClassE.printStackTrace();
		}
		catch (InstantiationException instationClassE) 
		{
			instationClassE.printStackTrace();
		}
		catch (IllegalAccessException invalidAccesE) 
		{
			invalidAccesE.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException lookaAndFeelE)
		{
			lookaAndFeelE.printStackTrace();
		}
	}


	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{

		if ( event.getSource() == this.elapsedTime )
		{
			this.updateElapsedTime();
		}
	}

	/**
	 * 
	 */
	private void updateElapsedTime()
	{
		++this.elapsedSeconds;
		long minutes = this.elapsedSeconds / 60;
		long seconds = this.elapsedSeconds % 60;
		level = this.dimension-2;
		if (level != -2)
		{
			String text = String.format("Level: %d. Time %02d:%02d"
					, this.dimension - 2
					, minutes
					, seconds);
			this.magicTime.setText(text);
		}
	}


	/**
	 * 
	 * @author Diego Orozco & Cristian Rodriguez
	 *
	 */
	class ButtonListener extends MagicSquareView implements ActionListener 
	{
		private int newNumber;

		/**
		 * 
		 */
		@Override public void actionPerformed(ActionEvent e)
		{
			JButton source = (JButton) e.getSource();
			try 
			{
				this.newNumber = Integer.parseInt(JOptionPane.showInputDialog(null, source.getText() + " Insert the number"));
				source.setBackground(Color.ORANGE);
				source.setText(String.valueOf(newNumber));				
			}
			catch (NumberFormatException a)
			{
				JOptionPane.showMessageDialog(null, "Only numbers are valid!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}

