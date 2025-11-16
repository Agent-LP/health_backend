package com.pdg.health_back.entities;

import java.io.Serializable;
import java.util.Objects;

public class HabitoCategoriaId implements Serializable {
    private Integer idHabito;
    private Integer idCategoria;

    public HabitoCategoriaId() {
    }

    public HabitoCategoriaId(Integer idHabito, Integer idCategoria) {
        this.idHabito = idHabito;
        this.idCategoria = idCategoria;
    }

    public Integer getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(Integer idHabito) {
        this.idHabito = idHabito;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitoCategoriaId that = (HabitoCategoriaId) o;
        return Objects.equals(idHabito, that.idHabito) &&
                Objects.equals(idCategoria, that.idCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHabito, idCategoria);
    }
}



