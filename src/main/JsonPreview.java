package main;

import java.util.ArrayList;

import templates.json.datareading.SingleTemplateFile;
import templates.json.datareading.Template;

public class JsonPreview 
{
	private Template template;
	private ArrayList<SingleTemplateFile> templateFiles;
	
	public JsonPreview(Template who)
	{
		this.template = who;
	}
	
	private int findForOption(String option)
	{
		ArrayList<SingleTemplateFile> templates = getFiles();
		for (int i = 0; i < templates.size(); i++)
		{
			if (templates.get(i).getFileNameWithPath().equals(option))
			{
				return i;
			}
		}
		return -1;
	}
	
	public String getFile(int which)
	{
		return getFiles().get(which).getContents();
	}
	
	public String getFile(String which)
	{
		int index = findForOption(which);
		if (index != -1)
		{
			return getFile(index);
		}
		return "";
	}
	
	/**
	 * get the text for all the files (for example: all tool types for given set)
	 * @return
	 */
	public ArrayList<SingleTemplateFile> getFiles()
	{
		if (templateFiles == null)
		{
			templateFiles = new ArrayList<>();
			SingleTemplateFile[] tmp = template.FillContent();
			for (int i = 0; i < tmp.length; i++)
			{
				templateFiles.add(tmp[i]);				
			}
		}
		return templateFiles;
	}
	
	public String getFileName(int id)
	{
		return getFiles().get(id).getFileName();
	}
	
	public String getFileName(String id)
	{
		int index = findForOption(id);
		if (index != -1)
		{
			return getFileName(index);
		}
		return "";
	}
	
	public String getFileNameWithPath(int id)
	{
		return getFiles().get(id).getFileNameWithPath();
	}
	
	public int size()
	{
		return getFiles().size();
	}
	
	public void remove(int option)
	{
		getFiles().remove(option);
	}
	
	public void remove(String option)
	{
		int index = findForOption(option);
		if (index != -1)
		{
			remove(index);
		}
	}
	
	public String getFolder(int option)
	{
		return getFiles().get(option).getFolder();
	}
	
	public String getFolder(String option)
	{
		int index = findForOption(option);
		if (index != -1)
		{
			return getFolder(index);
		}
		return "";
	}
}
