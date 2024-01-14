package templates.json.models.multipart;

import java.util.Map;

import util.MapInit;

public class Element implements IElement
{
	float[] from;
	float[] to;
	Rotation rotation;
	boolean shade;
	Map<String, IFace> faces;
	
	public Element(float[] from, float[] to, Rotation rotation, boolean shade, String facing, IFace face)
	{
		this(from, to, rotation, shade, new MapInit<String, IFace>().init(facing, face).get());
	}
	
	public Element(float[] from, float[] to, Rotation rotation, boolean shade, Map<String, IFace> faces)
	{
		this.from = from;
		this.to = to;
		this.rotation = rotation;
		this.shade = shade;
		this.faces = faces;
	}
}
