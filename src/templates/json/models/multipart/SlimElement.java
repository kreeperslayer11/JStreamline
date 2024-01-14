package templates.json.models.multipart;

import java.util.Map;

import util.MapInit;

public class SlimElement implements IElement
{
	float[] from;
	float[] to;
	Map<String, IFace> faces;
	
	public SlimElement(float[] from, float[] to, String facing, IFace face)
	{
		this(from, to, new MapInit<String, IFace>().init(facing, face).get());
	}
	
	public SlimElement(float[] from, float[] to, Map<String, IFace> faces)
	{
		this.from = from;
		this.to = to;
		this.faces = faces;
	}
}
