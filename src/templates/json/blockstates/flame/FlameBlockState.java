package templates.json.blockstates.flame;

import java.util.List;
import java.util.Map;

import templates.json.blockstates.multipart.IMultipartPortion;
import templates.json.blockstates.multipart.MultipartBlockState;
import templates.json.blockstates.multipart.MultipartLogicPortion;
import templates.json.blockstates.multipart.MultipartPortion;
import util.MapInit;
import util.MapList;
import templates.json.blockstates.ModelInit;
import templates.json.blockstates.ModelList;

public class FlameBlockState extends MultipartBlockState
{
	private static IMultipartPortion gen(String modid, String name, int y, boolean by, String side)
	{
		return new MultipartLogicPortion(
				new MapInit<String, List<Map<String, Boolean>>>()
				.init("OR", new MapList<String, Boolean>(2)
					.init(new MapInit<String, Boolean>()
						.init(side, true)
						.get())
					.init(new MapInit<String, Boolean>()
						.init("north", false)
						.init("east", false)
						.init("south", false)
						.init("west", false)
						.init("up", false)
						.get())
					.get())
				.get(),
			
			new ModelList(4)
				.init(by ? new ModelInit(modid, name + "_side0").init("y", y).get() : new ModelInit(modid, name + "_side0").get())
				.init(by ? new ModelInit(modid, name + "_side1").init("y", y).get() : new  ModelInit(modid, name + "_side1").get())
				.init(by ? new ModelInit(modid, name + "_side_alt0").init("y", y).get()  : new ModelInit(modid, name + "_side_alt0").get())
				.init(by ? new ModelInit(modid, name + "_side_alt1").init("y", y).get()  : new ModelInit(modid, name + "_side_alt1").get())
		);
	}
	
	public FlameBlockState(String modid, String texture)
	{
		super(new IMultipartPortion[]{
			new MultipartPortion(
				new MapInit<String, Boolean>()
					.init("north", false)
					.init("east", false)
					.init("south", false)
					.init("west", false)
					.init("up", false)
					.get(), 
				new ModelList(2)
					.init(new ModelInit(modid, texture + "_floor0").get())
					.init(new ModelInit(modid, texture + "_floor1").get())
			),
			gen(modid, texture, 0, false, "north"),
			gen(modid, texture, 90, true, "east"),
			gen(modid, texture, 180, true, "south"),
			gen(modid, texture, 270, true, "west"),
			new MultipartPortion(
				new MapInit<String, Boolean>()
					.init("up", true).get(),
				new ModelList(4)
					.init(new ModelInit(modid, texture + "_up0").get())
					.init(new ModelInit(modid, texture + "_up1").get())
					.init(new ModelInit(modid, texture + "_up_alt0").get())
					.init(new ModelInit(modid, texture + "_up_alt1").get())
			)
		},
		new ModelInit(modid, texture + "_side0"));
	}
}
