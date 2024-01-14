package templates.json.models.multipart.flame;

import templates.json.models.multipart.Element;
import templates.json.models.multipart.Face;
import templates.json.models.multipart.FaceRot;
import templates.json.models.multipart.IFace;
import templates.json.models.multipart.MultipartModel;
import templates.json.models.multipart.Rotation;
import util.MapInit;

public class FlameDef extends MultipartModel
{
	public static Element[] builder(FacingDir dir)
	{
		int[] origin;
		IFace face;
		int[] uv = new int[] { 0, 0, 16, 16 };
		String texMap = "flame";
		float angle = 22.5f;
		switch (dir)
		{
			case down:
				origin = new int[] { 8, 8, 8 };
				float zx = 8.8f, xz = 7.2f, y = 22.4f, alt = 16f;
				face = new Face(uv, texMap);
				return new Element[] {
						new Element(new float[] { 0, 0, zx }, new float[] { alt, y, zx}, new Rotation(origin, "x", angle * -1, true), false, "south", face),
						new Element(new float[] { 0, 0, xz }, new float[] { alt, y, xz}, new Rotation(origin, "x", angle, true), false, "north", face),
						new Element(new float[] { zx, 0, 0 }, new float[] { zx, y, alt}, new Rotation(origin, "x", angle * -1, true), false, "west", face),
						new Element(new float[] { xz, 0, 0 }, new float[] { xz, y, alt}, new Rotation(origin, "x", angle, true), false, "east", face)
				};
			case up:
				return new Element[] {
						new Element(new float[] { 0, 16, 0 }, new float[] { 16, 16, 16 }, new Rotation(new int[] {16, 16, 8}, "z", angle, true), false, "down", new FaceRot(uv, texMap, 270)),
						new Element(new float[] { 0, 16, 0 }, new float[] { 16, 16, 16 }, new Rotation(new int[] {0, 16, 8}, "z", angle * -1, true), false, "down", new FaceRot(uv, texMap, 90))
				};
			case up_alt:
				return new Element[] {
						new Element(new float[] { 0, 16, 0 }, new float[] { 16, 16, 16 }, new Rotation(new int[] {8, 16, 16}, "x", angle * -1, true), false, "down", new FaceRot(uv, texMap, 180)),
						new Element(new float[] { 0, 16, 0 }, new float[] { 16, 16, 16 }, new Rotation(new int[] {8, 16, 0}, "x", angle, true), false, "down", new Face(uv, texMap))
				};
			case side:
				return new Element[]{
						new Element(new float[] { 0, 0, 0.01f }, new float[] { 16, 22.4f, 0.01f }, null, false, new MapInit<String, IFace>().init("south", new Face(uv, texMap)).init("north", new Face(uv, texMap)).get())
				};
			case side_alt:
				return new Element[]{
						new Element(new float[] { 0, 0, 0.01f }, new float[] { 16, 22.4f, 0.01f }, null, false, new MapInit<String, IFace>().init("south", new Face(new int[] { 16, 0, 0, 16 }, texMap)).init("north", new Face(new int[] { 16, 0, 0, 16 }, texMap)).get())
				};
			default:
				return new Element[0];
		}
		
	}
	
	public FlameDef(FacingDir facing)
	{
		super(false, builder(facing));
	}
}
