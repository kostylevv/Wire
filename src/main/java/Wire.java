/**
 * Created by vkostylev on 04/04/16.
 *
 * Race condition demonstartion.
 * See HFJ, chapter 15
 * Still don't have idea why they used t.sleep in original example...
 *
 */
public class Wire implements Runnable {

    private BankAccount bankAccount = new BankAccount();

    public static void main(String[] args) {
        Wire wire = new Wire();
        Thread one = new Thread(wire);
        Thread two = new Thread(wire);
        Thread three = new Thread(wire);
        one.setName("one");
        two.setName("two");
        three.setName("three");

        one.start();
        two.start();
        three.start();


    }

    public void run() {

        for (int i = 0; i < 10; i++) {
            withdraw(10);
            if (bankAccount.getAmmount() < 0) {
                System.out.println("Overdraft");
            }
        }

    }
    //delete synchronized and voila...
    private synchronized void withdraw(double ammount) {
        System.out.println(Thread.currentThread().getName() + " is about to withdraw");
        System.out.println("Balance prior: " + bankAccount.getAmmount());
        if (bankAccount.getAmmount() >= ammount) {
            bankAccount.withdraw(10);
            System.out.println(Thread.currentThread().getName() + " transact completed OK");
            System.out.println("Balance after: " + bankAccount.getAmmount());
        } else {
            System.out.println("Not enough for " + Thread.currentThread().getName());
        }
    }

    class  BankAccount {
        public int getAmmount() {
            return ammount;
        }

        public void setAmmount(int ammount) {
            this.ammount = ammount;
        }

        public void withdraw(int ammount){
            this.ammount = this.ammount-ammount;
        }

        private int ammount = 100;


    }


}
