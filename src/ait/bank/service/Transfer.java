package ait.bank.service;

import ait.bank.model.Account;

public class Transfer implements Runnable {
    private Account accFrom;
    private Account accTo;
    private int sum;

    public Transfer(Account accFrom, Account accTo, int sum) {
        this.accFrom = accFrom;
        this.accTo = accTo;
        this.sum = sum;
    }

    @Override
    public void run() {
        synchronized (accFrom) {
            synchronized (accTo) {
                if (accFrom.getBalance() >= sum) {
                    accFrom.credit(sum);
                    accTo.debit(sum);
                }
            }
        }
    }
}
