package templates.json.datareading.options;

import main.Main;
import templates.json.savedata.lang.Lang;
import util.RefString;

public class Field 
{
	private String id;
	private boolean iterationField;
	private boolean carriesContents;
	private String TitleLangKey;
	private String Title;
	private String DefaultFillerKey;
	private String DefaultFiller;
	private RefString currentFiller;
	private int randomId;
	
	public Field(String id, String title, String Default, RefString currentFiller)
	{
		if (currentFiller == null)
		{
			currentFiller = new RefString(null);
		}
		this.id = id;
		this.carriesContents = id.matches("-?(0|[1-9]\\d*)");//regular expression for ensuring it is an integer
		this.iterationField = !this.carriesContents && id.startsWith("i");
		if (!this.carriesContents)
		{
			randomId = Main.rand.nextInt();
		}
		this.TitleLangKey = title;
		this.Title = null;
		this.DefaultFillerKey = Default;
		this.DefaultFiller = null;
		this.currentFiller = currentFiller;
	}
	
	public boolean iterationField()
	{
		return iterationField;
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getTitle()
	{
		if (Title == null)
		{
			Title = Lang.lang.getUILabel(TitleLangKey);
		}
		return Title;
	}
	
	private String getDefaultFiller()
	{
		if (DefaultFiller == null)
		{
			if (iterationField())
			{
				DefaultFiller = "1";
				if (DefaultFillerKey.matches("-?(0|[1-9]\\d*)"))
				{
					DefaultFiller = DefaultFillerKey;
				}
			}
			else
			{
				DefaultFiller = Lang.lang.getDefaultText(DefaultFillerKey);				
			}
		}
		return DefaultFiller;
	}
	
	public String getFiller()
	{
		return getCurrentFiller();
	}
	
	public String getCurrentFiller()
	{
		if (currentFiller.value == null)
		{
			currentFiller.value = getDefaultFiller();
		}
		return currentFiller.value;
	}
	
	public void updateCurrentFiller(String New)
	{
		if (iterationField() && !New.matches("-?(0|[1-9]\\d*)"))
		{
			New = "1";
		}
		currentFiller.value = New;
	}
	
	@Override
	public int hashCode() 
	{
		return (id + (this.carriesContents ? "" : ("" + randomId))).hashCode();
	}
}
