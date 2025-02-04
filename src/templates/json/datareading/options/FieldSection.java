package templates.json.datareading.options;

import java.util.ArrayList;

import templates.json.savedata.lang.Lang;

public class FieldSection 
{
	private String SectionName;
	private String SectionNameKey;
	private boolean HasSectionName;
	int which = 0;
	
	private ArrayList<Field> fields = new ArrayList<>();
	
	public FieldSection(String title)
	{
		if (title == null)
		{
			title = "";
		}
		this.SectionName = null;
		this.SectionNameKey = title;
		this.HasSectionName = title != "";
	}
	
	public boolean amITitled(String titleLangKey)
	{
		return SectionNameKey.equals(titleLangKey);
	}
	
	public String getSectionTitle()
	{
		if (HasSectionName)
		{
			if (SectionName == null)
			{
				SectionName = Lang.lang.getUILabel(SectionNameKey);
			}
			return SectionName;
		}
		return "";
	}
	
	public void addFieldInSequence(Field field)
	{
		fields.add(field);
	}
	
	public boolean HasNextField()
	{
		if (fields.size() > which)
		{
			return true;
		}
		which = 0;
		return false;
	}
	
	public Field getNextField()
	{
		Field tmp = fields.get(which);
		which++;
		return tmp;
	}
	
	public boolean contains(Field field)
	{
		//When handled through the handler, each field of an id is only created once.
		return fields.contains(field);
	}
}
