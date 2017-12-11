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
	// a reference to the matrix with the buttons
	JButton gameMatrix[][] = null;

	// 	variable to save the dimensions of the square
	private int dimensions = 0;

	// variable to save the magic constant of the square
	private int magicConstant = 0;

	// matrix to to obtain the data in integers of each cell of the magic square given by the user in the interface
	private long magicSquare [][] = null;

	// Final variable that it determines if the picture fulfills the conditions to be a magic square
	private boolean isValid = true;
	
	private String level = null;


	/**
	 * Constructor
	 * @param magicSquareMatrix
	 * @param dimensions
	 */
	public MagicSquereModel(JButton magicSquareMatrix[][],int dimensions, String level)
	{
		this.gameMatrix = magicSquareMatrix;
		this.dimensions =dimensions;
		this.level = level;
	}

	/**
	 *  Default Constructor
	 */
	public MagicSquereModel() 
	{

	}

	/**
	 *  Creates the matrix and read the data given by the user
	 * @return a boolean, that determinate if the matrix was able to read
	 */
	public boolean read()
	{
		
		
		// calculate the magic constant
		this.magicConstant = (dimensions *( (dimensions*dimensions + 1) / 2));

		// create the matrix to save the data type int
		this.magicSquare = new long [dimensions][dimensions];

		// Convert string data from the buttons to integers
		try
		{
			int number = 0;
			for ( int rows = 0; rows < this.dimensions; ++rows )
			{
				// Read all values for current row
				for ( int col = 0; col < this.dimensions; col++ )
				{
					// convert the data in String of each cell of the magic square given by the user
					System.out.print ( this.magicSquare[rows][col] = level.charAt(number++) );
				}
				System.out.println();
			}
		}
		// In case of invalid data
		catch( InputMismatchException exeption)
		{
			return false;
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
/*
		gameMatrix[0][0].setText(""+10);
		gameMatrix[0][1].setText(""+9);
		gameMatrix[0][2].setText(""+2);
		gameMatrix[1][0].setText(""+3);
		gameMatrix[1][1].setText(""+5);
		gameMatrix[1][2].setText(""+7);
		gameMatrix[2][0].setText(""+8);
		gameMatrix[2][1].setText(""+1);
		gameMatrix[2][2].setText(""+6);
*/
	}


	 public void readTxt(){
		    try {
		        //ruta de tu archivo
		        String ruta = "archivo.txt"
		        BufferedReader br = getBuffered(ruta);
		        //leemos la primera linea
		        String linea =  br.readLine();
		        //creamos la matriz vacia
		        char[][] = new char[8][11];
		        //contador
		        int contador = 0;
		        while(linea != null){
		            String[] values = linea.split(",");
		            //recorremos el arrar de string
		            for (int i = 0; i<values.length; i++) {
		                //se obtiene el primer caracter de el arreglo de strings
		                char[contador][i] = values[i].charAt(0);
		            }
		            contador++;
		            linea = br.readLine();
		        }
		    } catch (IOException | NumberFormatException e) {
		        e.printStackTrace();
		    }
		}

}
