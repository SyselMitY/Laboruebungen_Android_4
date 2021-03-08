package cf.soisi.tankmesser.db;

import java.time.LocalDate;
import java.util.Objects;

public class Tankvorgang {
    private final LocalDate date;
    private final int kmOld;
    private final int kmNew;
    private final double amount;
    private final double price;
    private final Long id;

    Tankvorgang(LocalDate date, int kmOld, int kmNew, double amount, double price, Long id) {
        if (kmOld > kmNew || kmOld < 0 || kmNew < 0 || amount <= 0 || price <= 0)
            throw new IllegalArgumentException();

        this.date = Objects.requireNonNull(date);
        this.kmOld = kmOld;
        this.kmNew = kmNew;
        this.amount = amount;
        this.price = price;
        this.id = id;
    }

    public Tankvorgang(LocalDate date, int kmOld, int kmNew, double amount, double price) {
        this(date, kmOld, kmNew, amount, price,null);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getKmOld() {
        return kmOld;
    }

    public int getKmNew() {
        return kmNew;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }
}
