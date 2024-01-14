package templates.json.blockstates.multipart;

import java.util.List;
import java.util.Map;

import templates.json.blockstates.ModelList;

public class MultipartPortion implements IMultipartPortion
{
	Map<String, Boolean> when;
	List<Map<String, Object>> apply;
	
	public MultipartPortion(Map<String, Boolean> when, ModelList apply)
	{
		this.when = when;
		this.apply = apply.get();
	}
}
