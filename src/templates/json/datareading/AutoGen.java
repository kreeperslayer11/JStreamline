package templates.json.datareading;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import templates.json.datareading.options.FieldHandler;
import templates.json.datareading.options.FieldSection;
import templates.json.savedata.settings.Save;
import util.FileHandler;
import util.Reference;
import util.json.parser.IterationFieldStorage;
import util.json.parser.JsonHandler;

public class AutoGen 
{
	private ArrayList<Template> generationTemplates = new ArrayList<>();
	
	public ArrayList<Template> getTemplates()
	{
		return generationTemplates;
	}
	
	public static AutoGen gen = new AutoGen();
	
	private AutoGen() {}
	
	private String[] getTemplateFiles()
	{
		String[] templates = FileHandler.filesInDirectory(new File(Reference.TEMPLATE_FOLD));
    	for (int i = 0; i < templates.length; i++)
    	{
    		int end = templates[i].length() - ".json".length();
    		templates[i] = templates[i].substring(0, end);
    	}
    	return templates;
	}
	
	private void parseFieldsInSection(String WhoAmI, String SectionName, JsonArray fields)
	{
		int fieldCount = fields.size();
		for (int i = 0; i < fieldCount; i++)
		{
			String id = fields.get(i).getAsJsonObject().get("id").getAsString();
			String Title = fields.get(i).getAsJsonObject().get("Title").getAsString();
			String Default = fields.get(i).getAsJsonObject().get("Default").getAsString();
			FieldHandler.handler.add(WhoAmI, SectionName, id, Title, Default);
		}
	}
	
	private void parseFieldSections(String WhoAmI, JsonArray required)
	{
		int sectionCount = required.size();
		for (int i = 0; i < sectionCount; i++)
		{
			String SectionName = required.get(i).getAsJsonObject().get("SectionName").getAsString();
			JsonArray fields = required.get(i).getAsJsonObject().get("SectionFields").getAsJsonArray();
			parseFieldsInSection(WhoAmI, SectionName, fields);
		}
	}
	
	private SingleTemplateFile parseSingleTemplate(String FileName, String FilePath, JsonObject contents, JsonArray Iterate)
	{
		IterationFieldStorage[] contentIterationTypes;
		if (Iterate != null)
		{
			Gson gson = new Gson();
			contentIterationTypes = new IterationFieldStorage[Iterate.size()];
			for (int i = 0; i < Iterate.size(); i++)
			{
				String id = Iterate.get(i).getAsJsonObject().get("For").getAsString();
				String[] levels = gson.fromJson(Iterate.get(i).getAsJsonObject().get("At").getAsJsonArray(), String[].class);
				contentIterationTypes[i] = new IterationFieldStorage(id, levels);
			}
		}
		else
		{
			contentIterationTypes = new IterationFieldStorage[0];
		}
		return new SingleTemplateFile(FileName, FilePath, contents, contentIterationTypes);
	}
	
	private void parseFileTemplates(String WhoAmI, JsonArray templates)
	{
		ArrayList<FieldSection> sections = FieldHandler.handler.acquireSectionsForMe(WhoAmI);
		int templateCount = templates.size();
		Template tmp = new Template(WhoAmI, sections);
		for (int i = 0; i < templateCount; i++)
		{
			String fileName = templates.get(i).getAsJsonObject().get("FileName").getAsString();
			String saveTo = templates.get(i).getAsJsonObject().get("SaveTo").getAsString();
			JsonObject contents = templates.get(i).getAsJsonObject().get("Contents").getAsJsonObject();
			JsonArray contentIterationTypes = null;
			if (templates.get(i).getAsJsonObject().has("Iterate"))
			{
				contentIterationTypes = templates.get(i).getAsJsonObject().get("Iterate").getAsJsonArray();
			}
			SingleTemplateFile template = parseSingleTemplate(fileName, saveTo, contents, contentIterationTypes);
			tmp.addFileToGenerate(template);
		}
		generationTemplates.add(tmp);
	}
	
	private void parseSingleTemplate(JsonObject templateMetadata)
	{
		String WhoAmI = templateMetadata.get("WhoAmI").getAsString() + " Json Type";
		JsonArray Required = templateMetadata.get("Required").getAsJsonArray();
		parseFieldSections(WhoAmI, Required);
		JsonArray Templates = templateMetadata.get("Template").getAsJsonArray();
		parseFileTemplates(WhoAmI, Templates);
	}
	
	private String getNextSortValue(ArrayList<Integer> used, String[] fromFolder, String[] known, int order)
	{
		for (int i = 0; i < fromFolder.length; i++)
		{
			if (fromFolder[i].equals(known[order]))
			{
				used.add(i);
				return fromFolder[i];
			}
		}
		return null;
	}
	
	private String[] sortBySettings(String[] fromFolder)
	{
		String[] known = Save.save.getOrderedOptionFiles();
		String[] sorted = new String[fromFolder.length];
		int ind = 0;
		
		ArrayList<Integer> used = new ArrayList<>();
		for (int order = 0; order < known.length; order++)
		{
			String next = getNextSortValue(used, fromFolder, known, order);
			if (next != null)
			{
				sorted[ind++] = next;
			}
		}
		for (int i = 0; i < fromFolder.length; i++)
		{
			if (!used.contains(i))
			{
				sorted[ind++] = fromFolder[i];
			}
		}
		return sorted;
	}
	
	public void Import()
	{
		String[] templateFileNames = sortBySettings(getTemplateFiles());
		
		for (int i = 0; i < templateFileNames.length; i++)
		{
			JsonObject data = FileHandler.readInResource(Reference.TEMPLATE_FILE(templateFileNames[i]));
			
			JsonObject[] templates = JsonHandler.readObjArrayFromElement(data.get("Options"));
			for (int j = 0; j < templates.length; j++)
			{
				parseSingleTemplate(templates[j]);
			}
		}
	}
}
