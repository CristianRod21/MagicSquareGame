
public class MagicSquareController {

	private MagicSquareView magicSquareView = null;
	private MagicSquereModel magicSquareModel = null;
	
	
	public static void main(String[] args) 
	{
		MagicSquareController magicSquareController = new MagicSquareController();
		magicSquareController.run();
		
	}
	
	public void run()
	{
		magicSquareView = new MagicSquareView();
		magicSquareModel = new MagicSquereModel();
		
		magicSquareView.init();
	}
	
	/**
	 * hacer un metodo que reporte las dimensiones 
	 * 
	 */
	
	

}
