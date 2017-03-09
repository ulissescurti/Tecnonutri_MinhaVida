package br.com.soulskyye.tecnonutri.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.view.ui.FeedDetailsActivity;
import br.com.soulskyye.tecnonutri.view.ui.ProfileDetailsActivity;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.model.Profile;
import br.com.soulskyye.tecnonutri.util.Utils;
import retrofit2.Callback;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.FeedViewHolder> {

    public ArrayList<Item> listItems;
    Context context;
    Callback paginationCallback;

    public int p;
    public int t;

    public FeedListAdapter(ArrayList<Item> listItems, Context context, Callback paginationCallback) {
        this.listItems = listItems;
        this.context = context;
        this.paginationCallback = paginationCallback;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false);
        FeedViewHolder holder = new FeedViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        Item item = listItems.get(position);

        Picasso.with(context)
                .load(item.getProfile().getPhoto())
                .placeholder(R.drawable.person_placeholder)
                .error(R.drawable.ic_image_not_found)
                .into(holder.authorPhotoIv);
        Picasso.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.meal_placeholder)
                .error(R.drawable.ic_image_not_found)
                .into(holder.mealPhotoIv);

        holder.authorNameTv.setText(item.getProfile().getName());
        holder.authorGoalTv.setText(item.getProfile().getGoal());
        holder.dayTv.setText(context.getString(R.string.meal_of)+ Utils.getFormattedDate(item.getDate()));
        holder.kcalTv.setText(String.format("%.2f", item.getEnergy())+" kcal");
        //animate(holder);

        if(holder.getAdapterPosition() == listItems.size()-1){
            Utils.showProgressDialog(context);
            BackendManager.getInstance().getPaginatedFeed(p, t, paginationCallback);
        }
        holder.mealPhotoIv.setTag(item);
        holder.mealPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = (Item) v.getTag();
                Intent intent = new Intent(context, FeedDetailsActivity.class);
                intent.putExtra(FeedDetailsActivity.FEED_HASH, item.getFeedHash());
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation((FeedActivity)context, (ImageView)v, "mealImage");
                context.startActivity(intent);
            }
        });

        holder.profileLayout.setTag(item.getProfile());
        holder.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = (Profile) v.getTag();
                Intent intent = new Intent(context, ProfileDetailsActivity.class);
                intent.putExtra(ProfileDetailsActivity.PROFILE_ID, profile.getId());
                context.startActivity(intent);
            }
        });

        holder.heartCb.setChecked(item.isLiked());
        holder.heartCb.setTag(item);
        holder.heartCb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Item item = (Item) cb.getTag();

                item.setLiked(cb.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public void insertItem(Item item) {
        listItems.add(listItems.size(), item);
        notifyDataSetChanged();
    }

    public void insertItems(int oldSize) {
        notifyItemRangeInserted(oldSize, listItems.size());
    }

    public void removeItem(Item item) {
        int position = listItems.indexOf(item);
        listItems.remove(position);
        notifyItemRemoved(position);
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {

        LinearLayout profileLayout;
        ImageView authorPhotoIv;
        TextView authorNameTv;
        TextView authorGoalTv;

        ImageView mealPhotoIv;
        CheckBox heartCb;

        TextView dayTv;
        TextView kcalTv;

        FeedViewHolder(View itemView) {
            super(itemView);
            profileLayout = (LinearLayout) itemView.findViewById(R.id.feed_item_first_layout);
            authorPhotoIv = (ImageView) itemView.findViewById(R.id.feed_item_author_iv);
            authorNameTv = (TextView) itemView.findViewById(R.id.feed_item_author_name_tv);
            authorGoalTv = (TextView) itemView.findViewById(R.id.feed_item_goal_tv);
            mealPhotoIv = (ImageView) itemView.findViewById(R.id.feed_item_meal_iv);
            dayTv = (TextView) itemView.findViewById(R.id.feed_item_day_tv);
            kcalTv = (TextView) itemView.findViewById(R.id.feed_item_kcal_tv);
            heartCb = (CheckBox) itemView.findViewById(R.id.feed_item_meal_heart_cb);
        }
    }
}
