package templates.json.models;

import templates.inner.ITextures;
import templates.json.JsonSerializable;
import util.json.generator.JsonType;

public abstract class Model extends JsonSerializable
{
	String parent;
	ITextures textures;
	
	public Model(JsonType type, ITextures textures)
	{
		this.parent = type.parent;
		this.textures = textures;
	}
}
