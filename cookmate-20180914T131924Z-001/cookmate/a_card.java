package codes.saurabh.cookmate;

/**
 * Created by Saurabh Kumar on 09-03-2018.
 */

public class a_card {

    private String mUsernameString;
    private int mBookmarkId;
    private int mContentImageId;
    private String mCaptionString;
    private int mLikeId;
    private int mCommentId;
    private int mShareId;

    public a_card(String mUsernameString, int mBookmarkId, int mContentImageId, String mCaptionString, int mLikeId, int mCommentId, int mShareId) {
        this.mUsernameString = mUsernameString;
        this.mBookmarkId = mBookmarkId;
        this.mContentImageId = mContentImageId;
        this.mCaptionString = mCaptionString;
        this.mLikeId = mLikeId;
        this.mCommentId = mCommentId;
        this.mShareId = mShareId;
    }

    public String getmUsernameString() {
        return mUsernameString;
    }

    public void setmUsernameString(String mUsernameString) {
        this.mUsernameString = mUsernameString;
    }

    public int getmBookmarkId() {
        return mBookmarkId;
    }

    public void setmBookmarkId(int mBookmarkId) {
        this.mBookmarkId = mBookmarkId;
    }

    public int getmContentImageId() {
        return mContentImageId;
    }

    public void setmContentImageId(int mContentImageId) {
        this.mContentImageId = mContentImageId;
    }

    public String getmCaptionString() {
        return mCaptionString;
    }

    public void setmCaptionString(String mCaptionString) {
        this.mCaptionString = mCaptionString;
    }

    public int getmLikeId() {
        return mLikeId;
    }

    public void setmLikeId(int mLikeId) {
        this.mLikeId = mLikeId;
    }

    public int getmCommentId() {
        return mCommentId;
    }

    public void setmCommentId(int mCommentId) {
        this.mCommentId = mCommentId;
    }

    public int getmShareId() {
        return mShareId;
    }

    public void setmShareId(int mShareId) {
        this.mShareId = mShareId;
    }
}
