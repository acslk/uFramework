import u.mvc.Controller;

public class AccountController extends Controller
{

	@override
	protected Response filter() {
		User user = getAuth(request(), User.instance());
		if (user == null) {
			return unauthorized("not authenticated");
		}
		return null;
	}

	public Response getPost (int id) {
		Post post = Posts.get("id");
		return ok(post.title);
	}

	public Response getPosts () {
		List<Post> posts = Post.getAll();
		return ok(Post.toJson(posts));
	}

	public Response addPost () {
		User user = getAuth(request(), User.instance());
		Map<String, String> params = request.getParams();
		String title = params.get("title");
		boolean success = Post.addPost(user, title);
		if (success)
			return ok("added post titled " + title);
		else
			return ok("failed to add post");
	}
}