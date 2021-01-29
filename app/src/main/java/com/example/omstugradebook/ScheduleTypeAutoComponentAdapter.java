package com.example.omstugradebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omstugradebook.data.dao.ScheduleDao;
import com.example.omstugradebook.data.dao.ScheduleOwnerDao;
import com.example.omstugradebook.data.model.schedule.ScheduleAutoCompleteModel;
import com.example.omstugradebook.data.model.schedule.ScheduleOwner;
import com.example.omstugradebook.domain.connector.ScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class ScheduleTypeAutoComponentAdapter extends BaseAdapter implements Filterable {
    private final List<ScheduleAutoCompleteModel> scheduleAutoCompleteModels;

    private final Context mContext;

    private final boolean mHasInternet;

    @Inject
    ScheduleDao scheduleDao;

    @Inject
    ScheduleOwnerDao scheduleOwnerDao;

    //TODO Interactor просить у него данные
    public ScheduleTypeAutoComponentAdapter(Context context, boolean hasInternet) {
        App.getComponent().injectScheduleTypeAutoComponentAdapter(this);

        mContext = context;

        mHasInternet = hasInternet;

        scheduleAutoCompleteModels = new ArrayList<>();
    }

    private void setResults(List<ScheduleOwner> scheduleOwners, List<ScheduleOwner> favoriteScheduleOwners) {
        scheduleAutoCompleteModels.clear();

        List<String> favoriteStrings = new ArrayList<>();

        for (ScheduleOwner scheduleOwner : favoriteScheduleOwners) {
            favoriteStrings.add(scheduleOwner.getName());
        }

        for (ScheduleOwner scheduleOwner : scheduleOwners) {
            if (!scheduleOwner.getType().equals("person")) {
                boolean isFavorite = favoriteStrings.contains(scheduleOwner.getName());

                scheduleAutoCompleteModels.add(new ScheduleAutoCompleteModel(scheduleOwner, isFavorite));
            }
        }
    }


    @Override
    public int getCount() {
        return scheduleAutoCompleteModels.size();
    }

    @Override
    public ScheduleAutoCompleteModel getItem(int position) {
        return scheduleAutoCompleteModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            convertView = inflater.inflate(
                    R.layout.auto_complete_text_view_schedules,
                    parent,
                    false);
        }

        ScheduleOwner scheduleOwner;

        boolean isFavorite = true;

        try {
            ScheduleAutoCompleteModel scheduleAutoCompleteModel = getItem(position);

            scheduleOwner = scheduleAutoCompleteModel.getScheduleOwner();

            isFavorite = scheduleAutoCompleteModel.isFavorite();

        } catch (IndexOutOfBoundsException exception) {
            scheduleOwner = new ScheduleOwner(0, "-", "group");
            //todo: разобраться с этим, потому что костыль не выход
        }


        ((TextView) convertView.findViewById(R.id.value)).setText(scheduleOwner.getName());

        ((TextView) convertView.findViewById(R.id.type)).setText(scheduleOwner.getType());

        ImageView imageView = convertView.findViewById(R.id.favorite_image_view);

        if (isFavorite) {
            imageView.setImageResource(R.drawable.ic_favorite_blue);
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_white);
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint != null) {
                    List<ScheduleOwner> scheduleOwners = findScheduleOwners(constraint.toString());

                    filterResults.values = scheduleOwners;

                    filterResults.count = scheduleOwners.size();
                }
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                CompletableFuture.runAsync(() -> {
                    List<ScheduleOwner> scheduleOwners = scheduleOwnerDao.getAll();

                    if (results != null && results.count > 0) {
                        setResults((List<ScheduleOwner>) results.values, scheduleOwners);

                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                });
            }
        };
    }

    private List<ScheduleOwner> findScheduleOwners(String param) {
        return new ScheduleService().getScheduleOwners(param, mHasInternet);
    }
}
// todo: android recycler with pagination
