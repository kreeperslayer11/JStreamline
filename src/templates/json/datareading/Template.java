package templates.json.datareading;

import java.util.ArrayList;

import templates.json.datareading.options.Field;
import templates.json.datareading.options.FieldSection;

public class Template 
{
	private String WhoAmI;
	private ArrayList<FieldSection> sections;
	private ArrayList<SingleTemplateFile> templates;
	
	public Template(String WhoAmI, ArrayList<FieldSection> fields)
	{
		this.WhoAmI = WhoAmI;
		this.sections = fields;
		templates = new ArrayList<>();
	}
	
	public void addFileToGenerate(SingleTemplateFile template)
	{
		this.templates.add(template);
	}
	
	public SingleTemplateFile[] FillContent()
	{
		ArrayList<SingleTemplateFile> generated = new ArrayList<>();
		for (int i = 0; i < templates.size(); i++)
		{
			SingleTemplateFile generating = templates.get(i).duplicate();
			ArrayList<Field> iterationFields = new ArrayList<>();
			for (int j = 0; j < sections.size(); j++)
			{
				while(sections.get(j).HasNextField())
				{
					Field field = sections.get(j).getNextField();
					if (field.iterationField())
					{
						iterationFields.add(field);
					}
					else
					{
						generating.Fill(field);						
					}
				}
			}
			
			if (iterationFields.size() > 0)
			{
				ArrayList<SingleTemplateFile> iterationGeneration = new ArrayList<>();
				for (int iter = 0; iter < iterationFields.size(); iter++)
				{
					Field field = iterationFields.get(iter);
					int iterations = 1;
					String currentContent = field.getCurrentFiller();
					String iterId = field.getID();
					if (currentContent.matches("-?(0|[1-9]\\d*)"))
					{
						iterations = Integer.parseInt(currentContent);
					}
					if (generating.doesFieldIterateOnThisFile(field))
					{
						for (int q = 0; q < iterations; q++)
						{
							SingleTemplateFile iterGen = generating.duplicateFromIterateField(iterId, "" + q);
							if (!generating.doesFieldIterateOnContent(field))
							{
								iterGen.Fill(field, false, -1, "" + q);
							}
							else
							{
								iterGen.Fill(field, true, iterations, "");
							}
							iterationGeneration.add(iterGen);								
						}
					}
					else if (generating.doesFieldIterateOnContent(field))
					{
						SingleTemplateFile contentIterGen = generating.duplicate();
						contentIterGen.Fill(field, true, iterations, "");
						iterationGeneration.add(contentIterGen);
					}
					else
					{
						generated.add(generating);
					}
				}
				generated.addAll(iterationGeneration);
			}
			else
			{
				generated.add(generating);
			}
		}
		SingleTemplateFile[] gened = new SingleTemplateFile[generated.size()];
		for (int i = 0; i < gened.length; i++)
		{
			gened[i] = generated.get(i);
		}
		return gened;
	}
	
	public String whoAmI()
	{
		return WhoAmI;
	}
}
