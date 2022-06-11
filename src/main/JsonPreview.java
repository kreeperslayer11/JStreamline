package main;

import java.util.ArrayList;

import util.JsonType;
import util.Localization;

public class JsonPreview 
{
	private JsonType type;
	private String name;
	private String texture;
	private String modid;
	private int spacesPerIndent;
	private ArrayList<String> options = new ArrayList<>();
	private Localization local;
	
	/**
	 * 
	 * @param type kind of json file this is
	 */
	JsonPreview(Localization local, JsonType type, int spacesPerIndent)
	{
		this.local = local;
		this.options.add("");
		this.type = type;
		this.spacesPerIndent = spacesPerIndent;
	}
	
	/**
	 * 
	 * @param type kind of json file this is
	 * @param name name of the file without .json
	 * @param texture the name of the texture refered to in the file
	 * @param modid mod id of the mod this is being generated for
	 * @param spacesPerIndent
	 */
	JsonPreview(Localization local, JsonType type, String name, String texture, String modid, int spacesPerIndent)
	{
		this.local = local;
		this.options.add("");
		this.type = type;
		this.name = name;
		this.texture = texture;
		this.modid = modid;
		this.spacesPerIndent = spacesPerIndent;
	}
	
	/**
	 * 
	 * @param name name of the file without .json
	 * @param texture the name of the texture refered to in the file
	 * @param modid mod id of the mod this is being generated for
	 * @param spacesPerIndent
	 */
	public JsonPreview add(String name, String texture, String modid)
	{
		this.options.add("");
		this.name = name;
		this.texture = texture;
		this.modid = modid;
		return this;
	}
	
	/**
	 * 
	 * @param options list of textures for use with armor and tool set generation
	 */
	public JsonPreview add(ArrayList<String> options)
	{
		this.options = new ArrayList<String>();
		this.options.addAll(options);
		return this;
	}
	
	/**
	 * get the text of a file 
	 * @param option used for armor and tool sets for textures of multiple json files
	 * @return
	 */
	public String getFile(String option)
	{
		switch( type )
		{
			case BASIC:
				return buildFileBasic();
			case TOOL_SET:
				return buildFileTool(option);
			case ARMOR_SET:
				return buildFileArmor(option);
			case BLOCK_ITEM:
				return buildFileBlockItem();
			case BLOCK_STATE:
				return buildFileBlockState();
			case BLOCK:
				return buildFileBlock();
			default:
				return "";
		}	
	}
	
	/**
	 * get the text for all the files (for example: all tool types for given set)
	 * @return
	 */
	public String[] getFiles()
	{
		String[] ret;
		switch( type )
		{
			case BASIC:
				ret = new String[1];
				ret[0] = buildFileBasic();
				return ret;
			case TOOL_SET:
				ret = new String[options.size()];
				for(int i = 0; i < options.size(); i++)
				{
					ret[i] = buildFileTool(options.get(i));
				}
				return ret;
			case ARMOR_SET:
				ret = new String[options.size()];
				for(int i = 0; i < options.size(); i++)
				{
					ret[i] = buildFileArmor(options.get(i));
				}
				return ret;
			case BLOCK_ITEM:
				ret = new String[1];
				ret[0] = buildFileBlockItem();
				return ret;
			case BLOCK_STATE:
				ret = new String[1];
				ret[0] = buildFileBlockState();
				return ret;
			case BLOCK:
				ret = new String[1];
				ret[0] = buildFileBlock();
				return ret;
			default:
				return null;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<String> getOptions()
	{
		return options;
	}
	
	public JsonType getType()
	{
		return type;
	}
	
	/**
	 * create a new line with x indentations (spacesPerIndent spaces each)
	 * @param x
	 * @return
	 */
	private String newLine(int x)
	{
		String ret = "\n";
		
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < this.spacesPerIndent; j++)
			{
				ret = ret + " ";
			}
		}
		return ret;
	}
	
