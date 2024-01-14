package util;

import main.JsonPreview;

public class InnerPreview
{
	private String option;
	private JsonPreview preview;
	
	public InnerPreview(JsonPreview preview, String innerOption)
	{
		this.preview = preview;
		this.option = innerOption;
	}
	
	public InnerPreview(JsonPreview preview, int innerOption)
	{
		this.preview = preview;
		this.option = preview.getOptions().get(innerOption);
	}
	
	//Removes the inner option from the JsonPreview
	public void remove()
	{
		preview.remove(option);
	}
	
	public String getTypeFolder()
	{
		return preview.getType().folder;
	}
	
	public String getFileName()
	{
		return preview.getFileName(option);
	}
	
	public String getFile()
	{
		return preview.getFile(option);
	}
}
