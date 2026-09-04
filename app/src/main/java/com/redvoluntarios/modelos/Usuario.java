package com.redvoluntarios.modelos;

/**
 * CLASE MODELO / ENTIDAD: Usuario
 * ============================================================================
 * Representa a un usuario del sistema (Adulto Mayor o Voluntario).
 * En la arquitectura Android, las clases de modelo (POJO - Plain Old Java Object)
 * encapsulan los datos y estado que se persisten localmente en SQLite o remotamente en Firebase.
 */
public class Usuario {

    // Atributos de entidad
    private int idUsuario;
    private String nombre;
    private String rut;
    private String rol; // Identificador de rol: 'ADULTO_MAYOR' o 'VOLUNTARIO'
    private String telefono;
    private String direccion;

    /**
     * Constructor vacío requerido para operaciones de reflexión y deserialización.
     */
    public Usuario() {
    }

    /**
     * Constructor completo para instancias recuperadas desde base de datos.
     */
    public Usuario(int idUsuario, String nombre, String rut, String rol, String telefono, String direccion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rut = rut;
        this.rol = rol;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /**
     * Constructor sin ID para crear nuevos usuarios antes de registrarlos en BD.
     */
    public Usuario(String nombre, String rut, String rol, String telefono, String direccion) {
        this.nombre = nombre;
        this.rut = rut;
        this.rol = rol;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Métodos Encapsuladores (Getters y Setters)
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", rol='" + rol + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}