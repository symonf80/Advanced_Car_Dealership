import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarDealership {
    private static final int QUANTITY_CARS = 10;
    private static final int RECEIVE_TIME = 2500;
    private static final int SELL_TIME = 1000;
    private final List<Automaker> automakers = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void receiveCar() {
        for (int i = 0; i < QUANTITY_CARS; i++) {
            try {
                Thread.sleep(RECEIVE_TIME);
                automakers.add(new Automaker());
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " выпустил 1 авто.");
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон.");
            while (automakers.isEmpty()) {
                System.out.println("Машин нет,санкции!");
                condition.await();
            }
            Thread.sleep(SELL_TIME);
            System.out.println(Thread.currentThread().getName() + " умчал на новом авто.");
            automakers.remove(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
