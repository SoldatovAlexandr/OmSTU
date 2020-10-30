package com.example.omstugradebook.view.state.model;

import com.example.omstugradebook.model.schedule.ScheduleOwner;

import java.util.ArrayList;
import java.util.Calendar;

public class DataCashRepository {
    private static DataCashRepository instance;
    private final ScheduleStateModel scheduleStateModel;
    private SearchStateModel searchStateModel;

    private DataCashRepository() {
        scheduleStateModel = new ScheduleStateModel(new ArrayList<>());
        searchStateModel = new SearchStateModel(
                new ScheduleOwner(351, "ПИН-181", "group"), Calendar.getInstance());
    }

    public static DataCashRepository getInstance() {
        if (instance == null) {
            instance = new DataCashRepository();
        }
        return instance;
    }

    public void saveScheduleStateModel(ScheduleStateModel scheduleStateModel) {
        this.scheduleStateModel.getSchedules().clear();
        this.scheduleStateModel.getSchedules().addAll(scheduleStateModel.getSchedules());
    }

    public ScheduleStateModel getScheduleStateModel() {
        return scheduleStateModel;
    }

    public void saveSearchStateModel(SearchStateModel searchStateModel) {
        this.searchStateModel = searchStateModel;
    }

    public SearchStateModel getSearchStateModel() {
        return searchStateModel;
    }
}
