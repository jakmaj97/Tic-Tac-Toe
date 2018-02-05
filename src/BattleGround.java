import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;

public class BattleGround 
{
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
	ArrayList<JButton> getFields()
	{
		return alFields;
	}
}
