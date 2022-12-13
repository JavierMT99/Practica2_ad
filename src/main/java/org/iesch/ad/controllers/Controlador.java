package org.iesch.ad.controllers;

import org.iesch.ad.models.DatosUnizar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Controlador {
    private final String uri;
    private static Controlador ctrl;
    private Document data;

    public Controlador(String uri) {
        this.uri = uri;
    }

    public void initData() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder;
        dbBuilder = dbFactory.newDocumentBuilder();
        this.data = dbBuilder.newDocument();

        Element rootElement = this.data.createElement("Datos_Unizar");
        this.data.appendChild(rootElement);
    }

    public void loadData() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(this.uri);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        dBuilder = dbFactory.newDocumentBuilder();
        this.data = dBuilder.parse(xmlFile);
        this.data.getDocumentElement().normalize();
    }

    private Transformer preProcess() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }

    public void writeXMLFile(String uri) throws TransformerException {
        Transformer transformer = this.preProcess();
        DOMSource source = new DOMSource(this.data);
        StreamResult file = new StreamResult(new File(uri));
        transformer.transform(source, file);
    }

    public void addDatosUnizar(DatosUnizar data) {
        Element rootElement = (Element) this.data.getElementsByTagName("Datos_Unizar").item(0);
        rootElement.appendChild(createUserElement(data));
    }

    private Node createUserElements(Element element, String name, String value) {
        Element node = this.data.createElement(name);
        node.appendChild(this.data.createTextNode(value));
        return node;
    }

    private Node createUserElement(DatosUnizar data) {
        Element userElement = this.data.createElement("Dato_Unizar");

        userElement.appendChild(createUserElements(userElement, "Curso_Academico", String.valueOf(data.getCurso_academico())));
        userElement.appendChild(createUserElements(userElement, "Tipo_Estudio", data.getTipo_estudio()));
        userElement.appendChild(createUserElements(userElement, "Estudio", data.getEstudio()));
        userElement.appendChild(createUserElements(userElement, "Localidad", data.getLocalidad()));
        userElement.appendChild(createUserElements(userElement, "Centro", data.getCentro()));
        userElement.appendChild(createUserElements(userElement, "Asignatura", data.getAsignatura()));
        userElement.appendChild(createUserElements(userElement, "Tipo_Asignatura", data.getTipo_asignatura()));
        userElement.appendChild(createUserElements(userElement, "Clase_Asignatura", data.getClase_asignatura()));
        userElement.appendChild(createUserElements(userElement, "Fecha_Actualizacion", data.getFecha_actualizacion()));
        userElement.appendChild(createUserElements(userElement, "Alumnos_Evaluados", String.valueOf(data.getAlumnos_evaluados())));
        userElement.appendChild(createUserElements(userElement, "Alumnos_Superados", String.valueOf(data.getAlumnos_superados())));
        userElement.appendChild(createUserElements(userElement, "Alumnos_Presentados", String.valueOf(data.getAlumnos_presentados())));

        try {
            userElement.appendChild(createUserElements(userElement, "Tasa_Exito", String.valueOf(data.getTasa_exito())));
        } catch (NullPointerException e) {
            userElement.appendChild(createUserElements(userElement, "Tasa_Exito", null));
        }

        try {
            userElement.appendChild(createUserElements(userElement, "Tasa_Rendimiento", String.valueOf(data.getTasa_rendimiento())));
        } catch (NullPointerException e) {
            userElement.appendChild(createUserElements(userElement, "Tasa_Rendimiento", null));
        }

        try {
            userElement.appendChild(createUserElements(userElement, "Tasa_Evaluacion", String.valueOf(data.getTasa_evaluacion())));
        } catch (NullPointerException e) {
            userElement.appendChild(createUserElements(userElement, "Tasa_Evaluacion", null));
        }

        try {
            userElement.appendChild(createUserElements(userElement, "Media_Convocatorias_Consumidas", String.valueOf(data.getMedia_convocatorias_consumidas())));
        } catch (NullPointerException e) {
            userElement.appendChild(createUserElements(userElement, "Media_Convocatorias_Consumidas", null));
        }

        return userElement;
    }
}