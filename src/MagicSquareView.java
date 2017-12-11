
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;


/**
 * Class that controls the JApplet that
 * Initialize the view 
 * 
 * @author Diego Orozco & Cristian Rodriguez
 *
 */
public class MagicSquareView extends JApplet implements ActionListener 
{
	// A reference to a matrix with the JButtons
	private JButton gameMatrix[][] = null;

	// A reference to a ComboBox, that contains the levels 
	private JComboBox sizesComboBox = null;

	private JComboBox unknownComboBox = null;

	// An array with the levels of the game
	private String gameLevels[]= {"3","4","5","6", "7", "9", "12"};

	private String unknowLevels[]= {"3","6","9","12", "15"};

	// A JPanel that will be located at the center
	private JPanel centerPosition = null;


	
	// The Jbutton, to star the game
	private JButton startButton = null;

	// Contents the magic constant number
	private JLabel magicConstat = null;

	private JLabel magicTime = null;


	// The main lower panel
	private JPanel lowerPanel = null;

	private JPanel indicatorPanel = null;


	MagicSquereModel magicSquareModel = null;


	private Timer elapsedTime = null;
	private long elapsedSeconds = 0;

	private int level = 1;
	
	private int dimension = 0;



	/**
	 * This methods calls the JApplet to run 
	 * 
	 */
	public void init()
	{				
		createView();

		gameStarted();
		
		JOptionPane.showMessageDialog(null, "Welcome to Magic Square Game\n" +
				                            "To begin to play, first you must write your " +
				                            "name and select the level that wants to begin.", 
				                            "Instruction",
				                            JOptionPane.WARNING_MESSAGE);


	}
	
	
	public void createViewWelcome()
	{
		
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
		this.setSize(640, 480);
		// Esthetic change
		this.changeButtonsSkin();

		// 
		createLowerPanel();
		
		//
		createIndicatorPanel();

		//
		createTheButtons();

		// Adds the created elements to the entire view
		this.add(lowerPanel ,BorderLayout.NORTH);
		this.add(indicatorPanel ,BorderLayout.SOUTH);
		this.add(centerPosition,BorderLayout.CENTER);

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
		lowerPanel.add(new JLabel("Size: "));
		lowerPanel.add(sizesComboBox);

		//
		createunknownBox();
		lowerPanel.add(new JLabel("unknown: "));
		lowerPanel.add(unknownComboBox);

		//
		startButton = new JButton("Create");
		lowerPanel.add(startButton);
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
	}

	/**
	 * Makes a combo box, that displays the levels 
	 * that the user wanna choose 
	 */
	public void createLevelBox()
	{
		sizesComboBox= new JComboBox<Object>(gameLevels);
	}

	/**
	 * 
	 */
	public void createunknownBox()
	{	
		unknownComboBox = new JComboBox<Object>(unknowLevels);
	}


	/**
	 * Creates the Label for the magic square
	 * and changes some of their features
	 */
	public void createMagicConstantSquare()
	{
		magicConstat = new JLabel("All rows and columns must add: ");
		magicConstat.setBackground(new Color(220, 20, 60));
		magicConstat.setFont(new Font("Serif", Font.PLAIN, 20));

	}

	/**
	 * Creates the Label for the magic square
	 * and changes some of their features
	 */
	public void createElapsedTime()
	{
		magicTime = new JLabel();
		magicTime.setFont(new Font("Serif", Font.PLAIN, 20));
		this.elapsedTime = new Timer(1000, this);
		this.elapsedTime.start();
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
	 * Makes an item listener in order to know which
	 * level options was choose and reacts according
	 * to the user desire
	 */
	public void createTheButtons()
	{
		sizesComboBox.addItemListener(new ItemListener()
		{
			@Override public void itemStateChanged(ItemEvent event) 
			{
				// If the item was selected
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					// Remove all the previous buttons from the old level
					centerPosition.removeAll();
					// Gets the selected level, and create a matrix of buttons according to the user
					// desired level
					
					dimension = Integer.parseInt(sizesComboBox.getSelectedItem().toString());

					gameMatrix = new JButton[dimension][dimension];
					// Makes a gridLayout of the matrix length
					centerPosition.setLayout(new GridLayout(gameMatrix.length,gameMatrix.length));


					// Fills the gridLayout with the buttons 
					for(int rows=0;rows<gameMatrix.length;rows++)
					{
						for(int cols=0;cols<gameMatrix.length;cols++)
						{
							gameMatrix[rows][cols] = new JButton();
							centerPosition.add(gameMatrix[rows][cols]);	
						}
					}
					centerPosition.updateUI();
					repaint();
				}
			}
		} );
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
				// Get the number of the level
				int constant = Integer.parseInt(sizesComboBox.getSelectedItem().toString());
				// Calculates the magic constant
				magicConstat.setText("All rows and columns must add: " + constant * ((constant*constant + 1) / 2));

				// Creates a reference to the model
				magicSquareModel = new MagicSquereModel(gameMatrix,Integer.parseInt(sizesComboBox.getSelectedItem().toString()), "/asset/level01.txt" );

				
				for(int rows=0;rows<gameMatrix.length;rows++)
				{
					for(int cols=0;cols<gameMatrix.length;cols++)
					{
						ActionListener myButtonListener = new ButtonListener();
						gameMatrix[rows][cols].addActionListener(myButtonListener);

					}
				}

				// See the class javadoc for more information
				magicSquareModel.startGame();
				magicSquareModel.read();
				
				


				/**
				 * Its a quick fix, its necessary to review this, in order
				 * to keep the neutrality of the MVC standar
				 */
				validateMagicSquare();
			}
		} );
	}

	/**
	 * Temporary method that validates if the given matrix
	 * is valid, and if the user win or lose
	 */
	public void validateMagicSquare()
	{
		magicSquareModel.isMagicSquare();

		// if the data entered by the user was correct
		if ( magicSquareModel.read() ) 
		{  
			// the magic square is valid
			if ( magicSquareModel.isMagicSquare() && magicSquareModel.isValid())
			{
				JOptionPane.showMessageDialog(null, "You win, is a Magic Square");
			}
		}
		else
		{
			System.out.print("Invalid Data");
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
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		level = getLevel();
		String text = String.format("Level: %d. Time %02d:%02d"
				, level
				, minutes
				, seconds);
		this.magicTime.setText(text);


	}
	
	
	class ButtonListener extends MagicSquareView implements ActionListener 
	{
		private String newNumber;

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			this.newNumber = JOptionPane.showInputDialog(source, source.getText() + " Insert the number");

		}
		
		
		public String getNumber()
		{
			return this.newNumber;
		}

	}




	
}

