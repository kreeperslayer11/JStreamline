package templates.json.blockstates;

public class NormalVariant implements IVariant
{
	String model;
	
	public NormalVariant(String modid, String texture)
	{
		this.model = modid + ":" + texture;
	}
}
