import java.util.Scanner;



/** 
 * shows the magic square game interface, and is responsible 
 * for controlling the model and the view
 * 
 * @author Diego Orozco & Cristian Rodriguez
 */
public class MagicSquareController {

	// Create the attribute of the object that will do the show the interface
	private MagicSquareView magicSquareView = null;

	// Create the attribute of the object that will do the validation of the magic square game
	private MagicSquereModel magicSquareModel = null;

	/**
	 * Start the execution of the MagicSquareController
	 * @param args Command line arguments
	 */
	public static void main(String[] args) 
	{
		MagicSquareController magicSquareController = new MagicSquareController();
		magicSquareController.run();

	}

	/**
	 * Run the solution. This method is called from main()
	 */
	public void run()
	{
		// Create the object that will do the show the interface
		magicSquareView = new MagicSquareView();

		// Create the object that will do the validation of the magic square game
		magicSquareModel = new MagicSquereModel();

		// 
		magicSquareView.init();

		// 
		magicSquareModel.isMagicSquare();

		// if the data entered by the user was correct
		if ( magicSquareModel.read() ) 
		{  
			// the magic square is valid
			if ( magicSquareModel.isMagicSquare() && magicSquareModel.isValid())
			{
				System.out.print("Magic Square");
			}
		}
		else
		{
			System.out.print("Invalid Data");
		}
	}



	/**
	 * hacer un metodo que reporte las dimensiones 
	 * 
	 */



}
