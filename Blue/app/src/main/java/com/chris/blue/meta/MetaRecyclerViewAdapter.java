package com.chris.blue.meta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chris.blue.R;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.diskCacheStrategyOf;
import static com.bumptech.glide.request.RequestOptions.errorOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;
import static com.bumptech.glide.request.RequestOptions.skipMemoryCacheOf;

public class MetaRecyclerViewAdapter extends RecyclerView.Adapter<MetaRecyclerViewAdapter.MetaViewHolder> {

    private List<ImageBean> imgList;

    private Context context;

    public MetaRecyclerViewAdapter(Context context,List<ImageBean> imgList){
        this.imgList = imgList;
        this.context = context;
    }


    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MetaViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        ImageBean imageBean = imgList.get(position);
        holder.tvTitle.setText(imageBean.getTitle());
        setImg(imageBean.getThumb(),holder.imageView);

    }

    public void updateList(List<ImageBean> imgList){
        if(imgList != null){
            this.imgList = imgList;
            notifyDataSetChanged();
        }
    }


    public void setImg(String url,ImageView imageView) {
        try {
            Glide.with(context)
                    .load(url)
                    .apply(errorOf(R.drawable.ic_test))
                    .apply(placeholderOf(R.drawable.ic_test))
                    .apply(skipMemoryCacheOf(false))
                    .apply(diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return imgList == null ? 0 : imgList.size();
    }

    static class MetaViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView imageView;

        public MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.meta_item_title);
            imageView = itemView.findViewById(R.id.meta_item_img);
        }
    }
}
