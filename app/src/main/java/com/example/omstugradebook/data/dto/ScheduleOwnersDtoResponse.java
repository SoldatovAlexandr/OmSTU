package com.example.omstugradebook.data.dto;

import java.io.Serializable;

public class ScheduleOwnersDtoResponse implements Serializable {
    private int id;

    private final String label;

    private final String description;

    private final String type;

    public ScheduleOwnersDtoResponse(int id, String label, String description, String type) {
        this.id = id;

        this.label = label;

        this.description = description;

        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
