package templates.json.blockstates.glass;

import templates.json.blockstates.ModelInit;
import templates.json.blockstates.multipart.IMultipartPortion;
import templates.json.blockstates.multipart.MultipartBlockState;
import templates.json.blockstates.multipart.MultipartSingleModelPortion;

public class PaneBlockState extends MultipartBlockState
{
	public PaneBlockState(String modid, String name)
	{
		super(new IMultipartPortion[] {
			new MultipartSingleModelPortion(null, new ModelInit(modid, name + "_post")),
			new MultipartSingleModelPortion("north", true, new ModelInit(modid, name + "_side")),
			new MultipartSingleModelPortion("east", true, new ModelInit(modid, name + "_side").init("y", 90)),
			new MultipartSingleModelPortion("south", true, new ModelInit(modid, name + "_side_alt")),
			new MultipartSingleModelPortion("west", true, new ModelInit(modid, name + "_side_alt").init("y", 90)),
			new MultipartSingleModelPortion("north", false, new ModelInit(modid, name + "_noside")),
			new MultipartSingleModelPortion("east", false, new ModelInit(modid, name + "_noside_alt")),
			new MultipartSingleModelPortion("south", false, new ModelInit(modid, name + "_noside_alt").init("y", 90)),
			new MultipartSingleModelPortion("west", false, new ModelInit(modid, name + "_noside").init("y", 270))
		}, null);
	}
}
