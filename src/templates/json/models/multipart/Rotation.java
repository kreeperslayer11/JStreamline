package templates.json.models.multipart;

public class Rotation
{
	int[] origin;
	String axis;
	float angle;
	boolean rescale;
	
	public Rotation(int[] origin, String axis, float angle, boolean rescale)
	{
		this.origin = origin;
		this.axis = axis;
		this.angle = angle;
		this.rescale = rescale;
	}
}
