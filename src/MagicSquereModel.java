import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Class responsible for the validation of the 
 * information entered by the user in the magic 
 * square game 
 * 
 * @author Diego Orozco & Cristian Rodriguez
 */

public class MagicSquereModel extends Thread 
{
	// a reference to the matrix with the buttons
	JButton gameMatrix[][] = null;

	// 	variable to save the dimensions of the square
	private int dimensions = 0;

	// variable to save the magic constant of the square
	private int magicConstant = 0;


	// Final variable that it determines if the picture fulfills the conditions to be a magic square
	private boolean isValid = true;

	//
	private boolean unknow = false;

	//
	BufferedReader textReader = null;
	
	BestHighScores bestHighScores = new BestHighScores();



	/**
	 * Constructor
	 * @param magicSquareMatrix
	 * @param dimensions
	 */
	public MagicSquereModel(JButton magicSquareMatrix[][],int dimensions, boolean unknow, int magicConstant)
	{
		this.gameMatrix = magicSquareMatrix;
		this.dimensions =dimensions;
		this.unknow = unknow;
		this.magicConstant = magicConstant;
	}

	
	public MagicSquereModel()
	{
		
	}

	/**
	 *  Creates the matrix and read the data given by the user
	 * @return a boolean, that determinate if the matrix was able to read
	 */
	public boolean read()
	{
		bestHighScores.readTextFile(dimensions, textReader);
	
		// Convert string data from the buttons to integers
		
		if (this.unknow)
		{
			fillWithUnknow();
		}
		// In case of invalid data
		return true;
	}


	public boolean fillWithUnknow()
	{
		try
		{
			int count = 0;
			String[] temp = textReader.readLine().split(" ");
			for ( int rows = 0; rows < this.dimensions; ++rows )
			{			
				// Read all values for current row
				for ( int col = 0; col < this.dimensions; col++ )
				{
					boolean random = new Random().nextBoolean();
					
					if (random)
					{							
						gameMatrix[rows][col].setText(temp[count]);
					}
					count++;
				
				}
			}
		}
		catch( InputMismatchException exeption)
		{
			return false;
		} 
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;

	}

	/**
	 * Method that is responsible for the validation 
	 * of the information and check of the validity 
	 * of the magic picture, calls sub-methods that 
	 * realize the vertical check, horizontal and diagonal
	 * 
	 * @return true if it is valid
	 */
	public boolean isMagicSquare()
	{
		this.isValid = true;
		if (this.isValid)
		{
			// check the rows
			checkRows();
			if (this.isValid)
			{
				// check the columns
				checkColums();

				if (this.isValid)
				{
					// check the diagonals
					checkDiagonals();
				}

			}
		}
		return this.isValid;
	}

	/**
	 * Check that the sum of all the rows equals the magic constant
	 */
	public void checkRows()
	{
		long sumValues = 0;

		for (int col = 0; col < this.dimensions && this.isValid; col++ )
		{
			sumValues = 0;
			for (int row = 0; row < this.dimensions; row++)
			{
				sumValues += Integer.parseInt(this.gameMatrix[row][col].getText().toString().trim() ); 
			}

			// if the total sum is different from the constant, it is not a magic square
			if (sumValues != this.magicConstant)
			{
				// indicates that column number does not meet the magic condition
				this.isValid = false;
			}
		}

	}

	/**
	 * check that the sum of all the columns equals the magic constant
	 */
	public void checkColums()
	{
		long sumValues = 0;

		for (int row = 0; row < this.dimensions && this.isValid == true; row++)
		{
			sumValues = 0;
			for (int col = 0; col < this.dimensions; col++ )
			{
				sumValues += Integer.parseInt(this.gameMatrix[row][col].getText().toString() );
			}

			// if the total sum is different from the constant, it is not a magic square
			if (sumValues != this.magicConstant)
			{
				// indicates that row number does not meet the magic condition
				this.isValid = false;
			}

		}
	}

	/**
	 * check that the sum of all the diagonals equals the magic constant
	 */
	public void checkDiagonals()
	{
		long sumValues = 0;

		for (int row = 0; row < this.dimensions; row++)
		{
			sumValues += Integer.parseInt(this.gameMatrix[row][row].getText().toString() );
		}

		// if the total sum is different from the constant, it is not a magic square
		if (sumValues != this.magicConstant)
		{
			this.isValid = false;
		}

		sumValues = 0;

		for (int row = this.dimensions - 1; row >= 0; row--)
		{
			sumValues += Integer.parseInt(this.gameMatrix[row][row].getText().toString() );
		}

		// if the total sum is different from the constant, it is not a magic square
		if (sumValues != this.magicConstant)
		{
			this.isValid = false;
		}

	}
	
	public boolean isFill()
	{
		for (int row = 0; row < this.dimensions && this.isValid == true; row++)
		{
			for (int col = 0; col < this.dimensions; col++ )
			{
				if ( this.gameMatrix[row][col].getText().toString().equals("") )
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean seachValue(int value)
	{
		for (int row = 0; row < this.dimensions && this.isValid == true; row++)
		{
			for (int col = 0; col < this.dimensions; col++ )
			{
				if ( this.gameMatrix[row][col].getText().toString().equals("") )
				{
					if ( Integer.parseInt( this.gameMatrix[row][col].getText().toString() ) == value )
					{
						System.out.print( this.gameMatrix[row][col].getText() );
					
						return false;
					}
				}
				
			}
			System.out.println();
		}
		return true;
	}

	/**
	 * @returnFinal variable that it determines if the picture fulfills the conditions to be a magic square
	 */
	public boolean isValid()
	{
		return this.isValid;
	}


}
