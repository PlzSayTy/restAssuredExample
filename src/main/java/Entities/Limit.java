package Entities;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Limit {
    @SerializedName("BOTH:SUM")
    private String bothSum;
    @SerializedName("ACCOUNT:COUNT")
    private String count;
    @SerializedName("CARD:SUM")
    private String cardSum;
    public Limit(){

    }
    public Limit(String bothSum, String count, String cardSum){
        this.bothSum = bothSum;
        this.count = count;
        this.cardSum = cardSum;
    }
    public String getBothSum() {
        return bothSum;
    }

    public void setBothSum(String bothSum) {
        this.bothSum = bothSum;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCardSum() {
        return cardSum;
    }

    public void setCardSum(String cardSum) {
        this.cardSum = cardSum;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
