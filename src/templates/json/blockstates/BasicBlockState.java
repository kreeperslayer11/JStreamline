package templates.json.blockstates;

import templates.json.JsonSerializable;

public class BasicBlockState extends JsonSerializable
{
	StateVariants variants;
	
	public BasicBlockState(String modid, String texture)
	{
		variants = new StateVariants(new NormalVariant(modid, texture));
	}
}
