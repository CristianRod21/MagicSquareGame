
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Class that controls the JApplet that
 * Initialize the view 
 * 
 * @author Diego Orozco & Cristian Rodriguez
 *
 */
public class MagicSquareView extends JApplet 
{
	// A reference to a matrix with the JButtons
	private JButton gameMatrix[][] = null;
	// A reference to a ComboBox, that contains the levels 
	private JComboBox sizesComboBox = null;
	// An array with the levels of the game
	private String gameLevels[]= {"3","4","5","6", "7", "9", "12"};
	// A JPanel that will be located at the center
	private JPanel centerPosition = null;
	// The Jbutton, to star the game
	private JButton startButton = null;
	// Contents the magic constant number
	private JLabel magicConstat = null;
	// The main lower panel
	private JPanel lowerPanel = null;
	MagicSquereModel magicSquareModel = null;
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
	 * with some additional information
	 */
	public void createView()
	{
		// Makes the center JPanel
		centerPosition = new JPanel();

		// Esthetic change
		this.changeButtonsSkin();

		createLowerPanel();

		createTheButtons();
		
		// Adds the created elements to the entire view
		this.add(lowerPanel ,BorderLayout.SOUTH);
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
		lowerPanel.add(new JLabel("Size: "));
		
		// See method java doc for more information
		createLevelBox();
		createMagicConstantSquare();
		
		startButton = new JButton("Start");

		// Adds the components to the panel
		lowerPanel.add(sizesComboBox);
		lowerPanel.add(startButton);
		lowerPanel.add(magicConstat, BorderLayout.EAST);
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
	 * Creates the Label for the magic square
	 * and changes some of their features
	 */
	public void createMagicConstantSquare()
	{
		magicConstat = new JLabel("Magic Constant: ");
		magicConstat.setBackground(new Color(220, 20, 60));
		magicConstat.setFont(new Font("Serif", Font.PLAIN, 20));
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
					gameMatrix = new JButton[Integer.parseInt(sizesComboBox.getSelectedItem().toString())]  [Integer.parseInt(sizesComboBox.getSelectedItem().toString())];
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
		});

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
				magicConstat.setText("Magic Constant " + constant * ((constant*constant + 1) / 2));
				
				// Creates a reference to the model
				magicSquareModel = new MagicSquereModel(gameMatrix,Integer.parseInt(sizesComboBox.getSelectedItem().toString()));
				
				
				// See the class javadoc for more information
				magicSquareModel.startGame();
				magicSquareModel.read();
				
				/**
				 * Its a quick fix, its necessary to review this, in order
				 * to keep the neutrality of the MVC standar
				 */
				validateMagicSquare();
			}
		});
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
				System.out.print("You win, is a Magic Square");
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
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
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

}
