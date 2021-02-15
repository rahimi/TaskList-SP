package de.hsba.app.tasklistsp;

import android.content.SharedPreferences;

import java.util.List;

public class Task {
    private String label;

    public Task(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
