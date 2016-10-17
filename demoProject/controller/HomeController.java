import u.mvc.Controller;

public class HomeController extends Controller
{
	public Response home () {
		return ok("home");
	}

	public Response login () {
		User user = auth(request(), User.instance());
		if (user == null)
			return ok("failed to log in");
		return ok("logged in as " user.name);
	}

	public Response logout () {
		User user = getAuth(request(), User.instance());
		if (user == null)
			return ok("not logged in");
		unauth(user);
		return ok ("logged out");
	}
}