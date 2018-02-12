import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.sun.prism.paint.Color;

import javafx.scene.layout.Border;

public class TicTacToeWindow extends JFrame implements ActionListener
{
	final int WINDOW_WIDTH = 400;
	final int WINDOW_HEIGHT = 500;
	final int TITLE_HEIGHT = 40;
	final int TITLE_WIDTH = 200;
	final int TITLE_FONT_SIZE = 30;
	final int TITLE_FROM_UP_FRAME = 20;
	final int SPEAKER_HEIGHT = 20;
	final int SPEAKER_WIDTH = 200;
	final int SPEAKER_FONT_SIZE = 15;
	final int SPEAKER_FROM_UP_FRAME = 75;
	final int CHAMPION_SIZE = 100;
	final int CHAMPION_FONT_SIZE = 60;
	final int FIELD_SIZE = 50;
	final int FIELD_FONT_SIZE = 20;
	final int FIELD_FROM_UP_FRAME = 150;
	final int FIELD_SHIFT = 170;
	final int EXIT_HEIGHT = 30;
	final int EXIT_WIDTH = 100;
	
	final String TITLE = "Tic-Tac-Toe";
	final String SPEAKER_COMM_1 = "Choose your champion!";
	final String SPEAKER_COMM_2 = "CPU is first!";
	final String SPEAKER_COMM_3 = "You are first!";
	final String SPEAKER_COMM_4 = "CPU's turn!";
	final String SPEAKER_COMM_5 = "Your turn!";
	final String SPEAKER_COMM_6 = "Your champion is ";
	final String SPEAKER_COMM_7 = "Congratulations! You won!";
	final String SPEAKER_COMM_8 = "You lost :(";
	final String SPEAKER_COMM_9 = "Oops! Draw!";
	final String CHAMPION_X_NAME = "X";
	final String CHAMPION_O_NAME = "O";
	final String EMPTY_STRING = "";
	final String EXIT = "Exit";
	
	JLabel lTitle;
	JLabel lSpeaker;
	JButton bChampionX;
	JButton bChampionO;
	JButton bExit;
	String sUserSign;
	String sCPUSign;
	BattleGround bgBattleGround;
	
	TicTacToeWindow()
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setTitle(TITLE);
		setLayout(null);
		
		//TITLE
		lTitle = new JLabel(TITLE);
		lTitle.setSize(TITLE_WIDTH, TITLE_HEIGHT);
		lTitle.setFont(new Font("ComicSans", Font.BOLD, TITLE_FONT_SIZE));
		lTitle.setLocation((getWidth()-lTitle.getWidth())/2, TITLE_FROM_UP_FRAME);
		add(lTitle);
		
		//SPEAKER
		lSpeaker = new JLabel(SPEAKER_COMM_1);
		lSpeaker.setSize(SPEAKER_WIDTH, SPEAKER_HEIGHT);
		lSpeaker.setFont(new Font("ComicSans", Font.BOLD, SPEAKER_FONT_SIZE));
		lSpeaker.setLocation((getWidth()-lSpeaker.getWidth())/2, SPEAKER_FROM_UP_FRAME);
		add(lSpeaker);
		
		//EXIT
		bExit = new JButton(EXIT);
		bExit.setSize(EXIT_WIDTH, EXIT_HEIGHT);
		bExit.setFont(new Font("ComicSans", Font.BOLD, FIELD_FONT_SIZE));
		bExit.setLocation(WINDOW_HEIGHT/2, WINDOW_HEIGHT-EXIT_WIDTH);
		bExit.addActionListener(this);
		add(bExit);
		
		//CHAMPIONS
		bChampionX = new JButton(CHAMPION_X_NAME);
		bChampionX.setSize(CHAMPION_SIZE, CHAMPION_SIZE);
		bChampionX.setFont(new Font("ComicSans", Font.BOLD, CHAMPION_FONT_SIZE));
		bChampionX.setLocation((getWidth()-bChampionX.getWidth())/2+(getWidth()/5), (getHeight()-bChampionX.getHeight())/2);
		bChampionX.addActionListener(this);
		add(bChampionX);
		
		bChampionO = new JButton(CHAMPION_O_NAME);
		bChampionO.setSize(CHAMPION_SIZE, CHAMPION_SIZE);
		bChampionO.setFont(new Font("ComicSans", Font.BOLD, CHAMPION_FONT_SIZE));
		bChampionO.setLocation((getWidth()-bChampionO.getWidth())/2-(getWidth()/4), (getHeight()-bChampionO.getHeight())/2);
		bChampionO.addActionListener(this);
		add(bChampionO);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if((e.getSource()).equals(bExit))
			dispose();
		else
		{
			if((e.getSource()).equals(bChampionX) || (e.getSource()).equals(bChampionO))
			{
				if((e.getSource()).equals(bChampionX))
				{
					sUserSign = CHAMPION_X_NAME;
					sCPUSign = CHAMPION_O_NAME;
					lSpeaker.setText(SPEAKER_COMM_6+CHAMPION_X_NAME);
				}
				else
				{
					sUserSign = CHAMPION_O_NAME;
					sCPUSign = CHAMPION_X_NAME;
					lSpeaker.setText(SPEAKER_COMM_6+CHAMPION_O_NAME);
				}
				bChampionO.setVisible(false);
				bChampionX.setVisible(false);
				remove(bChampionO);
				remove(bChampionX);	
				v_add_battle_ground();
			}
			for(int i = 0; i < bgBattleGround.al_get_fields().size(); i++)
				if((e.getSource()).equals(bgBattleGround.al_get_fields().get(i)) && bgBattleGround.al_get_fields().get(i).getText() == EMPTY_STRING)
				{
					bgBattleGround.al_get_fields().get(i).setText(sUserSign);
					if(!b_check_end())
						bgBattleGround.jb_cpu_turn(sUserSign, sCPUSign).setText(sCPUSign);
					b_check_end();
				}	
		}
	}	
	boolean b_check_end()
	{
		int iCounter = 0;
		for(int i = 0; i < bgBattleGround.al_get_fields().size(); i++)
			if(bgBattleGround.al_get_fields().get(i).getText() != EMPTY_STRING)
				iCounter++;
		if(iCounter == 9)
		{
			lSpeaker.setText(SPEAKER_COMM_9);
			return true;
		}
		else
		{
			int iEnd = bgBattleGround.i_end(sUserSign, sCPUSign);
			if(iEnd == 1 || iEnd == 2)
			{ 
				if(iEnd == 1)
					lSpeaker.setText(SPEAKER_COMM_7);
				else
					lSpeaker.setText(SPEAKER_COMM_8);
				for(int j = 0; j < bgBattleGround.al_get_fields().size(); j++)
					if(bgBattleGround.al_get_fields().get(j).getText() == EMPTY_STRING)
						remove (bgBattleGround.al_get_fields().get(j));
				return true;
			}
			return false;
		}
	}
	void v_add_battle_ground()
	{
		bgBattleGround = new BattleGround(WINDOW_WIDTH, FIELD_FROM_UP_FRAME, FIELD_SIZE, FIELD_FONT_SIZE, FIELD_SHIFT);
		for(int i = 0; i < bgBattleGround.al_get_fields().size(); i++)
		{
			bgBattleGround.al_get_fields().get(i).addActionListener(this);
			add(bgBattleGround.al_get_fields().get(i));
		}
	}
}
