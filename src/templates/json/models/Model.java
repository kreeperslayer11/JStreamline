package templates.json.models;

import java.util.Map;

import templates.json.JsonSerializable;
import templates.json.models.multipart.flame.FacingDir;
import util.MapInit;
import util.json.generator.JsonType;

public abstract class Model extends JsonSerializable
{
	String parent;
	Map<String, String> textures;
	
	public Model(JsonType type, String textureKey, String textureValue)
	{
		this(type, new MapInit<String, String>().init(textureKey, textureValue).get());
	}
	
	public Model(JsonType type, Map<String, String> textures)
	{
		this.parent = type.parent;
		this.textures = textures;
	}
	
	public Model(JsonType type, String modid, String name, FacingDir dir, Map<String, String> textures)
	{
		this.parent = modid + ":" + type.parent + name + dir.postfix;
		this.textures = textures;
	}
	
	public Model(JsonType type, FacingDir dir, Map<String, String> textures)
	{
		this.parent = type.parent + dir.postfix;
		this.textures = textures;
	}
}
