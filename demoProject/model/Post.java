import u.mvc.Model;

import User;

public class Post extends Model
{
	public int id;
	public String title;
	public MList<Comment> comments;
	public User owner;

	public static boolean addPost(User user, String title) {
		return Post.add(new Post(Post.nextId(), title, new MList<Comment>(), user));
	}

	public static string toJson(List<Post> posts) {
		JsonArray ret = new JsonArray();
		for (Post post : posts) {
			JsonArray.add(post.title);
		}
		return ret;
	}
}