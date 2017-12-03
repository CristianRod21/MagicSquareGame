
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JButton;

/**
 * 
 * @author Cristian
 *
 */
public class MagicSquereModel extends Thread 
{
	
	JButton gameMatrix[][] = null;
	private int dimensions = 0;
	
	private int magicConstant = 0;

	private String magicSquareL [][] = null;
	
	private long magicSquare [][] = null;

	private boolean isValid = true;


	public boolean read()
	{

			this.magicConstant = (dimensions *( (dimensions*dimensions + 1) / 2));
	
			this.magicSquareL = new String [dimensions][dimensions];
			this.magicSquare = new long [dimensions][dimensions];
			try
			{
				for ( int rows = 0; rows < this.dimensions; ++rows )
				{
					// Read all values for current row
					for ( int col = 0; col < this.dimensions; col++ )
					{
						System.out.println(gameMatrix[rows][col])  ;
					}
					System.out.println();

					
					
				}
				
			
				
				
			}
			catch( InputMismatchException e)
			{
				return false;
			}
			
			return true;
	}

	
	public boolean isMagicSquare()
	{
		if (this.isValid)
		{
			checkRows();
			if (this.isValid)
			{
				checkColums();

				if (this.isValid)
				{
					checkDiagonals();
				}
			}
		}
	
//		System.out.print(this.isValid);
		return this.isValid;
	}
	
	public void checkRows()
	{
		long addValues = 0;
		
		for (int col = 0; col < this.dimensions && this.isValid; col++ )
		{
			addValues = 0;
			for (int row = 0; row < this.dimensions; row++)
			{
				addValues += this.magicSquare[row][col] ; 
			}

			if (addValues != this.magicConstant)
			{
				printErr(col, "Column");	
				this.isValid = false;
			}
		}

	}
	
	public void checkColums()
	{
		long addValues = 0;

		for (int row = 0; row < this.dimensions && this.isValid == true; row++)
		{
			addValues = 0;
			for (int col = 0; col < this.dimensions; col++ )
			{
				addValues += this.magicSquare[row][col];
			}
			
			if (addValues != this.magicConstant)
			{
				printErr(row, "Row");	
				this.isValid = false;
			}

		}
		if (this.isValid == false)
		{
		}
	
	}
	
	public void checkDiagonals()
	{
		long addValues = 0;

		for (int row = 0; row < this.dimensions; row++)
		{
			addValues += magicSquare[row][row];
		}
		if (addValues != this.magicConstant)
		{
			this.isValid = false;
		}
		
		addValues = 0;
		
		for (int row = this.dimensions - 1; row >= 0; row--)
		{
			addValues += magicSquare[row][row];
		}
		if (addValues != this.magicConstant)
		{
			this.isValid = false;
		}

	}
	
	public void printErr(int notMagic, String type)
	{
		this.isValid = false;
		System.out.printf("%s %d %s%n", type, notMagic + 1, "is not magic");
	}
	
	public boolean isValid()
	{
		return this.isValid;
	}
	

//---	

	
	
	/**
	 * 
	 * @param magicSquareMatrix
	 * @param dimensions
	 */
	public MagicSquereModel(JButton magicSquareMatrix[][],int dimensions)
	{
		this.gameMatrix = magicSquareMatrix;
		this.dimensions =dimensions;
	}
	
	/**
	 * 
	 */
	public MagicSquereModel() 
	{
		
	}

	/**
	 * 
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
