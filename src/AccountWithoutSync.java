import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountWithoutSync {
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
        private int balance = 0;

        public int gatBalance(){
            return balance;
        }

        public void deposit(int amount){
            int newBalance = balance + amount;
            try {
                Thread.sleep(5);
            }catch (InterruptedException e){}

            balance = newBalance;
        }
    }
}
