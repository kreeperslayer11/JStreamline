package util;

import java.io.File;

public class Language
{
	public static final Language lang = new Language();
	
	private String[] langs;
	
	public Language()
	{
		this.langs = FileHandler.filesInDirectory(new File(Reference.LANG_FOLD));
    	for (int i = 0; i < langs.length; i++)
    	{
    		int begin = "lang_".length();
    		int end = langs[i].length() - ".json".length();
    		langs[i] = langs[i].substring(begin, end);
    	}
	}
	
	public boolean isReal(String language)
	{
		for (int i = 0; i < langs.length; i++)
		{
			if (langs[i].equals(language))
			{
				return true;
			}
		}
		return false;
	}
	
	public String[] getLangs()
	{
		String[] tmp = new String[langs.length];
		for (int i = 0; i < langs.length; i++)
		{
			tmp[i] = langs[i];
		}
		return tmp;
	}
}
