package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import templates.json.JsonSerializable;
import templates.json.blockstates.BasicBlockState;
import templates.json.blockstates.flame.FlameBlockState;
import templates.json.blockstates.glass.PaneBlockState;
import templates.json.blockstates.portal.PortalBlockState;
import templates.json.blockstates.stairs.StairsBlockState;
import templates.json.models.Armor;
import templates.json.models.BasicItem;
import templates.json.models.BlockBlock;
import templates.json.models.BlockItem;
import templates.json.models.FlameBlock;
import templates.json.models.Tool;
import templates.json.models.multipart.flame.FacingDir;
import templates.json.models.multipart.flame.FlameDef;
import templates.json.models.multipart.portal.PortalDef;
import util.MapInit;
import util.json.generator.JsonType;

public class JsonPreview 
{
	private JsonType type;
	private String name;
	private String textureTop;
	private String textureBottom;
	private String textureSide;
	private String texture;
	private String modid;
	private ArrayList<String> options = new ArrayList<>();
	private ArrayList<String> files = new ArrayList<>();
	
	/**
	 * 
	 * @param type kind of json file this is
	 * @param name name of the file without .json
	 * @param texture the name of the texture refered to in the file
	 * @param modid mod id of the mod this is being generated for
	 * @param spacesPerIndent
	 */
	public JsonPreview(JsonType type, String name, String texture, String top, String bottom, String side, String modid)
	{
		this.options.add("");
		this.type = type;
		this.name = name;
		this.texture = texture;
		this.textureTop = top;
		this.textureBottom = bottom;
		this.textureSide = side;
		this.modid = modid;
	}
	
	public JsonPreview replaceOptions(String option)
	{
		this.options.clear();
		this.options.add(option);
		return this;
	}
	
	/**
	 * 
	 * @param options list of textures for use with armor and tool set generation
	 */
	public JsonPreview replaceOptions(List<String> options)
	{
		this.options.clear();
		this.options.addAll(options);
		return this;
	}
	
	public String getFile(String option)
	{
		int index = options.indexOf(option);
		if (index == -1)
		{
			return "";
		}
		return getFiles()[index];
	}
	
	/**
	 * get the text for all the files (for example: all tool types for given set)
	 * @return
	 */
	public String[] getFiles()
	{
		String[] ret;
		if (files.isEmpty())
		{
			switch( type )
			{
				case BASIC:
					files.add(buildFileBasic());
					break;
				case TOOL_SET:
					for(int i = 0; i < options.size(); i++)
					{
						files.add(buildFileTool(options.get(i)));
					}
					break;
				case ARMOR_SET:
					for(int i = 0; i < options.size(); i++)
					{
						files.add(buildFileArmor(options.get(i)));
					}
					break;
				case BLOCK_ITEM:
					files.add(buildFileBlockItem(options.get(0)));
					break;
				case BLOCK_STATE:
					files.add(buildFileBlockState());
					break;
				case BLOCK:
					files.add(buildFileBlock());
					break;
				case FLAME_STATE:
					files.add(buildFileFlameBlockState());
					break;
				case FLAME:
					ret = new String[options.size()];
					int f = 0;
					f = buildFlameBlocks(2, f, ret, FacingDir.down);
					f = buildFlameBlocks(2, f, ret, FacingDir.side);
					f = buildFlameBlocks(2, f, ret, FacingDir.side_alt);
					f = buildFlameBlocks(2, f, ret, FacingDir.up);
					f = buildFlameBlocks(2, f, ret, FacingDir.up_alt);
					files.addAll(Arrays.asList(ret));
					break;
				case PANE_BLOCK:
					files.add(buildFileGlassBlock(FacingDir.noside));
					files.add(buildFileGlassBlock(FacingDir.noside_alt));
					files.add(buildFileGlassBlock(FacingDir.post));
					files.add(buildFileGlassBlock(FacingDir.side));
					files.add(buildFileGlassBlock(FacingDir.side_alt));
					break;
				case PANE_STATE:
					files.add(buildFileGlassState());
					break;
				case PANE:
					files.add(buildFileGlassItem());
					break;
				case PORTAL_BLOCK:
					files.add(buildFilePortalBlockDef(true));
					files.add(buildFilePortalBlockDef(false));
					break;
				case PORTAL_STATE:
					files.add(buildFilePortalState());
					break;
				case STAIRS_BLOCK:
				case STAIRS_INNER_BLOCK:
				case STAIRS_OUTER_BLOCK:
					options.clear();
					options.add(type.parent.replace("block/", ""));
					files.add(buildFileStairsBlock(type));
					break;
				case STAIRS_STATE:
					options.clear();
					options.add(type.parent);
					files.add(buildFileStairsState(type.parent));
					break;
				default:
					files.add("");
					break;
			}
		}
		
		return files.toArray(new String[files.size()]);
	}
	
