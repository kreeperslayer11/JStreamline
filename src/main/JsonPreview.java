package main;

import java.util.ArrayList;

import templates.json.blockstates.BasicBlockState;
import templates.json.models.Armor;
import templates.json.models.BasicItem;
import templates.json.models.BlockBlock;
import templates.json.models.BlockItem;
import templates.json.models.Tool;
import util.json.generator.JsonType;

public class JsonPreview 
{
	private JsonType type;
	private String name;
	private String texture;
	private String modid;
	private ArrayList<String> options = new ArrayList<>();
	
	/**
	 * 
	 * @param type kind of json file this is
	 * @param name name of the file without .json
	 * @param texture the name of the texture refered to in the file
	 * @param modid mod id of the mod this is being generated for
	 * @param spacesPerIndent
	 */
	public JsonPreview(JsonType type, String name, String texture, String modid)
	{
		this.options.add("");
		this.type = type;
		this.name = name;
		this.texture = texture;
		this.modid = modid;
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
	
	private String buildFileBasic()
	{
		BasicItem item = new BasicItem(this.modid, this.texture);
		return item.serialize();
	}
	
	private String buildFileTool(String option)
	{
		Tool item = new Tool(this.modid, this.texture, option);
		return item.serialize();
	}
	
	private String buildFileArmor(String option)
	{
		Armor item = new Armor(this.modid, this.texture, option);
		return item.serialize();
	}
	
	private String buildFileBlockItem()
	{
		BlockItem item = new BlockItem(this.modid, this.texture);
		return item.serialize();
	}
	
	private String buildFileBlockState()
	{
		BasicBlockState item = new BasicBlockState(this.modid, this.texture);
		return item.serialize();
	}
	
	private String buildFileBlock()
	{
		BlockBlock item = new BlockBlock(this.modid, this.texture);
		return item.serialize();
	}
}
