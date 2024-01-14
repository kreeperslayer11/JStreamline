package templates.json.models.multipart;

import templates.json.JsonSerializable;

public abstract class MultipartModel extends JsonSerializable
{
	boolean ambientocclusion;
	IElement[] elements;
	
	public MultipartModel(boolean ambientocclusion, IElement[] elements)
	{
		this.ambientocclusion = ambientocclusion;
		this.elements = elements;
	}
}