	private int buildFlameBlocks(int num, int current, String[] list, FacingDir dir)
	{
		list[current++] = buildFileFlameBlockDef(dir);
		for (int i = 0; i < num; i++)
		{
			list[current++] = buildFileFlameBlock(dir, i);
		}
		return current;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFileName(String option)
	{
		return name + option + ".json";
	}
	
	public String getFileName(int optionIndex)
	{
		return getFileName(options.get(optionIndex));
	}
	
	public ArrayList<String> getOptions()
	{
		return options;
	}
	
	public void remove(String option)
	{
		int index = options.indexOf(option);
		if (index == -1)
		{
			return;
		}
		remove(index);
	}
	
	public void remove(int option)
	{
		files.remove(option);
		options.remove(option);
	}
	
	public JsonType getType()
	{
		return type;
	}
	
	private String build(JsonSerializable item)
	{
		return item.serialize();
	}
	
	private String buildFileBasic()
	{
		return build(new BasicItem(this.modid, this.texture));
	}
	
	private String buildFileTool(String option)
	{
		return build(new Tool(this.modid, this.texture, option));
	}
	
	private String buildFileArmor(String option)
	{
		return build(new Armor(this.modid, this.texture, option));
	}
	
	private String buildFileBlockItem(String option)
	{
		return build(new BlockItem(this.modid, this.name, option));
	}
	
	private String buildFileBlockState()
	{
		return build(new BasicBlockState(this.modid, this.name));
	}
	
	private String buildFileBlock()
	{
		return build(new BlockBlock(this.modid, this.texture));
	}
	
	private String buildFileFlameBlockState()
	{
		return build(new FlameBlockState(this.modid, this.name));
	}
	
	private String buildFileFlameBlock(FacingDir dir, int layer)
	{
		return build(new FlameBlock(this.modid, this.name, this.texture, dir, layer));
	}
	
	private String buildFileFlameBlockDef(FacingDir dir)
	{
		return build(new FlameDef(dir));
	}
	
	private String buildFileGlassBlock(FacingDir dir)
	{
		MapInit<String, String> textures = new MapInit<String, String>()
			.init("pane", this.modid + ":blocks/" + this.texture);
		if (dir != FacingDir.noside && dir != FacingDir.noside_alt)
		{
			textures.init("edge", this.modid + ":blocks/" + this.textureSide);
		}
		return build(new BlockBlock(JsonType.PANE_BLOCK, dir, textures.get()));
	}
	
	private String buildFileGlassState()
	{
		return build(new PaneBlockState(this.modid, this.name));
	}
	
	private String buildFileGlassItem()
	{
		return build(new BasicItem(this.modid, this.texture, "blocks"));
	}
	
	private String buildFilePortalBlockDef(boolean NSTrue_EWFalse)
	{
		return build(new PortalDef(this.modid, this.texture, NSTrue_EWFalse));
	}
	
	private String buildFilePortalState()
	{
		String built = build(new PortalBlockState(this.modid, this.name, getOptions().get(0), getOptions().get(1)));
		options.clear();
		options.add("");
		return built;
	}
	
	private String buildFileStairsBlock(JsonType type)
	{
		return build(new BlockBlock(type, modid, textureTop, textureBottom, textureSide));
	}
	
	private String buildFileStairsState(String option)
	{
		return build(new StairsBlockState(this.modid, this.name, option));
	}
}
