package templates.json.blockstates.stairs;

import java.util.Map;

import templates.json.blockstates.BasicBlockState;
import templates.json.blockstates.ModelInit;
import util.MapInit;

public class StairsBlockState extends BasicBlockState
{
	public StairsBlockState(String modid, String name, String stairsName)
	{
		super(new MapInit<String, Map<String, Object>>()
				.init("facing=east,half=bottom,shape=straight", new ModelInit(modid, name + stairsName).get())
				.init("facing=west,half=bottom,shape=straight", new ModelInit(modid, name + stairsName).init("y", 180).init("uvlock", true).get())
				.init("facing=south,half=bottom,shape=straight", new ModelInit(modid, name + stairsName).init("y", 90).init("uvlock", true).get())
				.init("facing=north,half=bottom,shape=straight", new ModelInit(modid, name + stairsName).init("y", 270).init("uvlock", true).get())
				.init("facing=east,half=bottom,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).get())
				.init("facing=west,half=bottom,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("y", 180).init("uvlock", true).get())
				.init("facing=south,half=bottom,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("y", 90).init("uvlock", true).get())
				.init("facing=north,half=bottom,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("y", 270).init("uvlock", true).get())
				.init("facing=east,half=bottom,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("y", 270).init("uvlock", true).get())
				.init("facing=west,half=bottom,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("y", 90).init("uvlock", true).get())
				.init("facing=south,half=bottom,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).get())
				.init("facing=north,half=bottom,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("y", 180).init("uvlock", true).get())
				.init("facing=east,half=bottom,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).get())
				.init("facing=west,half=bottom,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("y", 180).init("uvlock", true).get())
				.init("facing=south,half=bottom,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("y", 90).init("uvlock", true).get())
				.init("facing=north,half=bottom,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("y", 270).init("uvlock", true).get())
				.init("facing=east,half=bottom,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("y", 270).init("uvlock", true).get())
				.init("facing=west,half=bottom,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("y", 90).init("uvlock", true).get())
				.init("facing=south,half=bottom,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).get())
				.init("facing=north,half=bottom,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("y", 180).init("uvlock", true).get())
				.init("facing=east,half=top,shape=straight", new ModelInit(modid, name + stairsName).init("x", 180).init("uvlock", true).get())
				.init("facing=west,half=top,shape=straight", new ModelInit(modid, name + stairsName).init("x", 180).init("y", 180).init("uvlock", true).get())
				.init("facing=south,half=top,shape=straight", new ModelInit(modid, name + stairsName).init("x", 180).init("y", 90).init("uvlock", true).get())
				.init("facing=north,half=top,shape=straight", new ModelInit(modid, name + stairsName).init("x", 180).init("y", 270).init("uvlock", true).get())
				.init("facing=east,half=top,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("y", 90).init("uvlock", true).get())
				.init("facing=west,half=top,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("y", 270).init("uvlock", true).get())
				.init("facing=south,half=top,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("y", 180).init("uvlock", true).get())
				.init("facing=north,half=top,shape=outer_right", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("uvlock", true).get())
				.init("facing=east,half=top,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("uvlock", true).get())
				.init("facing=west,half=top,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("y", 180).init("uvlock", true).get())
				.init("facing=south,half=top,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("y", 90).init("uvlock", true).get())
				.init("facing=north,half=top,shape=outer_left", new ModelInit(modid, name + "outer_" + stairsName).init("x", 180).init("y", 270).init("uvlock", true).get())
				.init("facing=east,half=top,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("y", 90).init("uvlock", true).get())
				.init("facing=west,half=top,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("y", 270).init("uvlock", true).get())
				.init("facing=south,half=top,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("y", 180).init("uvlock", true).get())
				.init("facing=north,half=top,shape=inner_right", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("uvlock", true).get())
				.init("facing=east,half=top,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("uvlock", true).get())
				.init("facing=west,half=top,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("y", 180).init("uvlock", true).get())
				.init("facing=south,half=top,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("y", 90).init("uvlock", true).get())
				.init("facing=north,half=top,shape=inner_left", new ModelInit(modid, name + "inner_" + stairsName).init("x", 180).init("y", 270).init("uvlock", true).get()).get()
				);
	}
}
