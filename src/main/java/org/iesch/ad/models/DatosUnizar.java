package org.iesch.ad.models;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatosUnizar {

    private Integer curso_academico;
    private String tipo_estudio;
    private String estudio;
    private String localidad;
    private String centro;
    private String asignatura;
    private String tipo_asignatura;
    private String clase_asignatura;
    private Double tasa_exito;
    private Double tasa_rendimiento;
    private Double tasa_evaluacion;
    private Integer alumnos_evaluados;
    private Integer alumnos_superados;
    private Integer alumnos_presentados;
    private Double media_convocatorias_consumidas;
    private String fecha_actualizacion;
}