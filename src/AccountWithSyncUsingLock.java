import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class AccountWithSyncUsingLock {
    private static Account account = new Account();

    public static void main(String[] args){

        ExecutorService executer = Executors.newCachedThreadPool();

        for(int i = 0 ; i < 100 ; i++){
            executer.execute(new AddAPennyTask());
        }

        executer.shutdown();

        while (!executer.isTerminated()){}

        System.out.println("What is balance? " + account.balance);

    }

    private static class AddAPennyTask implements Runnable{
        public void run(){
            account.deposit(1);
        }
    }

    private static class Account{
        //create lock
        private static Lock lock = new ReentrantLock();
        private int balance = 10;

        public int gatBalance(){
            return balance;
        }

        public void deposit(int amount){
            lock.lock();
            try {
                int newBalance = balance + amount;
                Thread.sleep(5);
                balance = newBalance;
            }catch (InterruptedException e){}
            finally {
                lock.unlock();
            }
        }
    }
}

