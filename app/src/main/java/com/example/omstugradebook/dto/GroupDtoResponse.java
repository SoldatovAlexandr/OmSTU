package com.example.omstugradebook.dto;

import java.io.Serializable;

public class GroupDtoResponse implements Serializable {
    private int id;
    private String label;
    private String description;
    private String type;

    public GroupDtoResponse(int id, String label, String description, String type) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.type = type;
    }

    public GroupDtoResponse() {
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
