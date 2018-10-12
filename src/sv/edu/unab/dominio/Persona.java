package sv.edu.unab.dominio;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(schema = "avr", name = "personas")
@SequenceGenerator(schema = "avr", sequenceName = "personas_id_seq", name = "Persona_seq_id", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
        @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre")
})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Persona_seq_id")
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidomaterno")
    private String apellidoMaterno;
    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "apellidopaterno")
    private String apellidoPaterno;
    @NotNull
    @Column(name = "genero")
    private Character genero;
    @NotNull
    @Column(name = "dui")
    private String dui;
    @NotNull
    @Column(name = "nit")
    private String nit;
    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "fechanacimiento")
    private LocalDate fechaNacimiento;
    @NotNull
    @Size(min = 9)
    @Column(name = "telefono")
    private String telefono;
    @Lob
    @NotNull
    @Column(name = "direccion")
    private String direccion;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotNull
    @Column(name = "email")
    private String email;

    //Mapea la relacion con Cliente
    @OneToOne(mappedBy = "datosPersonales", fetch = FetchType.LAZY, targetEntity = Cliente.class)
    //Une id de la entidad persona para ser el mismo para la entidad cliente
    @PrimaryKeyJoinColumn
    private Cliente cliente;
    //Mapea la relacion con Empleado
    @OneToOne(mappedBy = "datosPersonales", fetch = FetchType.LAZY, targetEntity = Empleado.class)
    //Une id de la entidad persona ser el mismo para la entidad empleado
    @PrimaryKeyJoinColumn
    private Empleado empleado;

    public Persona() {
    }

    public Persona(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;

        Persona persona = (Persona) o;

        return id.equals(persona.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add(nombre)
                .add(apellidoPaterno)
                .add(apellidoMaterno)
                .toString();
    }
}
