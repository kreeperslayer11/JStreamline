package templates.json.models;

import templates.json.models.multipart.flame.FacingDir;
import util.MapInit;
import util.json.generator.JsonType;

public class FlameBlock extends Model
{
	public FlameBlock(String modid, String name, String texture, FacingDir dir, int layer)
	{
		super(JsonType.FLAME, modid, name, dir, new MapInit<String, String>()
				.init("particle", modid + ":blocks/" + texture + "_layer_" + layer)
				.init("flame", modid + ":blocks/" + texture + "_layer_" + layer).get());
	}
}
