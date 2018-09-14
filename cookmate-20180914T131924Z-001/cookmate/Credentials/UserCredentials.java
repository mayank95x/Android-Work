package codes.saurabh.cookmate.Credentials;

/**
 * Created by Mayank on 3/27/2018.
 */

public class UserCredentials
{
    private String username ;
    public String name;
    public UserCredentials()
    {
        this.username = "@pk";
        this.name = "PK";

    }

    public String getUsername()
    {
        return this.username;
    }
    public String getName()
    {
        return this.name;
    }
}
