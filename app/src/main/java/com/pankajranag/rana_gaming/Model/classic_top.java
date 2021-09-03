package com.pankajranag.rana_gaming.Model;

public class classic_top {

    public String id;

    public String amount;
    public String name;


    public classic_top(String id, String amount, String name) {
        this.id = id;
        this.amount = amount;
        this.name = name;
    }

    public classic_top() {
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
