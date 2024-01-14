package templates.json.blockstates.multipart;

import java.util.Map;

import templates.json.JsonSerializable;
import templates.json.blockstates.ModelInit;
import util.MapInit;

public abstract class MultipartBlockState extends JsonSerializable
{
	IMultipartPortion[] multipart;
	Map<String, Map<String, Object>> variants;
	
	public MultipartBlockState(IMultipartPortion[] multipart, ModelInit model)
	{
		this.multipart = multipart;
		this.variants = model != null ? new MapInit<String, Map<String, Object>>().init("inventory", model.get()).get() : null;
	}
}
