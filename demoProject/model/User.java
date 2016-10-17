import u.mvc.Model;

public class User extends AuthModel
{
	public int id;
	public String name;
	public String password;
	public int reputation;
	public MList<Post> posts;
	public MList<Comment> comments;

	@override
	public Map<String, String> authKey() {
		Map<String, String> keys = new Map<>();
		keys.add("name", name);
		keys.add("password", password);
		return keys;
	}
}