package com.example.diogo.clima;

import java.io.Serializable;

public class Clima implements Serializable{
    private String name;
    private String date;
    private String weekday;
    private String max;
    private String min;
    private String description;
    private String condition;

    public Clima(String date, String weekday, String max, String min, String description, String condition) {
        this.date = date;
        this.weekday = weekday;
        this.max = max;
        this.min = min;
        this.description = description;
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

}
