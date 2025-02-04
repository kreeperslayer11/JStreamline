package util;

public class Reference 
{
	public static final String RESOURCES_FOLD = "resources";
	public static final String LANG_FOLD = RESOURCES_FOLD + "/lang";
	public static final String TEMPLATE_FOLD = RESOURCES_FOLD + "/Templates";
	
	public static final String DATA_FILE = RESOURCES_FOLD + "/settings.json";
	public static String LANG_FILE(String languageName)
	{
		return LANG_FOLD + "/lang_" + (Language.lang.isReal(languageName) ? languageName : "") + ".json";
	}
	public static String TEMPLATE_FILE(String fileName)
	{
		return TEMPLATE_FOLD + "/" + fileName + ".json";
	}
	public static final String ICON_FILE = RESOURCES_FOLD + "/JStreamlineIcon.png";
	
	public static final String ITEM_FOLD = "models\\item";
	public static final String BLCK_FOLD = "models\\block";
	public static final String B_STATE_FOLD = "blockstates";
}
