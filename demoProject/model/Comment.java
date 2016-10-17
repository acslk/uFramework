import u.mvc.Model;

import User;

public class Comment extends Model
{
	public int id;
	public String message;
	public User owner;
}