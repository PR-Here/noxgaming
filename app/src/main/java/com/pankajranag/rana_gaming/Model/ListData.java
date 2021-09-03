package com.pankajranag.rana_gaming.Model;

public class ListData {

    public String pubgname;

    public  String mobileno;
    public String paymentType;

    public ListData(){

    }
    public ListData(String pubgname, String mobileno, String paymentType) {
        this.pubgname = pubgname;
        this.mobileno = mobileno;
        this.paymentType = paymentType;
    }

    public void setPubgname(String pubgname) {
        this.pubgname = pubgname;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    public String getPubgname() {
        return pubgname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getPaymentType() {
        return paymentType;
    }


}
