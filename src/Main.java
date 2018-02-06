import java.util.Random;

import javax.swing.JFrame;

public class Main
{
	public final int WINDOW_SIZE = 500;
	public static void main(String[] args)
	{
		TicTacToeWindow tWindow1 = new TicTacToeWindow();
		tWindow1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tWindow1.setVisible(true);
	}
}
