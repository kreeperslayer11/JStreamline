package util;

import java.util.ArrayList;

public class Resolution 
{
	private int res;
	
	private ArrayList<ResVals> resolutions = new ArrayList<>();
	
	public Resolution()
	{
		this.res = 0;
		resolutions.add(ResVals.DEFAULT);
		resolutions.add(ResVals.EIGHT_HUNDRED);
		resolutions.add(ResVals.SEVEN_TWENTY);
		resolutions.add(ResVals.TEN_TWENTYFOUR);
		resolutions.add(ResVals.TWELVE_EIGHTY);
		resolutions.add(ResVals.FOURTEEN_HUNDRED);
		resolutions.add(ResVals.TEN_EIGHTY);
		resolutions.add(ResVals.SIXTEEN_HUNDRED);
		resolutions.add(ResVals.EIGHTEEN_FIFTYSIX);
		resolutions.add(ResVals.NINETEEN_TWENTY);
		resolutions.add(ResVals.TWENTY_FOURTYEIGHT);
		resolutions.add(ResVals.FOUR_K);
	}
	
	public void resolutionChange(int switcher)
	{
		this.res = switcher;
	}
	
	public int getHeight()
	{
		return resolutions.get(res).getHeight();
	}
	
	public int getWidth()
	{
		return resolutions.get(res).getWidth();
	}
	
	public int getBorder()
	{
		return resolutions.get(res).getBorder();
	}
	
	public int getFontSize()
	{
		return resolutions.get(res).getFontSize();
	}
	
	public int getIndexFromSaveData(String[] splitOnX)
	{
		for (int i = 0; i < resolutions.size(); i++)
		{
			if (resolutions.get(i).getWidth() == Integer.parseInt(splitOnX[0]))
			{
				for (int j = 0; j < resolutions.size(); j++)
				{
					if (resolutions.get(i).getHeight() == Integer.parseInt(splitOnX[1]))
					{
						if (i == j)
						{
							return i;
						}
					}
				}
			}
		}
		return 0;
	}
}
