package com.kigold.android.naijaprogrammers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Model> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public FoldingCellListAdapter(Context context, List<Model> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get title for selected view
        Model item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder

            viewHolder.github_url = (TextView) cell.findViewById(R.id.github_url);
            viewHolder.title_username = (TextView) cell.findViewById(R.id.title_username);
            viewHolder.content_username = (TextView) cell.findViewById(R.id.content_username);
            viewHolder.title_profile_pics = (ImageView) cell.findViewById(R.id.title_profile_pics);
            viewHolder.content_profile_pics = (ImageView) cell.findViewById(R.id.content_profile_pics);
            viewHolder.share_btn = (ImageView) cell.findViewById(R.id.share_btn);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder

        viewHolder.github_url.setText(item.getGithub_url());
        viewHolder.title_username.setText(item.getUsername());
        viewHolder.content_username.setText(item.getUsername());
        //load image with Picasso
        Picasso.with(getContext())
                .load(item.getAvatar())
                .placeholder(R.mipmap.naruto) // optional
                .error(R.mipmap.naruto)         // optional
                .into(viewHolder.title_profile_pics);
        Picasso.with(getContext())
                .load(item.getAvatar())
                .placeholder(R.mipmap.naruto) // optional
                .error(R.mipmap.naruto)         // optional
                .into(viewHolder.content_profile_pics);
        //viewHolder.title_profile_pics.setImageResource(item.getAvatar());
        //viewHolder.content_profile_pics.setImageResource(item.getAvatar());



        // set custom btn handler for list title from that title
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.share_btn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in title
            viewHolder.share_btn.setOnClickListener(defaultRequestBtnClickListener);
        }


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView share_btn;
        TextView title_username;
        TextView content_username;
        ImageView title_profile_pics;
        ImageView content_profile_pics;
        TextView github_url;
    }
}
