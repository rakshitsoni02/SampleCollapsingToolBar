package in.co.rakshit.samplefloat.ui.main;

import android.content.Context;
import android.location.Location;
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

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyViewHolder> {
    private Context mContext;
    private List<Business> albumList;
    private Location currentLocation;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, distance;
        public SquareImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            distance = (TextView) view.findViewById(R.id.distance);
            thumbnail = (SquareImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Inject
    public BusinessAdapter() {
        this.albumList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bussiness_card, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setBusiness(List<Business> ribots, Context context, Location currentLocation) {
        albumList = ribots;
        this.mContext = context;
        this.currentLocation = currentLocation;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Business business = albumList.get(position);
        holder.title.setText(business.getName());
        holder.distance.setText(business.getAddress().getStreet());
        holder.count.setText(business.getAddress().getAddressLine() + " " + business.getAddress().getCity());
        holder.distance.setText(getDistance(business.getLocation()));
        Glide.with(mContext)
                .load(business.getProfileImage())
                .into(new ViewTarget<SquareImageView, GlideDrawable>(holder.thumbnail) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                        SquareImageView myView = this.view;
                        myView.setBackground(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    private String getDistance(String toLatLong) {
        float distance = 0;

        if (currentLocation != null) {
            String[] splitLatLong = toLatLong.split(",");
            double latitude = Double.parseDouble(splitLatLong[0]);
            double longitude = Double.parseDouble(splitLatLong[1]);
            Location mCurrentLocation = new Location("crntLocation");
            mCurrentLocation.setLatitude(currentLocation.getLatitude());
            mCurrentLocation.setLongitude(currentLocation.getLongitude());

            Location mDestinationLocation = new Location("destLocation");
            mDestinationLocation.setLatitude(latitude);
            mDestinationLocation.setLongitude(longitude);


            distance = mCurrentLocation.distanceTo(mDestinationLocation);
        }
        String result = "";
        if (distance < 1001) {
            result = Math.round(distance) + " m";
        } else {
            result = Math.round(distance * 0.001) + " kms";
        }
        return result;


    }


}