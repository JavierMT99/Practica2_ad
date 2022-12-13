package org.iesch.ad.deserializer;

import com.google.gson.*;
import org.iesch.ad.models.DatosClima;

import java.lang.reflect.Type;

public class Deserializador implements JsonDeserializer<DatosClima> {
    @Override
    public DatosClima deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String fecha = "0000-00-00";
        if (jsonObject.get("fecha") != null) {
            fecha = jsonObject.get("fecha").getAsString();
        }

        String indicativo = " ";
        if (jsonObject.get("indicativo") != null) {
            indicativo = jsonObject.get("indicativo").getAsString();
        }

        String nombre = " ";
        if (jsonObject.get("nombre") != null) {
            nombre = jsonObject.get("nombre").getAsString();
        }

        String provincia = " ";
        if (jsonObject.get("fecha") != null) {
            fecha = jsonObject.get("fecha").getAsString();
        }

        int altitud = 0;
        if (jsonObject.get("altitud") != null) {
            altitud = jsonObject.get("altitud").getAsInt();
        }

        double tmed = 0.0;
        if (jsonObject.get("tmed") != null) {
            tmed = Double.parseDouble(jsonObject.get("tmed").getAsString().replace(",", "."));
        }

        double prec = 0.0;
        try {
            if (jsonObject.get("prec") != null) {
                prec = Double.valueOf(jsonObject.get("prec").getAsString().replace(",", "."));
            }
        } catch (NumberFormatException e) {
            prec = 0.0;
        }

        double tmin = 0.0;
        if (jsonObject.get("tmin") != null) {
            tmin = Double.parseDouble(jsonObject.get("tmin").getAsString().replace(",", "."));
        }

        String horatmin = "00:00";
        if (jsonObject.get("horatmin") != null) {
            horatmin = jsonObject.get("horatmin").getAsString();
        }

        double tmax = 0.0;
        if (jsonObject.get("tmax") != null) {
            tmax = Double.parseDouble(jsonObject.get("tmax").getAsString().replace(",", "."));
        }

        String horatmax = "00:00";
        if (jsonObject.get("horatmax") != null) {
            horatmax = jsonObject.get("horatmax").getAsString();
        }

        Integer dir = 0;
        if (jsonObject.get("dir") != null) {
            dir = jsonObject.get("dir").getAsInt();
        }

        double velmedia = 0.0;
        if (jsonObject.get("velmedia") != null) {
            velmedia = Double.parseDouble(jsonObject.get("velmedia").getAsString().replace(",", "."));
        }

        double racha = 0.0;
        if (jsonObject.get("racha") != null) {
            racha = Double.parseDouble(jsonObject.get("racha").getAsString().replace(",", "."));
        }

        String horaracha = "00:00";
        if (jsonObject.get("horaracha") != null) {
            horaracha = jsonObject.get("horaracha").getAsString();
        }

        double sol = 0.0;
        if (jsonObject.get("sol") != null) {
            sol = Double.parseDouble(jsonObject.get("sol").getAsString().replace(",", "."));
        }

        double presMax = 0.0;
        if (jsonObject.get("presMax") != null) {
            presMax = Double.parseDouble(jsonObject.get("presMax").getAsString().replace(",", "."));
        }

        String horapresMax = "00:00";
        if (jsonObject.get("horapresMax") != null) {
            horapresMax = jsonObject.get("horapresMax").getAsString();
        }

        double presMin = 0.0;
        if (jsonObject.get("presMin") != null) {
            presMin = Double.parseDouble(jsonObject.get("presMin").getAsString().replace(",", "."));
        }

        String horapresMin = "00:00";
        if (jsonObject.get("horapresMin") != null) {
            horapresMin = jsonObject.get("horapresMin").getAsString();
        }

        return new DatosClima(
                fecha, indicativo, nombre, provincia, altitud, tmed, prec, tmin,
                horatmin, tmax, horatmax, dir, velmedia, racha, horaracha, sol,
                presMax, horapresMax, presMin, horapresMin);
    }
}