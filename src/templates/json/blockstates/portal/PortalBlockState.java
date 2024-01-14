package templates.json.blockstates.portal;

import java.util.Map;

import templates.json.blockstates.BasicBlockState;
import templates.json.blockstates.ModelInit;
import util.MapInit;

public class PortalBlockState extends BasicBlockState
{
	public PortalBlockState(String modid, String name, String ns, String ew)
	{
		super(new MapInit<String, Map<String, Object>>()
				.init("axis=z", new ModelInit(modid, name + ew).get())
				.init("axis=x", new ModelInit(modid, name + ns).get())
				.init("inventory", new ModelInit(modid, name + ns).get())
				.get());
	}
}
