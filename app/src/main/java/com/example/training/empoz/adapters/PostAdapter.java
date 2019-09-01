package com.example.training.empoz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.training.empoz.R;
import com.example.training.empoz.model.Post_Content;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<Post_Content> mPost;
    public PostAdapter(Context context, ArrayList<Post_Content> post_details){
        mcontext=context;
        mPost=post_details;

    }
    @Override
    public int getCount() {
        if (mPost==null) {
            return 0;
        }else
        {
            return mPost.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mPost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post_Content post=mPost.get(position);
        ViewHolder viewHolder;
        if (convertView==null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.post_cont, parent, false);
            viewHolder.txtname = (TextView) convertView.findViewById(R.id.user_name_post);
            viewHolder.txtdescrip = (TextView) convertView.findViewById(R.id.user_post_value);
            viewHolder.txtdate=(TextView) convertView.findViewById(R.id.user_post_time);
            
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.txtname.setText(post.post_id);
        viewHolder.txtdescrip.setText(post.post_value);
        viewHolder.txtdate.setText(post.post_time);

       return convertView;
    }
    private static class ViewHolder {
        TextView txtname;
        TextView txtdescrip;
        TextView txtdate;

    }
}
