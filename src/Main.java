public class Main {
    public static final int CUSTOMER = 10;

    public static void main(String[] args) {
        final CarDealership carDealership = new CarDealership();
        for (int i = 1; i <= CUSTOMER; i++) {
            new Thread(null, carDealership::sellCar, "Покупатель " + i).start();
        }
        new Thread(null, carDealership::receiveCar, "Производитель Audi").start();
    }
}
