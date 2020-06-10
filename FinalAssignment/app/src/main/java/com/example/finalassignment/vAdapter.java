package com.example.finalassignment;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class vAdapter extends RecyclerView.Adapter<vAdapter.ViewHolder>{
    private static final String TAG="aAdapter";
    private List<VideoItem> videoItemList;
    private final ListItemClickListener vClickListener;

    public vAdapter(List<VideoItem> vlist,ListItemClickListener listener){
        this.videoItemList = vlist;
        this.vClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context1 = parent.getContext();
        int layoutIdForListItem = R.layout.v_item;
        LayoutInflater inflater = LayoutInflater.from(context1);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        VideoItem v1 = videoItemList.get(position);
        holder.tv_description.setText(v1.getDescription());
        holder.tv_nickname.setText("用户名："+v1.getNickname());
        Glide.with(holder.itemView.getContext()).load(v1.getAvatar()).override(223,128).into(holder.iv_avatar);

    }

    @Override
    public int getItemCount() {
        return videoItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tv_description;
        private final TextView tv_nickname;
        private final ImageView iv_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            this.tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            this.iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (vClickListener != null) {
                vClickListener.onListItemClick(clickedPosition);
            }
        }
    }



    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
