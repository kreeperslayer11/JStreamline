package templates.json.models.multipart.flame;

public enum FacingDir
{
	up("_up"), up_alt("_up_alt"), down("_floor"), side("_side"), side_alt("_side_alt"), noside("_noside"), noside_alt("_noside_alt"), post("_post");// east, west, north, south;
	
	public final String postfix;
	
	private FacingDir(String postfix)
	{
		this.postfix = postfix;
	}
}
