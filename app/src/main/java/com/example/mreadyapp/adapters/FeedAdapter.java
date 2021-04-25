package com.example.mreadyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mreadyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedAdapterViewHolder>{

    private List<JSONObject> postsLists;
    private Context context;

    public FeedAdapter() {
    }

    public void setData(List<JSONObject> postsLists){
        this.postsLists=postsLists;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public FeedAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new FeedAdapter.FeedAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.row_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapterViewHolder holder, int position) {

        JSONObject response=postsLists.get(position);
        String name= null;
        String message=null;
        String date=null;

        try {
            name = response.getString("display_name");
            message=response.getString("message");
            date=response.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(name.equals("null")){
            holder.tvDisplayName.setText("Anonymous");
            holder.tvDisplayName.setTextColor(ContextCompat.getColor(context,R.color.color_anonim));
        }
        else {
            holder.tvDisplayName.setText(name);
            holder.tvDisplayName.setTextColor(ContextCompat.getColor(context,R.color.color_name));

        }

        holder.tvMessage.setText(message);

//            DateFormat format=new SimpleDateFormat("HH:mm , dd MM yyyy ");
//        try {
//            Date date1=format.parse(date);
//            holder.tvDate.setText(String.valueOf(date1));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
            holder.tvDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return postsLists.size();
    }

    public class FeedAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView tvDisplayName, tvMessage, tvDate;
        public FeedAdapterViewHolder(View itemView) {
            super(itemView);
            tvDisplayName=itemView.findViewById(R.id.tvDisplayName);
            tvMessage=itemView.findViewById(R.id.tvMessage);
            tvDate=itemView.findViewById(R.id.tvDate);


        }
    }
}
