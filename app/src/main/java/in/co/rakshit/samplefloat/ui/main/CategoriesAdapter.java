package in.co.rakshit.samplefloat.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.co.rakshit.samplefloat.R;
import in.co.rakshit.samplefloat.data.model.Business;
import in.co.rakshit.samplefloat.views.SquareImageView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Business> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SquareImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (SquareImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Inject
    public CategoriesAdapter() {
        this.albumList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_card, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setBusiness(List<Business> ribots, Context context) {
        albumList = ribots;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Business album = albumList.get(position);
        holder.thumbnail.setBackground(mContext.getDrawable(R.drawable.dog));
//        Glide.with(mContext)
//                .load(album.getProfileImage())
//                .into(new ViewTarget<SquareImageView, GlideDrawable>(holder.thumbnail) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
//                        SquareImageView myView = this.view;
//                        myView.setBackground(resource);
//                        // Set your resource on myView and/or start your animation here.
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}