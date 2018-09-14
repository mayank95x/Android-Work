package codes.saurabh.cookmate.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import codes.saurabh.cookmate.R;
import codes.saurabh.cookmate.Util.CommentUtil;

/**
 * Created by Mayank on 3/27/2018.
 */

public class CommentAdapter extends ArrayAdapter<CommentUtil>
{
    public CommentAdapter(Activity context, ArrayList<CommentUtil> list)
    {
        super(context, 0 ,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if(currentView == null)
        {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_comment, null, false);
        }

        CommentUtil comment = getItem(position);

        TextView usernameTextView = (TextView) currentView.findViewById(R.id.commentUtilUsername);
        //TextView nameTextView = (TextView) currentView.findViewById(R.id.commentUtilName);
        TextView commentTextView = (TextView) currentView.findViewById(R.id.commentUtilComment);

        usernameTextView.setText(comment.getUsername());
        //nameTextView.setText(comment.getName());
        commentTextView.setText(comment.getComment());

        return currentView;
    }
}
