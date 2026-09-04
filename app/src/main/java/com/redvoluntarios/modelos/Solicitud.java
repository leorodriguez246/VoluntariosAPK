package com.redvoluntarios.modelos;

/**
 * CLASE MODELO / ENTIDAD: Solicitud
 * ============================================================================
 * Representa una petición de ayuda creada por un Adulto Mayor y atendida por un Voluntario.
 * Almacena los metadatos completos requeridos para el seguimiento de la tarea.
 */
public class Solicitud {

    // Identificadores y datos del creador
    private int idSolicitud;
    private int idCreador;
    private String nombreCreador;
    private String telefonoCreador;
    private String direccion;

    // Parámetros de la necesidad
    private String prioridad; // 'Normal' o 'URGENTE'
    private String categoria; // 'Medicamentos', 'Mercadería', 'Compañía', 'Trámites'
    private String descripcion;
    private double latitud;
    private double longitud;
    private String fechaHora;
    private String estado; // 'PENDIENTE', 'EN_PROCESO', 'COMPLETADA'

    // Seguimiento del voluntario asignado y evidencias
    private int idVoluntario;
    private String nombreVoluntario;
    private String tareasRealizadas;
    private String fotoEvidencia;

    public Solicitud() {
    }

    public Solicitud(int idSolicitud, int idCreador, String nombreCreador, String telefonoCreador,
                     String direccion, String prioridad, String categoria, String descripcion,
                     double latitud, double longitud, String fechaHora, String estado) {
        this.idSolicitud = idSolicitud;
        this.idCreador = idCreador;
        this.nombreCreador = nombreCreador;
        this.telefonoCreador = telefonoCreador;
        this.direccion = direccion;
        this.prioridad = prioridad;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Métodos Encapsuladores (Getters y Setters)
    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getTelefonoCreador() {
        return telefonoCreador;
    }

    public void setTelefonoCreador(String telefonoCreador) {
        this.telefonoCreador = telefonoCreador;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(int idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public String getNombreVoluntario() {
        return nombreVoluntario;
    }

    public void setNombreVoluntario(String nombreVoluntario) {
        this.nombreVoluntario = nombreVoluntario;
    }

    public String getTareasRealizadas() {
        return tareasRealizadas;
    }

    public void setTareasRealizadas(String tareasRealizadas) {
        this.tareasRealizadas = tareasRealizadas;
    }

    public String getFotoEvidencia() {
        return fotoEvidencia;
    }

    public void setFotoEvidencia(String fotoEvidencia) {
        this.fotoEvidencia = fotoEvidencia;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "idSolicitud=" + idSolicitud +
                ", nombreCreador='" + nombreCreador + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}