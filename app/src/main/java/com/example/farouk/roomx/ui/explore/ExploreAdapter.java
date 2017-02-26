package com.example.farouk.roomx.ui.explore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Const;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//
//import static android.R.attr.key;
//import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by farouk on 1/29/17.
 */

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {


    private List<PlaceObject> roomList;

    Context context;
    VolleyCallback volleyCallback;
    private String roomId;
    private PlaceObject placeObject;
    private Integer placeId;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, tag, date, city, detale;
        public ImageView roompic;
        CircleImageView userpic;
        ToggleButton likeToggleButton;
        RelativeLayout likeRelativeLayout;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_date);
            tag = (TextView) view.findViewById(R.id.tv_tag);
            city = (TextView) view.findViewById(R.id.tv_cantry);
            detale = (TextView) view.findViewById(R.id.tv_inf);

            userpic = (CircleImageView) view.findViewById(R.id.imv_userphoto);
            roompic = (ImageView) view.findViewById(R.id.img_picRoom);
            likeToggleButton = (ToggleButton) view.findViewById(R.id.like_toggle);
            likeRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
        }
    }

    public ExploreAdapter(List<PlaceObject> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    public void callbackInfo(VolleyCallback volleyCallback) {
        this.volleyCallback = volleyCallback;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlist, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                String item = mList.get(itemPosition);*/
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        placeObject = roomList.get(position);
        if(placeObject.getUser().getPhotolink()!=null) {
            Log.i(" photo user", placeObject.getUser().getPhotolink());
            Picasso.with(context).load(placeObject.getUser().getPhotolink()).resize(70, 70).placeholder(R.drawable.ic_profile).into(holder.userpic);
        }else{
            holder.userpic.setImageResource(R.drawable.ic_profile);
        }
        if(placeObject.getRoomPhoto()!=null&&!placeObject.getRoomPhoto().isEmpty()&&placeObject.getRoomPhoto().size()>0) {
            Log.i(" photo placeObject", placeObject.getRoomPhoto().get(0).toString());
            Picasso.with(context).load(placeObject.getRoomPhoto().get(0).getPhotolink()).resize(200, 85).into(holder.roompic);
        }else{
            holder.roompic.setImageResource(R.drawable.building);
        }
        holder.name.setText(placeObject.getName()+"");
        holder.tag.setText(placeObject.getUser().getEmail()+"");
        holder.date.setText(placeObject.getCreatedAt()+"");
        holder.city.setText(placeObject.getUser().getCountry() + placeObject.getUser().getCity()+"");
        holder.detale.setText(placeObject.getDescription()+"");
        if (placeObject.getIsFavourate() == 1) {
            holder.likeToggleButton.setChecked(true);
        } else holder.likeToggleButton.setChecked(false);
        holder.likeToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == holder.likeToggleButton.getId()) {
                    Requests requests = new Requests(context);
                    requests.addToWishList(volleyCallback, context, String.valueOf(placeObject.getPid()),placeObject.getId());

                }
            }
        });
        holder.roompic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeId = roomList.get(position).getPid();
                Log.d("placeId", String.valueOf(placeId));
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra(Const.PLACE_PID, placeId);
                intent.putExtra(Const.PLACE_ID, roomList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }


}
