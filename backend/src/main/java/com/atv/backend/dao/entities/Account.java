package com.atv.backend.dao.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Entity
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"iban"})})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @Column(name = "iban", unique = true)
    private String iban;

    private Currency currency;

    private Double balance;

    private AccountType type;

//    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private List<Transaction> transactions;
//
//    public List<Transaction> getTransactions() {
//        return transactions;
//    }
//
//    public void setTransactions(List<Transaction> transactions) {
//        this.transactions = transactions;
//    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
    }

    public Account(String iban, Currency currency, Double balance, AccountType type, User user) {
    	this.iban = iban;
    	this.currency = currency;
    	this.balance = balance;
    	this.type = type;
    	this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static enum Currency {
    	RON, EUR, USD
    }

    public static enum AccountType {
    	CURRENT, SAVINGS, DEPOSIT, CREDIT
    }


}
