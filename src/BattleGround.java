import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;

public class BattleGround 
{
	final int FIELD_0 = 0;
	final int FIELD_1 = 1;
	final int FIELD_2 = 2;
	final int FIELD_3 = 3;
	final int FIELD_4 = 4;
	final int FIELD_5 = 5;
	final int FIELD_6 = 6;
	final int FIELD_7 = 7;
	final int FIELD_8 = 8;
	final int OUT_OF_FIELDS = 9;
	final int COLUMN_SHIFT = 3;
	final int SLANT_SHIFT = 4;
	final int NEVER_WILL_HAPPEN = 69;
	
	final String EMPTY_STRING = "";
	
	ArrayList<JButton> alFields;
	BattleGround(int iFrameWidth, int iFieldFromUpFrame, int iFieldSize, int iFieldFontSize, int iFieldShift)
	{
		alFields = new ArrayList<JButton>();
		for(int i = iFieldFromUpFrame; i < iFieldFromUpFrame + 3*iFieldSize; i += iFieldSize)
		{
			for(int j = (iFrameWidth-iFieldShift)/2; j < (iFrameWidth-iFieldShift)/2+3*iFieldSize; j += iFieldSize)
			{
				JButton bField = new JButton();
				bField.setSize(iFieldSize, iFieldSize);
				bField.setFont(new Font("ComicSans", Font.BOLD, iFieldFontSize));
				bField.setLocation(j, i);
				alFields.add(bField);
			}
		}
	}
	ArrayList<JButton> al_get_fields()
	{
		return alFields;
	}
	JButton jb_cpu_turn(String sUserSign, String sCPUSign)
	{
		int iMove = i_win(sUserSign, sCPUSign);
		if(iMove != OUT_OF_FIELDS)
			return alFields.get(iMove);
		iMove = i_defence(sUserSign, sCPUSign);
		if(iMove != OUT_OF_FIELDS)
			return alFields.get(iMove);
		else
			return alFields.get(i_offence(sUserSign, sCPUSign));
	}
	int i_defence(String sUserSign, String sCPUSign)
	{
		int iPotentialField = i_check_horizontal(sUserSign, sCPUSign);
		if(iPotentialField != OUT_OF_FIELDS)
			return iPotentialField;
		iPotentialField = i_check_vertical(sUserSign, sCPUSign);
		if(iPotentialField != OUT_OF_FIELDS)
			return iPotentialField;
		iPotentialField = i_check_aslant(sUserSign, sCPUSign);
		if(iPotentialField != OUT_OF_FIELDS)
			return iPotentialField;
		return OUT_OF_FIELDS;
	}
	int i_offence(String sUserSign, String sCPUSign) //To WIN i use method i_defence with reversed parameters
	{
		int iPotentialField = (new Random()).nextInt(OUT_OF_FIELDS);
		boolean bEmptyBattleGround = true;
		for(int i = 0; i < alFields.size(); i++)
			if(alFields.get(i).getText() == sCPUSign)
				bEmptyBattleGround = false;
		if(bEmptyBattleGround)
		{
			int iComm = (new Random()).nextInt(OUT_OF_FIELDS);
			while(alFields.get(iComm).getText() != EMPTY_STRING)
				iComm = (new Random()).nextInt(OUT_OF_FIELDS);
			return iComm;
		}
		else
		{
			while(alFields.get(iPotentialField).getText() != sCPUSign)
				iPotentialField = (new Random()).nextInt(OUT_OF_FIELDS);
			int iComm = i_one_sign_offence(sCPUSign, iPotentialField);
			return iComm;
		}
	}
	int i_win(String sUserSign, String sCPUSign)
	{
		int iPotentialField = i_two_sign_offence(sUserSign, sCPUSign);
		return iPotentialField;
	}
	int i_end(String sUserSign, String sCPUSign)
	{
		int iEnd = i_rows_end(sCPUSign, sUserSign);
		if(iEnd != 0)
			return iEnd;
		iEnd = i_column_end(sCPUSign, sUserSign);
		if(iEnd != 0)
			return iEnd;
		iEnd = i_slant_end(sCPUSign, sUserSign);
		return iEnd;
	}
	int i_rows_end(String sCPUSign, String sUserSign) //1 when user wins, 2 when CPU, 0 not win
	{
		for(int i = 0; i < OUT_OF_FIELDS; i += 3)
		{
			if(alFields.get(i).getText() == alFields.get(i+1).getText() && alFields.get(i).getText() == alFields.get(i+2).getText() && alFields.get(i).getText() == sUserSign)
				return 1;
			else if(alFields.get(i).getText() == alFields.get(i+1).getText() && alFields.get(i).getText() == alFields.get(i+2).getText() && alFields.get(i).getText() == sCPUSign)
				return 2;
		}
		return 0;
	}
	int i_column_end(String sCPUSign, String sUserSign)
	{
		for(int i = 0; i < 3; i++)
		{
			if(alFields.get(i).getText() == alFields.get(i + COLUMN_SHIFT).getText() && alFields.get(i).getText() == alFields.get(i + 2*COLUMN_SHIFT).getText() && alFields.get(i).getText() == sUserSign)
				return 1;
			else if(alFields.get(i).getText() == alFields.get(i + COLUMN_SHIFT).getText() && alFields.get(i).getText() == alFields.get(i + 2*COLUMN_SHIFT).getText() && alFields.get(i).getText() == sCPUSign)
				return 2;
		}
		return 0;
	}
	int i_slant_end(String sCPUSign, String sUserSign)
	{
		if(alFields.get(0).getText() == alFields.get(SLANT_SHIFT).getText() && alFields.get(0).getText() == alFields.get(2*SLANT_SHIFT).getText() && alFields.get(0).getText() == sUserSign)
			return 1;
		else if(alFields.get(0).getText() == alFields.get(SLANT_SHIFT).getText() && alFields.get(0).getText() == alFields.get(2*SLANT_SHIFT).getText() && alFields.get(0).getText() == sCPUSign)
			return 2;
		else if(alFields.get(2).getText() == alFields.get(SLANT_SHIFT).getText() && alFields.get(2).getText() == alFields.get(2+SLANT_SHIFT).getText() && alFields.get(2).getText() == sUserSign)
			return 1;
		else if(alFields.get(2).getText() == alFields.get(SLANT_SHIFT).getText() && alFields.get(2).getText() == alFields.get(2+SLANT_SHIFT).getText() && alFields.get(2).getText() == sUserSign)
			return 2;
		else
			return 0;
	}
	int i_check_horizontal(String sUserSign, String sCPUSign) //Check all rows
	{
		for(int i = 0; i < OUT_OF_FIELDS; i += COLUMN_SHIFT)
		{
			int iRowBlock = i_check_row(sUserSign, sCPUSign, i);
			if(iRowBlock != OUT_OF_FIELDS)
				return iRowBlock;
		}
		return OUT_OF_FIELDS;
	}
	int i_check_vertical(String sUserSign, String sCPUSign) //check all columns
	{
		for(int i = 0; i < 3; i++)
		{
			int iColumnBlock = i_check_column(sUserSign, sCPUSign, i);
			if(iColumnBlock != OUT_OF_FIELDS)
				return iColumnBlock;
					
		}
		return OUT_OF_FIELDS;
	}
	int i_check_aslant(String sUserSign, String sCPUSign) //Check all slants (Yes, in one method)
	{
		if(alFields.get(FIELD_0).getText() == sUserSign && alFields.get(FIELD_4).getText() == sUserSign && alFields.get(FIELD_8).getText() != sCPUSign)
			return FIELD_8;
		else if(alFields.get(FIELD_0).getText() == sUserSign && alFields.get(FIELD_8).getText() == sUserSign && alFields.get(FIELD_4).getText() != sCPUSign)
			return FIELD_4;
		else if(alFields.get(FIELD_4).getText() == sUserSign && alFields.get(FIELD_8).getText() == sUserSign && alFields.get(FIELD_0).getText() != sCPUSign)
			return FIELD_0;
		else if(alFields.get(FIELD_2).getText() == sUserSign && alFields.get(FIELD_4).getText() == sUserSign && alFields.get(FIELD_6).getText() != sCPUSign)
			return FIELD_6;
		else if(alFields.get(FIELD_2).getText() == sUserSign && alFields.get(FIELD_6).getText() == sUserSign && alFields.get(FIELD_4).getText() != sCPUSign)
			return FIELD_4;
		else if(alFields.get(FIELD_4).getText() == sUserSign && alFields.get(FIELD_6).getText() == sUserSign && alFields.get(FIELD_2).getText() != sCPUSign)
			return FIELD_2;
		return OUT_OF_FIELDS;
	}
	int i_check_row(String sUserSign, String sCPUSign, int iRowNumber) //Check ONE row
	{
		if(alFields.get(iRowNumber).getText() == sUserSign && alFields.get(iRowNumber + 1).getText() == sUserSign && alFields.get(iRowNumber + 2).getText() != sCPUSign)
			return iRowNumber + 2;
		else if(alFields.get(iRowNumber).getText() == sUserSign && alFields.get(iRowNumber + 2).getText() == sUserSign && alFields.get(iRowNumber + 1).getText() != sCPUSign)
			return iRowNumber + 1;
		else if(alFields.get(iRowNumber + 1).getText() == sUserSign && alFields.get(iRowNumber + 2).getText() == sUserSign && alFields.get(iRowNumber).getText() != sCPUSign)
			return iRowNumber;
		else 
			return OUT_OF_FIELDS;
	}
	int i_check_column(String sUserSign, String sCPUSign, int iColumnNumber) //Check ONE column
	{
		if(alFields.get(iColumnNumber).getText() == sUserSign && alFields.get(iColumnNumber + COLUMN_SHIFT).getText() == sUserSign && alFields.get(iColumnNumber + 2*COLUMN_SHIFT).getText() != sCPUSign)
			return iColumnNumber + 2*COLUMN_SHIFT;
		else if(alFields.get(iColumnNumber).getText() == sUserSign && alFields.get(iColumnNumber + 2*COLUMN_SHIFT).getText() == sUserSign && alFields.get(iColumnNumber + COLUMN_SHIFT).getText() != sCPUSign)
			return iColumnNumber + COLUMN_SHIFT;
		else if(alFields.get(iColumnNumber + COLUMN_SHIFT).getText() == sUserSign && alFields.get(iColumnNumber + 2*COLUMN_SHIFT).getText() == sUserSign && alFields.get(iColumnNumber).getText() != sCPUSign)
			return iColumnNumber;
		else 
			return OUT_OF_FIELDS;
	}
	int i_two_sign_offence(String sUserSign, String sCPUSing)
	{
		return i_defence(sCPUSing, sUserSign);
	}
	int i_one_sign_offence(String sCPUSing, int iField)
	{
		int iFieldToSign = OUT_OF_FIELDS-1;
		if(iField == FIELD_0)
		{
			while((iFieldToSign != FIELD_1 && iFieldToSign != FIELD_3 && iFieldToSign != FIELD_4) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(FIELD_5);
			return iFieldToSign;
		}
		else if(iField == FIELD_1)
		{
			while((iFieldToSign != FIELD_0 && iFieldToSign != FIELD_2 && iFieldToSign != FIELD_3 && iFieldToSign != FIELD_4 && iFieldToSign != FIELD_5) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(FIELD_6);
			return iFieldToSign;
		}
		else if(iField == FIELD_2)
		{
			while((iFieldToSign != FIELD_1 && iFieldToSign != FIELD_4 && iFieldToSign != FIELD_5) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(FIELD_6);
			return iFieldToSign;
		}
		else if(iField == FIELD_3)
		{
			while((iFieldToSign != FIELD_0 && iFieldToSign != FIELD_1 && iFieldToSign != FIELD_4 && iFieldToSign != FIELD_6 && iFieldToSign != FIELD_7) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(FIELD_8);
			return iFieldToSign;
		}
		else if(iField == FIELD_4)
		{
			while((iFieldToSign == FIELD_4) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(OUT_OF_FIELDS);
			return iFieldToSign;
		}
		else if(iField == FIELD_5)
		{
			while((iFieldToSign != FIELD_1 && iFieldToSign != FIELD_2 && iFieldToSign != FIELD_4 && iFieldToSign != FIELD_7 && iFieldToSign != FIELD_8) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(OUT_OF_FIELDS);
			return iFieldToSign;
		}
		else if(iField == FIELD_6)
		{
			while((iFieldToSign != FIELD_3 && iFieldToSign != FIELD_4 && iFieldToSign != FIELD_7) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(FIELD_8);
			return iFieldToSign;
		}
		else if(iField == FIELD_7)
		{
			while((iFieldToSign != FIELD_3 && iFieldToSign != FIELD_4 && iFieldToSign != FIELD_5 && iFieldToSign != FIELD_6 && iFieldToSign != FIELD_8) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(OUT_OF_FIELDS);
			return iFieldToSign;
		}
		else if(iField == FIELD_8)
		{
			while((iFieldToSign != FIELD_4 && iFieldToSign != FIELD_5 && iFieldToSign != FIELD_7) || alFields.get(iFieldToSign).getText() != EMPTY_STRING)
				iFieldToSign = (new Random()).nextInt(FIELD_6);
			return iFieldToSign;
		}
		return NEVER_WILL_HAPPEN;
	}
}
