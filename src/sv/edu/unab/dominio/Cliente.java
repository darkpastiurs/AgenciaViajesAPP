package sv.edu.unab.dominio;

import java.util.StringJoiner;

public class Cliente {

    private Long id;
    private Persona datosPersonales;

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(Persona datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add(datosPersonales.getNombre())
                .add(datosPersonales.getApellidoPaterno())
                .add(datosPersonales.getApellidoMaterno())
                .toString();
    }
}
