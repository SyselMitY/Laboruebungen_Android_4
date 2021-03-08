package cf.soisi.tankmesser.db;

import java.time.LocalDate;
import java.util.Objects;

public class Tankvorgang {
    private final LocalDate datum;
    private final int kmAlt;
    private final int kmNeu;
    private final double menge;
    private final double preis;
    private final Long id;

    Tankvorgang(LocalDate datum, int kmAlt, int kmNeu, double menge, double preis, Long id) {
        if (kmAlt > kmNeu || kmAlt < 0 || kmNeu < 0 || menge <= 0 || preis <= 0)
            throw new IllegalArgumentException();

        this.datum = Objects.requireNonNull(datum);
        this.kmAlt = kmAlt;
        this.kmNeu = kmNeu;
        this.menge = menge;
        this.preis = preis;
        this.id = id;
    }

    public Tankvorgang(LocalDate datum, int kmAlt, int kmNeu, double menge, double preis) {
        this(datum,kmAlt,kmNeu,menge,preis,null);
    }

    public LocalDate getDatum() {
        return datum;
    }

    public int getKmAlt() {
        return kmAlt;
    }

    public int getKmNeu() {
        return kmNeu;
    }

    public double getMenge() {
        return menge;
    }

    public double getPreis() {
        return preis;
    }

    public Long getId() {
        return id;
    }
}
