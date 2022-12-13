package org.iesch.ad.models;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatosClima {
    public String fecha;
    public String indicativo;
    public String nombre;
    public String provincia;
    public Integer altitud;
    public Double tmed;
    public Double prec;
    public Double tmin;
    public String horatmin;
    public Double tmax;
    public String horatmax;
    public Integer dir;
    public Double velmedia;
    public Double racha;
    public String horaracha;
    public Double sol;
    public Double presMax;
    public String horaPresMax;
    public Double presMin;
    public String horaPresMin;
}