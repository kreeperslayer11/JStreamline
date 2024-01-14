package templates.json.models;

import java.util.Map;

import templates.json.models.multipart.flame.FacingDir;
import util.MapInit;
import util.json.generator.JsonType;

public class BlockBlock extends Model
{
	public BlockBlock(String modid, String texture)
	{
		this(JsonType.BLOCK, modid, texture);
	}
	
	public BlockBlock(JsonType type, String modid, String texture)
	{
		super(JsonType.BLOCK, "all", modid + ":blocks/" + texture);
	}
	
	public BlockBlock(JsonType type, Map<String, String> textures)
	{
		super(type, textures);
	}
	
	public BlockBlock(JsonType type, String modid, String textureTop, String textureBottom, String textureSide)
	{
		super(type, new MapInit<String, String>()
				.init("bottom", modid + ":blocks/" + textureBottom)
				.init("top", modid + ":blocks/" + textureTop)
				.init("side", modid + ":blocks/" + textureSide).get());
	}
	
	public BlockBlock(JsonType type, FacingDir dir, Map<String, String> textures)
	{
		super(type, dir, textures);
	}
}
