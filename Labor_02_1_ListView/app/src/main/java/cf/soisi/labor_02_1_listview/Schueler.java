package cf.soisi.labor_02_1_listview;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Schueler implements Serializable, Comparable<Schueler> {
    private int nummer;
    private String vorname;
    private String nachname;
    private char geschlecht;
    private String klasse;

    public Schueler(int nummer, String vorname, String nachname, char geschlecht, String klasse) {
        this.nummer = nummer;
        this.vorname = Objects.requireNonNull(vorname);
        this.nachname = Objects.requireNonNull(nachname);
        this.geschlecht = geschlecht;
        this.klasse = Objects.requireNonNull(klasse);
    }

    public static Schueler getSchuelerFromCSV(String line) {
        String[] splitted = line.split(";");
        if(splitted.length != 5)
            throw new IllegalArgumentException("Invalid field count (must be 5)");
        if (splitted[4].length() != 1)
            throw new IllegalArgumentException("Geschlecht must be a char");

        int nummer = Integer.parseInt(splitted[0]);
        String klasse = splitted[1];
        String nachname = splitted[2];
        String vorname = splitted[3];
        char geschlecht = splitted[4].charAt(0);
        return new Schueler(nummer, vorname, nachname, geschlecht, klasse);
    }

    public static Schueler returnValidOrNull(String line) {
        try {
            return getSchuelerFromCSV(line);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public int getNummer() {
        return nummer;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public String getKlasse() {
        return klasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schueler schueler = (Schueler) o;
        return nummer == schueler.nummer &&
                geschlecht == schueler.geschlecht &&
                Objects.equals(vorname, schueler.vorname) &&
                Objects.equals(nachname, schueler.nachname) &&
                Objects.equals(klasse, schueler.klasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nummer, vorname, nachname, geschlecht, klasse);
    }

    @Override
    public String toString() {
        return String.format("%2d %s %s",nummer,nachname,vorname);
    }

    @Override
    public int compareTo(Schueler o) {
        return Comparator.comparing(Schueler::getNummer).compare(this,o);
    }
}
