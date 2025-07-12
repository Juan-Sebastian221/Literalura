package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anoNacimiento;
    private Integer anoMuerte;

    public Autor() {
    }

    public Autor(String nombre, Integer anoNacimiento, Integer anoMuerte) {
        this.nombre = nombre;
        this.anoNacimiento = anoNacimiento;
        this.anoMuerte = anoMuerte;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public Integer getAnoMuerte() {
        return anoMuerte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return Objects.equals(nombre, autor.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
