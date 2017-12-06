
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JButton;

/**
 * Class responsible for the validation of the 
 * information entered by the user in the magic 
 * square game 
 * 
 * @author Diego Orozco & Cristian Rodriguez
 */
public class MagicSquereModel extends Thread 
{
	//
	JButton gameMatrix[][] = null;

	// 	variable to save the dimensions of the square
	private int dimensions = 0;

	// variable to save the magic constant of the square
	private int magicConstant = 0;

	// matrix to to obtain the data in integers of each cell of the magic square given by the user in the interface
	private long magicSquare [][] = null;

	// Final variable that it determines if the picture fulfills the conditions to be a magic square
	private boolean isValid = true;


	/**
	 * constructor
	 * @param magicSquareMatrix
	 * @param dimensions
	 */
	public MagicSquereModel(JButton magicSquareMatrix[][],int dimensions)
	{
		this.gameMatrix = magicSquareMatrix;
		this.dimensions =dimensions;
	}

	/**
	 * constructor
	 */
	public MagicSquereModel() 
	{

	}

	

	/**
	 *  create the matrix and read the data given by the user
	 * @return
	 */
	public boolean read()
	{
		// calculate the magic constant
		this.magicConstant = (dimensions *( (dimensions*dimensions + 1) / 2));

		// create the matrix to save the data type int
		this.magicSquare = new long [dimensions][dimensions];

		// convert string data to integers
		try
		{
			for ( int rows = 0; rows < this.dimensions; ++rows )
			{
				// Read all values for current row
				for ( int col = 0; col < this.dimensions; col++ )
				{
					// convert the data in String of each cell of the magic square given by the user
					this.magicSquare[rows][col] = Integer.parseInt( gameMatrix[rows][col].getText() );
				}
			}
		}
		catch( InputMismatchException e)
		{
			return false;
		}

		return true;
	}

	/**
	 * method that is responsible for the validation 
	 * of the information and check of the validity 
	 * of the magic picture, calls sub-methods that 
	 * realize the vertical check, horizonal and diagonal
	 * 
	 * @return true if it is valid
	 */
	public boolean isMagicSquare()
	{
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
	 * check that the sum of all the rows equals the magic constant
	 */
	public void checkRows()
	{
		long sumValues = 0;

		for (int col = 0; col < this.dimensions && this.isValid; col++ )
		{
			sumValues = 0;
			for (int row = 0; row < this.dimensions; row++)
			{
				sumValues += this.magicSquare[row][col] ; 
			}

			// if the total sum is different from the constant, it is not a magic square
			if (sumValues != this.magicConstant)
			{
				// indicates that column number does not meet the magic condition
				printErr(col, "Column");	
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
				sumValues += this.magicSquare[row][col];
			}

			// if the total sum is different from the constant, it is not a magic square
			if (sumValues != this.magicConstant)
			{
				// indicates that row number does not meet the magic condition
				printErr(row, "Row");	
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
			sumValues += magicSquare[row][row];
		}
		
		// if the total sum is different from the constant, it is not a magic square
		if (sumValues != this.magicConstant)
		{
			this.isValid = false;
		}

		sumValues = 0;
		
		for (int row = this.dimensions - 1; row >= 0; row--)
		{
			sumValues += magicSquare[row][row];
		}

		// if the total sum is different from the constant, it is not a magic square
		if (sumValues != this.magicConstant)
		{
			this.isValid = false;
		}

	}

	/**
	 * print the number (column or row) that does not meet the condition
	 * @param notMagic number of column or row
	 * @param type (column or row)
	 */
	public void printErr(int notMagic, String type)
	{
		this.isValid = false;
		System.out.printf("%s %d %s%n", type, notMagic + 1, "is not magic");
	}

	/**
	 * @returnFinal variable that it determines if the picture fulfills the conditions to be a magic square
	 */
	public boolean isValid()
	{
		return this.isValid;
	}

	/**
	 * test made to verify the functionality of the game
	 */
	public void startGame()
	{
		int cont=1;

		gameMatrix[0][0].setText(""+4);
		gameMatrix[0][1].setText(""+9);
		gameMatrix[0][2].setText(""+2);
		gameMatrix[1][0].setText(""+3);
		gameMatrix[1][1].setText(""+5);
		gameMatrix[1][2].setText(""+7);
		gameMatrix[2][0].setText(""+8);
		gameMatrix[2][1].setText(""+1);
		gameMatrix[2][2].setText(""+6);

	}



}
