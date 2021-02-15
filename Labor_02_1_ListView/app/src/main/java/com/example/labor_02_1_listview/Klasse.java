package com.example.labor_02_1_listview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.ClosedByInterruptException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Klasse {
    private String name;
    private Set<Schueler> schueler;

    public static List<Klasse> readKlassen(InputStream inputStream) {
        List<Schueler> schueler;
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
             return stream
                    .map(Schueler::returnValidOrNull)
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(Schueler::getKlasse, Collectors.toSet()))
                    .entrySet()
                    .stream()
                    .map(e -> new Klasse(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        }
    }

    public Klasse(String name) {
        this.name = Objects.requireNonNull(name);
        this.schueler = new HashSet<>();
    }

    public Klasse(String name, Collection<Schueler> schueler) {
        this(name);
        this.schueler.addAll(schueler);
    }

    public boolean addSchueler(Schueler s) {
        return schueler.add(Objects.requireNonNull(s));
    }

    public boolean removeSchueler(Schueler s) {
        return schueler.remove(Objects.requireNonNull(s));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Klasse klasse = (Klasse) o;
        return Objects.equals(name, klasse.name) &&
                Objects.equals(schueler, klasse.schueler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, schueler);
    }
}
