package com.example.omstugradebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.omstugradebook.model.schedule.ScheduleOwner;
import com.example.omstugradebook.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTypeAutoComponentAdapter extends BaseAdapter implements Filterable {

    private final List<ScheduleOwner> mResults = new ArrayList<>();
    private final Context mContext;

    public ScheduleTypeAutoComponentAdapter(Context context) {
        mContext = context;
    }

    private void setResults(List<ScheduleOwner> scheduleOwners) {
        mResults.clear();
        mResults.addAll(scheduleOwners);
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public ScheduleOwner getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        ScheduleOwner scheduleOwner = getItem(position);
        ((TextView) convertView.findViewById(R.id.text1)).setText(scheduleOwner.getName());
        ((TextView) convertView.findViewById(R.id.text2)).setText(scheduleOwner.getType());

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
                if (results != null && results.count > 0) {
                    setResults((List<ScheduleOwner>) results.values);
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
