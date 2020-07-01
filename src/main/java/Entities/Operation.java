package Entities;

import com.google.gson.Gson;

public class Operation {
    private int value;
    private String resource;

    public Operation(int value, String resource) {
        this.value = value;
        this.resource = resource;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
