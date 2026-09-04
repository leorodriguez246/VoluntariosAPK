package com.redvoluntarios.modelos;

/**
 * CLASE MODELO / ENTIDAD: Review
 * ============================================================================
 * Almacena las puntuaciones (1.0 a 5.0 estrellas) y reseñas escritas
 * para calificar la experiencia y el trato entre usuarios.
 */
public class Review {

    private int idReview;
    private int idSolicitud;
    private int idEvaluador;
    private int idEvaluado;
    private String nombreEvaluador;
    private String nombreEvaluado;
    private String rolEvaluado; // 'VOLUNTARIO' o 'ADULTO_MAYOR'
    private float puntuacion; // Puntuación de 1.0 a 5.0 estrellas
    private String comentario;
    private String fecha;

    public Review() {
    }

    public Review(int idReview, int idSolicitud, int idEvaluador, int idEvaluado,
                  String nombreEvaluador, String nombreEvaluado, String rolEvaluado,
                  float puntuacion, String comentario, String fecha) {
        this.idReview = idReview;
        this.idSolicitud = idSolicitud;
        this.idEvaluador = idEvaluador;
        this.idEvaluado = idEvaluado;
        this.nombreEvaluador = nombreEvaluador;
        this.nombreEvaluado = nombreEvaluado;
        this.rolEvaluado = rolEvaluado;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    // Métodos Encapsuladores (Getters y Setters)
    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getIdEvaluador() {
        return idEvaluador;
    }

    public void setIdEvaluador(int idEvaluador) {
        this.idEvaluador = idEvaluador;
    }

    public int getIdEvaluado() {
        return idEvaluado;
    }

    public void setIdEvaluado(int idEvaluado) {
        this.idEvaluado = idEvaluado;
    }

    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    public String getRolEvaluado() {
        return rolEvaluado;
    }

    public void setRolEvaluado(String rolEvaluado) {
        this.rolEvaluado = rolEvaluado;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", idSolicitud=" + idSolicitud +
                ", nombreEvaluado='" + nombreEvaluado + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}