	private String buildFileBasic()
	{
		/*return "{" + newLine(1) + 
					"\"parent\": \"" + JsonType.BASIC.getParent() + "\"," + newLine(1) + 
					"\"textures\": {" + newLine(2) +
					"\"layer0\": \"" + this.modid + ":items/" + this.texture + "\"" + newLine(1) +
					"}" + newLine(0) +
					"}";*/
		return local.getJBasic(0) + newLine(1) +
					local.getJBasic(1) + JsonType.BASIC.getParent() + local.getJBasic(2) + newLine(1) +
					local.getJBasic(3) + newLine(2) +
					local.getJBasic(4) + this.modid + local.getJBasic(5) + this.texture + local.getJBasic(6) + newLine(1) +
					local.getJBasic(7) + newLine(0) +
					local.getJBasic(8);
	}
	
	private String buildFileTool(String option)
	{
		/*return "{" + newLine(1) + 
					"\"parent\": \"" + JsonType.TOOL_SET.getParent() + "\"," + newLine(1) + 
					"\"textures\": {" + newLine(2) +
					"\"layer0\": \"" + this.modid + ":items/" + this.texture + option + "\"" + newLine(1) +
					"}" + newLine(0) +
					"}";*/
		return local.getJTools(0) + newLine(1) +
				local.getJTools(1) + JsonType.TOOL_SET.getParent() + local.getJTools(2) + newLine(1) +
				local.getJTools(3) + newLine(2) +
				local.getJTools(4) + this.modid + local.getJTools(5) + this.texture + option + local.getJTools(6) + newLine(1) +
				local.getJTools(7) + newLine(0) +
				local.getJTools(8);
	}
	
	private String buildFileArmor(String option)
	{
		/*return "{" + newLine(1) + 
					"\"parent\": \"" + JsonType.ARMOR_SET.getParent() + "\"," + newLine(1) + 
					"\"textures\": {" + newLine(2) +
					"\"layer0\": \"" + this.modid + ":items/" + this.texture + option + "\"" + newLine(1) +
					"}" + newLine(0) +
					"}";*/
		return local.getJArmor(0) + newLine(1) +
				local.getJArmor(1) + JsonType.ARMOR_SET.getParent() + local.getJArmor(2) + newLine(1) +
				local.getJArmor(3) + newLine(2) +
				local.getJArmor(4) + this.modid + local.getJArmor(5) + this.texture + option + local.getJArmor(6) + newLine(1) +
				local.getJArmor(7) + newLine(0) +
				local.getJArmor(8);
	}
	
	private String buildFileBlockItem()
	{
		/*return "{" + newLine(1) +
					"\"parent\": \"" + this.modid + ":" + JsonType.BLOCK_ITEM.getParent() + 
						this.texture + "\"" + newLine(0) +
					"}";*/
		return local.getJBItem(0) + newLine(1) +
				local.getJBItem(1) + this.modid + local.getJBItem(2) + JsonType.BLOCK_ITEM.getParent() +
				this.texture + local.getJBItem(3) + newLine(0) +
				local.getJBItem(4);
	}
	
	private String buildFileBlockState()
	{
		/*return "{" + newLine(1) +
					"\"variants\": {" + newLine(2) +
					"\"normal\": { \"model\": \"" + this.modid + ":" + this.texture + "\" }" + newLine(1) +
					"}" + newLine(0) +
					"}";*/
		return local.getJBstate(0) + newLine(1) +
					local.getJBstate(1) + newLine(2) +
					local.getJBstate(2) + this.modid + local.getJBstate(3) + this.texture + local.getJBstate(4) + newLine(1) +
					local.getJBstate(5) + newLine(0) +
					local.getJBstate(6);
	}
	
	private String buildFileBlock()
	{
		/*return "{" + newLine(1) + 
					"\"parent\": \"" + JsonType.BLOCK.getParent() + "\"," + newLine(1) +
					"\"textures\": {" + newLine(2) +
					"\"all\": \"" + this.modid + ":blocks/" + this.texture + "\"" + newLine(1) +
					"}" + newLine(0) +
					"}";*/
		return local.getJBlock(0) + newLine(1) +
				local.getJBlock(1) + JsonType.BLOCK.getParent() + local.getJBlock(2) + newLine(1) +
				local.getJBlock(3) + newLine(2) +
				local.getJBlock(4) + this.modid + local.getJBlock(5) + this.texture + local.getJBlock(6) + newLine(1) +
				local.getJBlock(7) + newLine(0) +
				local.getJBlock(8);
	}
}
