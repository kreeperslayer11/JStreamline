package templates.json.models.multipart.portal;

import templates.json.models.multipart.Face;
import templates.json.models.multipart.IElement;
import templates.json.models.multipart.IFace;
import templates.json.models.multipart.MultipartTexturizedModel;
import templates.json.models.multipart.SlimElement;
import util.MapInit;

public class PortalDef extends MultipartTexturizedModel
{
	public PortalDef(String modid, String texture, boolean NSTrue_EWFalse)
	{
		super(modid, texture, "portal", new IElement[] {
				NSTrue_EWFalse ? new SlimElement( new float[] { 0, 0, 6 }, new float[] { 16, 16, 10 },
						new MapInit<String, IFace>().init("north", new Face(new int[] { 0, 0, 16, 16 }, "portal")).init("south", new Face(new int[] { 0, 0, 16, 16 }, "portal")).get())
						: new SlimElement(new float[] { 6, 0, 0 }, new float[] { 10, 16, 16 },
								new MapInit<String, IFace>().init("east", new Face(new int[] { 0, 0, 16, 16 }, "portal")).init("west", new Face(new int[] { 0, 0, 16, 16 }, "portal")).get())
		});
	}
}
