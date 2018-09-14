package codes.saurabh.cookmate.Util;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mayank on 3/25/2018.
 */

public class PostUtil
{
    private String caption;
    private String category;
    private long commentCount;
    private String ingredient;
    private long isVeg;
    private long likeCount;
    private String postDate;
    private String recipe;
    private String username;
    private String postId;
    private ArrayList<URL> photoList;

    public PostUtil(String postid ,String mcaption, String mCategory, long mCommentCount, String mingredient,
                long mIsVeg, long mLikeCount,String mPostDate, String mRecipe, String mUsername)
    {
        this.postId = postid;
        this.caption = mcaption;
        this.category = mCategory;
        this.commentCount = mCommentCount;
        this.ingredient = mingredient;
        this.isVeg = mIsVeg;
        this.likeCount = mLikeCount;
        this.postDate = mPostDate;
        this.recipe = mRecipe;
        this.username = mUsername;
        this.photoList = new ArrayList<>();
    }


    public String getPostId()
    {
        return this.postId;
    }
    public String getCategory()
    {
        return this.category;
    }

    public String getCaption()
    {
        return this.caption;
    }
    public long getCommentCount()
    {
        return this.commentCount;
    }
    public String getIngredient()
    {
        return this.ingredient;
    }
    public long getIsVeg()
    {
        return this.isVeg;
    }
    public long getLikeCount()
    {
        return this.likeCount;
    }
    public String getPostDate()
    {
        return this.postDate;
    }
    public String getRecipe()
    {
        return this.recipe;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setPhotoList(URL url)
    {
        this.photoList.add(url);
    }
    public ArrayList<URL> getPhotoUrlList()
    {
        return this.photoList;
    }
}
