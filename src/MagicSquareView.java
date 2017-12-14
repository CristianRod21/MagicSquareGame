import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;



/**
 * Class that controls the JApplet that
 * Initialize the view 
 * 
 * @author Diego Orozco & Cristian Rodriguez
 *
 * Final Version 2.0
 */

public class MagicSquareView extends JApplet implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// A reference to a matrix with the JButtons
	private JButton gameMatrix[][] = null;

	// A reference to a ComboBox, that contains the levels 
	private JComboBox<Object> sizesComboBox = null;

	// Contains the options about the unknowns
	private JComboBox<Object> unknownComboBox = null;

	// An array with the levels of the game
	private String gameLevels[]= {"-","3","4","5","6", "7", "8"};
	
	// An array with uknokw options
	private String unknowLevels[]= {"-","Yes", "No"};

	// A flag in order to know if the user choose the option as a yes
	private boolean unknow = false;

	// A JPanel that will be located at the center
	private JPanel centerPosition = null;

	// The welcome image panel 
	private JLabel picLabel  = null;

	// The Button, to star the game
	private JButton startButton = null;

	// When the level is finished, there is summit button to make checks
	private JButton summit = null;

	// Contents the magic constant number
	private JLabel magicConstat = null;

	// The label with the in game time, and the level
	private JLabel magicTime = null;

	// The best magic time register for that level
	private JLabel bestMagicTime = null;

	// The main lower panel
	private JPanel upperIndicatorPanel = null;

	// The top panel
	private JPanel lowerIndicatorPanel = null;
	
	// A reference to the model in order to make checks
	MagicSquereModel magicSquareModel = null;

	// The timer that we use to save the best scores
	private Timer elapsedTime = null;

	// The amount of seconds elapsed since the user press create button
	private long elapsedSeconds = 0;

	// The level
	private int level = 1;

	// The dimension of the game
	private int dimension = 0;

	// If a value is already select the flag turns on
	private boolean valuesSelect = false;

	// A reference to the class that reads the best high scores
	private BestHighScores bestHighScores = new BestHighScores();
	
	// The route in order to recover records
	private String fileRoute = null;
	
	public MagicSquareView()
	{
		init();
	}

	/**
	 * This methods calls the JApplet to run 
	 * 
	 */
	public void init()
	{
		createView();

		gameStarted();
	}

	
	/**
	 * This methods makes the view of the game
	 * call other methods in order to make his 
	 * center with the buttons, and the lower panel
	 * with some additional information, also the top panel
	 */
	public void createView()
	{
		// Change to location and the size of the JApplet
		getParent().setSize(1350, 780);
		this.getParent().getParent().setLocation(300, 50);

		// Makes the center JPanel
		centerPosition = new JPanel();

		// Esthetic change, to the buttons
		changeButtonsSkin();

		// Makes the top panel with its indicators
		createTopPanel();

		// Retrieve the image for the welcome menu
		createViewWelcome();

		// Creates the lower panel
		createLowerPanel();

		// Creates the combo box with the level options
		sizesComboBoxButtons();

		// Makes another combo box, but with the unknowns choices 
		unknownBoxButtons();

		// Creates the summit button
		summitButtons();

		// Add some of the created elements to the view
		addComponents();
	}

	

	/**
	 * Retrieves an image stored in the bin
	 * in order to display it as a welcome 
	 * screen to the user
	 */
	public void createViewWelcome()
	{
		// Buffer for reading the image
		BufferedImage myPicture = null;
		try 
		{
			// Tries to read the file
			myPicture = ImageIO.read(new File("menu.png"));
			
			// Creates a new JLabel with that image
			picLabel = new JLabel(new ImageIcon(myPicture));
			// Set it size
			picLabel.setSize(centerPosition.getWidth(), centerPosition.getHeight());
			// An add it to the center of the JApplet
			centerPosition.add(picLabel, BorderLayout.CENTER);;
			repaint();
		}
		catch (IOException exeption) 
		{
			exeption.printStackTrace();
		}
	
	}


	/**
	 * Add some components to the main view
	 * as the lower panel, the  upper indicator panel
	 * and the one within in the center position
	 * 
	 */
	public void addComponents()
	{
		// Adds the created elements to the entire view
		getContentPane().add(upperIndicatorPanel ,BorderLayout.NORTH);
		getContentPane().add(lowerIndicatorPanel ,BorderLayout.SOUTH);
		getContentPane().add(centerPosition,BorderLayout.CENTER);
	}

	/**
	 * Creates the necessary elements of the 
	 * upper panel, it includes the comboBox with
	 * the levels, the JLabel and the combo box for choosing
	 * if the game panel has or no unknowns show, also creates a 
	 * space for the best time in that level, and creates a button
	 * in order to start the game 
	 */
	public void createTopPanel()
	{
		// Makes a JPanel
		upperIndicatorPanel = new JPanel();

		// Creates the level box container
		createLevelBox();

		// Creates the button "Create"
		createStartButton();		
		
		// Creates the unknowns box and its label
		createunknownBox();
				
		// Creates a space for the best time record
		createBestsTime();

	}
	
	/**
	 * Creates the "Create" bottom
	 * that is the one that determinate
	 * when the game starts, also changes some
	 * attributes as the font, the icon etc
	 * 
	 */
	public void createStartButton()
	{
		// Creates the button, change font, foreground,
		// background, the icon, and its added to the lower panel
		startButton = new JButton("Create");
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		startButton.setForeground(new Color(0, 128, 128));
		startButton.setBackground(new Color(0, 139, 139));
		startButton.setIcon(new ImageIcon(MagicSquareView.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		upperIndicatorPanel.add(startButton);
	}
	
	/**
	 * Makes a combo box, that displays the levels 
	 * that the user want to choose, also creates a level
	 * to make more clear what the user is choosing, as the other
	 * buttons it changes some attributes as the font, the foreground 
	 */
	public void createLevelBox()
	{
		// Creates the combo box with the available level
		sizesComboBox= new JComboBox<Object>(gameLevels);
		sizesComboBox.setForeground(new Color(255, 140, 0));
		// Creates a Label named as size 
		JLabel label = new JLabel("Size: ");
		label.setForeground(new Color(255, 140, 0));
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		// Adds the size label, and the combo box with the levels
		upperIndicatorPanel.add(label);
		upperIndicatorPanel.add(sizesComboBox);
	}

	/**
	 * Creates the ComboBox in order to choose if the
	 * user want unknowns to be showed or not 
	 * also creates a JLabel it order to specify what 
	 * user is choose, also change some attributes
	 * finally it add them to the lower panel
	 */
	public void createunknownBox()
	{	
		// Creates a combo box with the options that user have, yes to show some help
		// no to a blank board
		unknownComboBox = new JComboBox<Object>(unknowLevels);
		unknownComboBox.setForeground(new Color(255, 140, 0));
		unknownComboBox.setBackground(new Color(255, 140, 0));
		
		// A JLabel in order to help the user identify what is he choosing
		JLabel lblUnknown = new JLabel("Enable Unknowns: ");
		lblUnknown.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnknown.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnknown.setForeground(new Color(255, 127, 80));
		// Adds both of the them to the upper panel
		upperIndicatorPanel.add(lblUnknown);
		upperIndicatorPanel.add(unknownComboBox);
	}
	
	/**
	 * Creates a label in the top in order
	 * to show the best time for that level
	 * also in the method we make some changes
	 * to some of its attributes 
	 * 
	 */
	public void createBestsTime()
	{
		// Creates JLabel in order to show the best time
		bestMagicTime = new JLabel();
		bestMagicTime.setHorizontalAlignment(SwingConstants.LEFT);
		bestMagicTime.setForeground(new Color(30, 144, 255));
		bestMagicTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		// Add it to the upper panel
		upperIndicatorPanel.add(bestMagicTime, BorderLayout.EAST);
		bestMagicTime.setVisible(false);
	}

	/**
	 * Its makes a JPanel and add some elements to it
	 * as the magic constant panel indicator, the elapsed time
	 * and the summit button, it changes some of their attributes
	 * as color, foreground and other esthetic things 
	 */
	public void createLowerPanel()
	{
		// Makes a JPanel to add some components 
		lowerIndicatorPanel = new JPanel();

		// Creates a border layout
		lowerIndicatorPanel.setLayout( new BorderLayout() );

		// Creates a Label where the magic Constant is shown 
		createMagicConstantSquare();
		

		// Create a timer in order to store the elapsed time
		createElapsedTime();
		
		
	}

	/**
	 * Makes the Summit button, change some of its
	 * attributes as the color, the font etc, and 
	 * set its visibility to false
	 */
	public void createSummitButton()
	{
		// Creates the button
		summit = new JButton("Summit");
		summit.setForeground(new Color(255, 0, 51));
		summit.setFont(new Font("Nirmala UI Semilight", Font.BOLD, 16));
		summit.setBackground(new Color(255, 0, 51));
		// Added to the lower panel, and change its visibility
		lowerIndicatorPanel.add(summit);
		summit.setVisible(false);
	}

	/**
	 * Creates the Label for the magic square
	 * and changes some of their features
	 * its shows the magic constant
	 */
	public void createMagicConstantSquare()
	{
		// Creates the label that will display the magic constant
		magicConstat = new JLabel("All rows, columns and diagonals must add: ");
		magicConstat.setForeground(new Color(32, 178, 170));
		magicConstat.setBackground(new Color(0, 0, 255));
		magicConstat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		// Added to the lower panel
		lowerIndicatorPanel.add(magicConstat, BorderLayout.WEST);
	}

	/**
	 * Creates the Label for the magic time
	 * and changes some of their features
	 * also makes the instance of the timer
	 * for taking notion of time
	 */
	public void createElapsedTime()
	{
		// Creates the label for the time
		magicTime = new JLabel();
		magicTime.setForeground(new Color(32, 178, 170));
		magicTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		// Creates the timer, to show the time
		this.elapsedTime = new Timer(1000, this);
		// Add the panel to the lower panel 
		lowerIndicatorPanel.add(magicTime, BorderLayout.EAST);
		// Stop the timer, in order to start until the user starts playing
		this.elapsedTime.stop();
	}

	/**
	 *  Its a listener, so when the user choose one of the both options
	 *  yes, or no, the view is able to the store some important values
	 *  at the time of the validation, to prevent bugs the valuesSelected
	 *  is used as flag to not start the game if its if false. So if the user press
	 *  yes that means that he want help with some uknonws, otherwise that means he wants
	 *  a blank board to start from scratch
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
					// If the item selected is yes
					if (unknownComboBox.getSelectedItem().toString().equals("Yes"))
					{
						// That means that the user wants some kind of help
						unknow = true;		
						valuesSelect = true;
					}
					else if (unknownComboBox.getSelectedItem().toString().equals("No"))
					{
						// The user wants a blank magic square
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
	 * to the user desire, also store as an attribute the dimensions of
	 * the square
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
					// Store the selected game dimension that the user wants
					if (!sizesComboBox.getSelectedItem().toString().equals("-"))
					dimension = Integer.parseInt(sizesComboBox.getSelectedItem().toString());
					
				}
					
			}
		} );
	}


	/**
	 * When the create button is pressed
	 * the listener, start the session of the user
	 * also sets some of the components that were not visible to visible
	 * creates the buttons according to the size, creates a new 
	 * reference to the model, starts the time, is basically the one in charge
	 * of the game to start
	 */
	public void gameStarted()
	{
		startButton.addActionListener(new ActionListener()
		{
			// When the start button is pressed
			@Override public void actionPerformed(ActionEvent event)
			{	
				// Now summit, at the best magic time area visible
				summit.setVisible(true);
				bestMagicTime.setVisible(true);
				// If the options were selected and dimension is not 0
				if (dimension != 0 && valuesSelect)
				{
					// Tell the time to start the time counting
					elapsedTime.start();
					elapsedTime.setInitialDelay(1);
					elapsedSeconds = 0;

					// Create the buttons, were the user choose the numbers
					createTheButtons();

					// Fills the matrix with the some empty values
					fillTheMatrix();
					
					// Updates the elapsed time
					updateElapsedTime();

					// Get the magic constant of the level
					int constant =  (dimension * (dimension*dimension + 1) / 2);
					// Set in the magic constant the one to be defeated
					magicConstat.setText("All rows, columns and diagonals must add: " + constant);
					// Creates a reference to the model
					magicSquareModel = new MagicSquereModel(gameMatrix, dimension, unknow, constant );
					// Add an action listener to each button
					addActionListenerToButtons();

					// See the class JavaDoc for more information
					magicSquareModel.read();
					 
					// Sets route of the files, more info at method JavaDoc
					setFileRoute();
					
					// Changes the best time record
					bestMagicTime.setText(bestHighScores.getBestTimeRecord(fileRoute, dimension));
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select the Size \n and if you want to Enable Unknowns or no");
				}
			}
		} );
	}
	
	/**
	 * Check if the matrix of the model is filled
	 * and if its is filled it checks if the user won, and also
	 * call the bestHighScores in order to know if the user broke a
	 * new record, otherwise if the matrix is not full, it will displays
	 * a message to that user that he is not able to summit an empty matrix
	 * 
	 */
	public void summitButtons()
	{
		summit.addActionListener(new ActionListener()
		{
			// When the create button is pressed
			@Override public void actionPerformed(ActionEvent event)
			{
				// The model checks if the matrix is complete full
				if ( magicSquareModel.isFill() )
				{
					// Checks if the user actually won
					if ( magicSquareModel.isMagicSquare() )
					{
						// Stop The time
						elapsedTime.stop();
						// Tell the user that he won
						JOptionPane.showMessageDialog(null, "You win, is a Magic Square");
						// If the time that the user got is better than the old record
						// the bestHighScore to overwrite them
						if (bestHighScores.isBest(fileRoute, dimension))
						{
							bestHighScores.overWriteBestScore(fileRoute, dimension);
						}
					}
					else
					{
						// The user haven't win yet
						JOptionPane.showMessageDialog(null, "Sorry, the matrix is not magic");						
					}
				}
				else
				{
					// The matrix still not full fill
					JOptionPane.showMessageDialog(null, "Sorry, the matrix must be full");
				}
			}
		} );
	}
	/**
	 * Creates a grid Layout in the center position
	 * and then its goes trough all the grid layout filling
	 * it with a button at a specific position, some button
	 * attributes are changed
	 * 
	 */
	public void createTheButtons()
	{
		// Remove all from the center position(welcome image)
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
	 * Sets the file route, in order for the bestHighScores
	 * to recover the record files from a specific level
	 * 
	 */
	public void setFileRoute()
	{
		// If has uknows
		if ( unknow )
			// The route is the level, with a 0 previously
			fileRoute = "Record-0" + (dimension-2) + ".txt";
		else
			// The route is the level
			fileRoute = "Record-" + (dimension-2)  + ".txt";
	}
	
	/**
	 * Fills the matrix with blanks spaces
	 * for commodity at the time of managing the
	 * matrix
	 */
	public void fillTheMatrix()
	{
		// Goes trough the rows
		for(int rows=0;rows<gameMatrix.length;rows++)
		{
			// Goes trough the cols
			for(int cols=0;cols<gameMatrix.length;cols++)
			{
				// Fill them with a blank space
				gameMatrix[rows][cols].setText("");
			}
		}

	}

	/**
	 * Adds an action Lister to each one of the buttons of 
	 * the magic matrix in order to know when were the 
	 * pressed
	 * 
	 */
	public void addActionListenerToButtons()
	{
		// Goes trough all the matrix
		for(int rows=0;rows<gameMatrix.length;rows++)
		{
			for(int cols=0;cols<gameMatrix.length;cols++)
			{
				// Creates a new lister an added it to the buttons
				ActionListener myButtonListener = new ButtonListener();
				gameMatrix[rows][cols].addActionListener(myButtonListener);
			}
		}
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
			// Set the look and feel accordingly to the system we are working with
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
	 * Method that makes the conversion also, goes 
	 * updating the elapsed time showed in the JLabel
	 * 
	 */
	private void updateElapsedTime()
	{
		// Add one to the elapsed seconds
		++this.elapsedSeconds;
		
		// Makes the conversion
		long minutes = this.elapsedSeconds / 60;
		long seconds = this.elapsedSeconds % 60;
		level = this.dimension-2;
		// If the level is valid
		if (level != -2)
		{
			// Change the magic time 
			String text = String.format("Level: %d. Time %02d:%02d"
					, this.dimension - 2
					, minutes
					, seconds);
			this.magicTime.setText(text);
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
	 * This is the class that reads the numbers 
	 * recovered from the buttons, when one of them
	 * is pressed its shows a Input Dialog, in order to 
	 * recover a number given by the user 
	 * @author Diego Orozco & Cristian Rodriguez
	 *
	 */
	class ButtonListener extends MagicSquareView implements ActionListener 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		// The number given
		private int newNumber = 0;

		/**
		 * When a button is pressed, show a dialog 
		 * to recover the value given by the user
		 * and use it as your new value
		 */
		@Override public void actionPerformed(ActionEvent eventPressed)
		{
			// Recovers what button was pressed
			JButton source = (JButton) eventPressed.getSource();
			try 
			{
				// Recovers the text from the JOption pane, and tries to make and integer
				this.newNumber = Integer.parseInt(JOptionPane.showInputDialog(null, source.getText() + " Insert the number"));
				source.setBackground(Color.ORANGE);
				// And changes the button, to his new text
				source.setText(String.valueOf(newNumber));					
			}
			catch (NumberFormatException a)
			{
				// A warning of only number are valid
				JOptionPane.showMessageDialog(null, "Only numbers are valid!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}

