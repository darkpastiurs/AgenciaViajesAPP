package sv.edu.unab.dominio;

import org.eclipse.persistence.annotations.CompositeMember;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Entity
@Table(schema = "avr", name = "clientes")
public class Cliente {

    @Id
    private Long id;
    @Size(min = 9)
    @Column(name = "pasaporte")
    private String pasaporte;
    @Size(min = 9)
    @Column(name = "visa")
    private String visa;
    //Para mapear la relacion una a una con persona
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Persona.class)
    //Esta atributo sirver para establecer que la misma id de la entidad de persona
    @MapsId("id")
    //Une el atributo id de cliente con el la columna idpersona en la base de datos
    @JoinColumn(name = "idpersona")
    private Persona datosPersonales;

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(String pasaporte) {
        this.pasaporte = pasaporte;
    }

    public String getVisa() {
        return visa;
    }

    public void setVisa(String visa) {
        this.visa = visa;
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
