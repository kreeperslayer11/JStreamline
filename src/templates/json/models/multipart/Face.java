package templates.json.models.multipart;

public class Face implements IFace
{
	int[] uv;
	String texture;
	
	public Face(int a, int b, int c, int d, String textureMapping)
	{
		this(new int[] { a, b, c, d }, textureMapping);
	}
	
	public Face(int[] uv, String textureMapping)
	{
		this.uv = uv;
		this.texture = "#" + textureMapping;
	}
}
