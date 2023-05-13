package com.atv.backend.requests;

public class TransactionRequest {
    private String ibanSource;
    private String ibanDest;
    private Double sum;

    public TransactionRequest(String ibanSource, String ibanDest, Double sum) {
        this.ibanSource = ibanSource;
        this.ibanDest = ibanDest;
        this.sum = sum;
    }

    public TransactionRequest() {
    }

    public String getIbanSource() {
        return ibanSource;
    }

    public void setIbanSource(String ibanSource) {
        this.ibanSource = ibanSource;
    }

    public String getIbanDest() {
        return ibanDest;
    }

    public void setIbanDest(String ibanDest) {
        this.ibanDest = ibanDest;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
