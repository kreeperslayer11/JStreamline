package templates.json.datareading.options;

import java.util.ArrayList;
import java.util.HashMap;

import util.RefString;

public class FieldHandler 
{
	private HashMap<String, Field> allFields = new HashMap<>();
	private HashMap<String, ArrayList<FieldSection>> allFieldSections = new HashMap<>();
	private HashMap<String, RefString> carryFieldContainers = new HashMap<>();
	
	private FieldHandler() {}
	
	public static FieldHandler handler = new FieldHandler();
	
	private FieldSection FindSection(ArrayList<FieldSection> sections, String named)
	{
		for (int i = 0; i < sections.size(); i++)
		{
			if (sections.get(i).amITitled(named))
			{
				return sections.get(i);
			}
		}
		return null;
	}
	
	private String makeKey(String whoAmI, String id)
	{
		return whoAmI + "_" + id;
	}
	
	public void add(String whoAmI, String Section, String id, String title, String Default)
	{
		String fieldsKey = makeKey(whoAmI, id);
		if (!allFields.containsKey(fieldsKey))
		{
			RefString ref = null;
			if (id.matches("-?(0|[1-9]\\d*)"))
			{
				if (carryFieldContainers.containsKey(id))
				{
					ref = carryFieldContainers.get(id);
				}
				else
				{
					ref = new RefString(null);
					carryFieldContainers.put(id, ref);
				}
			}
			allFields.put(fieldsKey, new Field(id, title, Default, ref));
			
		}
		Field field = allFields.get(fieldsKey);
		if (!allFieldSections.containsKey(whoAmI))
		{
			allFieldSections.put(whoAmI, new ArrayList<>());
		}
		ArrayList<FieldSection> sections = allFieldSections.get(whoAmI);
		FieldSection section = FindSection(sections, Section);
		
		if (section == null)
		{
			section = new FieldSection(Section);
			sections.add(section);
		}
		
		if (!section.contains(field))
		{
			section.addFieldInSequence(field);
		}
	}
	
	public ArrayList<FieldSection> acquireSectionsForMe(String WhoAmI)
	{
		return allFieldSections.get(WhoAmI);
	}
}
