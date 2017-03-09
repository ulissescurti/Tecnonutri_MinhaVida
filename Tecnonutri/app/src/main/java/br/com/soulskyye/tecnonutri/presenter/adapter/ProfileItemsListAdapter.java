package br.com.soulskyye.tecnonutri.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.view.ui.FeedDetailsActivity;
import br.com.soulskyye.tecnonutri.backend.BackendManager;
import br.com.soulskyye.tecnonutri.model.Item;
import br.com.soulskyye.tecnonutri.util.Utils;
import retrofit2.Callback;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class ProfileItemsListAdapter extends RecyclerView.Adapter<ProfileItemsListAdapter.ProfileViewHolder> {

    public ArrayList<Item> listItems;
    private ArrayList<Item[]> adapterListItems;
    Context context;
    Callback paginationCallback;
    long id;

    public int p;
    public int t;

    public ProfileItemsListAdapter(long id, ArrayList<Item> listItems, Context context, Callback paginationCallback) {
        this.id = id;
        this.listItems = listItems;
        this.context = context;
        this.paginationCallback = paginationCallback;

        adapterListItems = new ArrayList<>();
        int count = 0;
        int count2 = 0;
        for(Item item : listItems){
            if(count == 2){
                adapterListItems.get(count2)[count] = item;
                count = 0;
                count2++;
            } else if(count == 1){
                adapterListItems.get(count2)[count] = item;
                count++;
            } else{
                Item[] items = new Item[3];
                items[count] = item;
                adapterListItems.add(items);
                count++;
            }
        }
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
        ProfileViewHolder holder = new ProfileViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {

        Item[] item = adapterListItems.get(position);

        if(holder.getAdapterPosition() == adapterListItems.size()-1){
            Utils.showProgressDialog(context);
            BackendManager.getInstance().getPaginatedProfile(id, p, t, paginationCallback);
        }

        for(int i=0; i<3; i++){
            if(i == 0 && item[0] != null){
                holder.mealPhotoIv1.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(item[0].getImage())
                        .placeholder(R.drawable.meal_placeholder)
                        .error(R.drawable.ic_image_not_found)
                        .into(holder.mealPhotoIv1);
                holder.mealPhotoIv1.setTag(item[0]);
                holder.mealPhotoIv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item item = (Item) v.getTag();
                        Intent intent = new Intent(context, FeedDetailsActivity.class);
                        intent.putExtra(FeedDetailsActivity.FEED_HASH, item.getFeedHash());
                        context.startActivity(intent);

                    }
                });
            } else if(i == 0){
                holder.mealPhotoIv1.setVisibility(View.INVISIBLE);
            }
            if(i == 1 && item[1] != null){
                holder.mealPhotoIv2.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(item[1].getImage())
                        .placeholder(R.drawable.meal_placeholder)
                        .error(R.drawable.ic_image_not_found)
                        .into(holder.mealPhotoIv2);
                holder.mealPhotoIv2.setTag(item[1]);
                holder.mealPhotoIv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item item = (Item) v.getTag();
                        Intent intent = new Intent(context, FeedDetailsActivity.class);
                        intent.putExtra(FeedDetailsActivity.FEED_HASH, item.getFeedHash());
                        context.startActivity(intent);

                    }
                });
            } else if(i == 1){
                holder.mealPhotoIv2.setVisibility(View.INVISIBLE);
            }
            if(i == 2 && item[2] != null){
                holder.mealPhotoIv3.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(item[2].getImage())
                        .placeholder(R.drawable.meal_placeholder)
                        .error(R.drawable.ic_image_not_found)
                        .into(holder.mealPhotoIv3);
                holder.mealPhotoIv3.setTag(item[2]);
                holder.mealPhotoIv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item item = (Item) v.getTag();
                        Intent intent = new Intent(context, FeedDetailsActivity.class);
                        intent.putExtra(FeedDetailsActivity.FEED_HASH, item.getFeedHash());
                        context.startActivity(intent);

                    }
                });
            } else if (i == 2){
                holder.mealPhotoIv3.setVisibility(View.INVISIBLE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return adapterListItems.size();
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

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView mealPhotoIv1;
        ImageView mealPhotoIv2;
        ImageView mealPhotoIv3;

        ProfileViewHolder(View itemView) {
            super(itemView);
            mealPhotoIv1 = (ImageView) itemView.findViewById(R.id.profile_item_meal1_iv);
            mealPhotoIv2 = (ImageView) itemView.findViewById(R.id.profile_item_meal2_iv);
            mealPhotoIv3 = (ImageView) itemView.findViewById(R.id.profile_item_meal3_iv);
        }
    }
}
