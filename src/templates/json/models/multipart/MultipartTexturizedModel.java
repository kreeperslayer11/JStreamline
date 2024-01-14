package templates.json.models.multipart;

import java.util.Map;

import templates.json.JsonSerializable;
import util.MapInit;

public class MultipartTexturizedModel extends JsonSerializable
{
	Map<String, String> textures;
	IElement[] elements;
	
	public MultipartTexturizedModel(String modid, String texture, String mapping, IElement[] elements)
	{
		textures = new MapInit<String, String>().init("particle", modid + ":blocks/" + texture).init(mapping, modid + ":blocks/" + texture).get();
		this.elements = elements;
	}
}
