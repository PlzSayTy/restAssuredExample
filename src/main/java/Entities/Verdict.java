package Entities;

import com.google.gson.Gson;

public class Verdict {
    private String verdict;
    private String message;

    public Verdict(String verdict, String message) {
        this.verdict = verdict;
        this.message = message;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
