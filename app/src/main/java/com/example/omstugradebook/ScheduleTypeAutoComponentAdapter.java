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

import com.example.omstugradebook.database.DataBaseManager;
import com.example.omstugradebook.database.dao.ScheduleDao;
import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTypeAutoComponentAdapter extends BaseAdapter implements Filterable {
    private final List<ScheduleAutoCompleteModel> scheduleAutoCompleteModels = new ArrayList<>();
    private final Context mContext;


    public ScheduleTypeAutoComponentAdapter(Context context) {
        mContext = context;
    }

    private void setResults(List<ScheduleOwner> scheduleOwners, List<String> favoriteStrings) {
        scheduleAutoCompleteModels.clear();
        for (ScheduleOwner scheduleOwner : scheduleOwners) {
            if (favoriteStrings.contains(scheduleOwner.getName())) {
                scheduleAutoCompleteModels.add(new ScheduleAutoCompleteModel(scheduleOwner, true));
            } else {
                scheduleAutoCompleteModels.add(new ScheduleAutoCompleteModel(scheduleOwner, false));
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
            convertView = inflater.inflate(R.layout.auto_complete_text_view_schedules, parent, false);
        }
        ScheduleAutoCompleteModel scheduleAutoCompleteModel = getItem(position);
        ScheduleOwner scheduleOwner = scheduleAutoCompleteModel.getScheduleOwner();
        ((TextView) convertView.findViewById(R.id.value)).setText(scheduleOwner.getName());
        ((TextView) convertView.findViewById(R.id.type)).setText(scheduleOwner.getType());
        ImageView imageView = convertView.findViewById(R.id.favorite_image_view);
        if (scheduleAutoCompleteModel.isFavorite()) {
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
                ScheduleDao scheduleDao = DataBaseManager.getScheduleDao();
                List<String> favoriteSchedules = scheduleDao.readFavoriteSchedule();
                if (results != null && results.count > 0) {
                    setResults((List<ScheduleOwner>) results.values, favoriteSchedules);
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private List<ScheduleOwner> findScheduleOwners(String param) {
        return new ScheduleService().getScheduleOwners(param);
    }
}
