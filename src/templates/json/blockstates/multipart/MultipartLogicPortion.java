package templates.json.blockstates.multipart;

import java.util.List;
import java.util.Map;

import templates.json.blockstates.ModelList;

public class MultipartLogicPortion implements IMultipartPortion
{
	Map<String, List<Map<String, Boolean>>> when;
	List<Map<String, Object>> apply;
	
	public MultipartLogicPortion(Map<String, List<Map<String, Boolean>>> when, ModelList apply)
	{
		this.when = when;
		this.apply = apply.get();
	}
}
