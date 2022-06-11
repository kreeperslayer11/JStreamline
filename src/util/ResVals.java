package util;

public enum ResVals 
{
	DEFAULT(640, 500, 6, 12),
	EIGHT_HUNDRED(800, 600, 6, 12),
	SEVEN_TWENTY(960, 720, 8, 12),
	TEN_TWENTYFOUR(1024, 768, 9, 16),
	TWELVE_EIGHTY(1280, 960, 10, 16),
	FOURTEEN_HUNDRED(1400, 1050, 10, 16),
	TEN_EIGHTY(1440, 1080, 10, 16),
	SIXTEEN_HUNDRED(1600, 1200, 12, 24),
	EIGHTEEN_FIFTYSIX(1856, 1392, 14, 24),
	NINETEEN_TWENTY(1920, 1440, 14, 24),
	TWENTY_FOURTYEIGHT(2048, 1536, 16, 30),
	FOUR_K(3849, 2160, 16, 36);

	private final int height, width, border, font;
	
	private ResVals(int width, int height, int border, int font)
	{
		this.height = height;
		this.width = width;
		this.border = border;
		this.font = font;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getBorder()
	{
		return this.border;
	}
	
	public int getFontSize()
	{
		return this.font;
	}
}
