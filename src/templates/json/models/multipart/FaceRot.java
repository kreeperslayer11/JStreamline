package templates.json.models.multipart;

public class FaceRot extends Face
{
	int rotation;
	
	public FaceRot(int[] uv, String textureMapping, int rotation)
	{
		super(uv, textureMapping);
		this.rotation = rotation;
	}
}
