package sv.edu.unab.dominio;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(schema = "avr", name = "empleados")
@SequenceGenerator(schema = "avr", sequenceName = "empleados_seq_id", name = "Empleado_id_seq", allocationSize = 1)
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Empleado_id_seq")
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "seguro")
    private String iSSS;
    @NotNull
    @Column(name = "afp")
    private String aFP;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Persona.class, optional = false)
    @JoinColumn(name = "idpersona", referencedColumnName = "id", unique = true)
    private Persona datosPersonales;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getiSSS() {
        return iSSS;
    }

    public void setiSSS(String iSSS) {
        this.iSSS = iSSS;
    }

    public String getaFP() {
        return aFP;
    }

    public void setaFP(String aFP) {
        this.aFP = aFP;
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
        if (!(o instanceof Empleado)) return false;

        Empleado empleado = (Empleado) o;

        return id.equals(empleado.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Empleado.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("iSSS='" + iSSS + "'")
                .add("aFP='" + aFP + "'")
                .add("datosPersonales=" + datosPersonales)
                .toString();
    }
}
