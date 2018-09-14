package codes.saurabh.cookmate.Util;

/**
 * Created by Mayank on 3/27/2018.
 */

public class CommentUtil
{
    private String usernmae;
    private String name;
    private String comment;

    public CommentUtil(String uname, String name, String comment)
    {
        this.usernmae = uname;
        this.name = name;
        this.comment = comment;
    }

    public String getUsername()
    {
        return this.usernmae;
    }
    public String getName()
    {
        return this.name;
    }
    public String getComment()
    {
        return this.comment;
    }
}
