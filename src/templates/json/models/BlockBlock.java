package templates.json.models;

import templates.inner.BlockTextures;
import util.JsonType;

public class BlockBlock extends Model
{
	public BlockBlock(String modid, String texture)
	{
		super(JsonType.BLOCK, new BlockTextures(modid + ":blocks/" + texture));
	}
}
