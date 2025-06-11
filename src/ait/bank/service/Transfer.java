package ait.bank.service;

import ait.bank.model.Account;

public class Transfer implements Runnable {
    private final static Object monitor = new Object();
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

        // accNumber = id
        // Common Resource locking protocol:
        // two accounts will be blocked in ascending order of their numbers (accNumber)

        if (accFrom.getAccNumber() < accTo.getAccNumber()) {
            synchronized (accFrom) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (accTo) {
                    if (accFrom.getBalance() >= sum) {
                        accFrom.credit(sum);
                        accTo.debit(sum);
                    }
                }
            }
        } else {
            synchronized (accTo) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (accFrom) {
                    if (accFrom.getBalance() >= sum) {
                        accFrom.credit(sum);
                        accTo.debit(sum);
                    }
                }
            }
        }

    }
}
