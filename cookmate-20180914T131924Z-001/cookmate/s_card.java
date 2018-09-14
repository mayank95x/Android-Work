package codes.saurabh.cookmate;

/**
 * Created by Saurabh Kumar on 09-03-2018.
 */

public class s_card {

    private String mUsernameString;
    private String muser_handle;
    private int mcontentImageId;


    /*
    public s_card(String mUsernameString, int mBookmarkId, int mContentImageId, String mCaptionString, int mLikeId, int mCommentId, int mShareId) {
        this.mUsernameString = mUsernameString;

    }*/

    /*
    public s_card(String mUsernameString){
        this.mUsernameString=mUsernameString;
    }*/

    public s_card(String mUsernameString,String muser_handle,int mcontentImageId){
        this.mUsernameString=mUsernameString;
        this.muser_handle=muser_handle;
        this.mcontentImageId=mcontentImageId;
    }

    public String getmUsernameString() {
        return mUsernameString;
    }

    public String getmuser_handle(){ return muser_handle; }

    public int getMcontentImageId(){
        return mcontentImageId;
    }

    public void setmUsernameString(String mUsernameString) {
        this.mUsernameString = mUsernameString;
    }


    public void setmuser_name(String muser_handle){
        this.muser_handle=muser_handle;
    }


}
