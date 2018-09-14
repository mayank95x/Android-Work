package codes.saurabh.cookmate.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class follower {

    private   String username;
    private   String name;
    private   int picID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicID() {
        return picID;
    }

    public void setPicID(int picID) {
        this.picID = picID;
    }

    public follower(String username, String name, int picID) {
        this.username = username;
        this.name = name;
        this.picID = picID;
    }

    @Override
    public String toString() {
        return "Username: "+getUsername()+"\n"+"Name: "+getName();
    }
}
