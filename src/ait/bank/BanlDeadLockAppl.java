package ait.bank;

import ait.bank.model.Account;
import ait.bank.service.Transfer;

public class BanlDeadLockAppl {
    public static void main(String[] args) throws InterruptedException {
        Account dad = new Account(10_000);
        Account son = new Account(20_000);

        dad.debit(1000);
        son.debit(1000);

        Transfer transfer1 = new Transfer(dad, son, 900);
        Transfer transfer2 = new Transfer(son, dad, 900);

        Thread thread1 = new Thread(transfer1);
        Thread thread2 = new Thread(transfer2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("Balance od acc: " + dad.getAccNumber() + " = " + dad.getBalance());
        System.out.println("Balance od acc: " + son.getAccNumber() + " = " + son.getBalance());
    }
}
