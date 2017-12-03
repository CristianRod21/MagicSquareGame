
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
 * 
 * @author Cristian & Diego
 *
 */
public class MagicSquareView extends JApplet 
{
	

	private JButton gameMatrix[][] = null;
	private JComboBox sizesComboBox = null;
	private String gameLevels[]={"3","5","7","9"};
	private JPanel centerPosition = null;
	private JButton startButton = null;

	/**
	 * 
	 */
	public void init()
	{		
		changeButtonsSkin();
		JPanel lowerPanel = new JPanel();
		lowerPanel .add(new JLabel("Size: "));
		sizesComboBox= new JComboBox<Object>(gameLevels);
		
		final JLabel lblMoves = new JLabel("Magic Constant: ");
		lblMoves.setBackground(new Color(220, 20, 60));
		lblMoves.setFont(new Font("Serif", Font.PLAIN, 20));
		
		
		sizesComboBox.addItemListener(new ItemListener()
		{

			@Override public void itemStateChanged(ItemEvent event) 
			{
				if(event.getStateChange()==ItemEvent.SELECTED)
				{
					centerPosition.removeAll();
					gameMatrix=new JButton[Integer.parseInt(sizesComboBox.getSelectedItem().toString())][Integer.parseInt(sizesComboBox.getSelectedItem().toString())];
					centerPosition.setLayout(new GridLayout(gameMatrix.length,gameMatrix.length));
					for(int rows=0;rows<gameMatrix.length;rows++)
					{
						for(int cols=0;cols<gameMatrix.length;cols++)
						{
							gameMatrix[rows][cols]=new JButton();
							centerPosition.add(gameMatrix[rows][cols]);
						}
					}
					centerPosition.updateUI();
					repaint();
				}
			}
		});

		lowerPanel.add(sizesComboBox);
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener()
		{

			@Override public void actionPerformed(ActionEvent event)
			{
				int constant = Integer.parseInt(sizesComboBox.getSelectedItem().toString());
				lblMoves.setText("Magic Constant " + constant * ((constant*constant + 1) / 2));
				MagicSquereModel magicSquare = new MagicSquereModel(gameMatrix,Integer.parseInt(sizesComboBox.getSelectedItem().toString()));
				magicSquare.startGame();
				magicSquare.read();
				
			}

		});
		lowerPanel.add(startButton);
		lowerPanel.add(lblMoves, BorderLayout.EAST);

		centerPosition = new JPanel();

		add(lowerPanel ,BorderLayout.SOUTH);
		add(centerPosition,BorderLayout.CENTER);

	}

	/**
	 * 
	 */
	public void changeButtonsSkin()
	{
		try 
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException invalidClassE) 
		{
			invalidClassE.printStackTrace();
		} catch (InstantiationException instationClassE) 
		{
			instationClassE.printStackTrace();
		} catch (IllegalAccessException invalidAccesE) 
		{
			invalidAccesE.printStackTrace();
		} catch (UnsupportedLookAndFeelException lookaAndFeelE)
		{
			lookaAndFeelE.printStackTrace();
		}
	}

}
