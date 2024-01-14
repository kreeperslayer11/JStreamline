package templates.json.blockstates.multipart;

import java.util.Map;

import templates.json.blockstates.ModelInit;
import util.MapInit;

public class MultipartSingleModelPortion implements IMultipartPortion
{
	Map<String, Boolean> when;
	Map<String, Object> apply;
	
	public MultipartSingleModelPortion(String when, boolean whenValue, ModelInit applyModel)
	{
		this(new MapInit<String, Boolean>().init(when, whenValue).get(), applyModel);
	}
	
	public MultipartSingleModelPortion(Map<String, Boolean> when, ModelInit applyModel)
	{
		this.when = when;
		this.apply = applyModel.get();
	}
}
