package org.iesch.ad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.iesch.ad.controllers.Controlador;
import org.iesch.ad.deserializer.Deserializador;
import org.iesch.ad.models.DatosClima;
import org.iesch.ad.models.DatosUnizar;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static final String DELIMITER = ";";
    public static ArrayList<DatosUnizar> lstData = new ArrayList<>();
    public static List<DatosClima> lstClimaData = new ArrayList<>();

    static final String IP = "localhost";
    static final String PUERTO = "3306";
    static final String BBDD = "clima";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String CADENA_CONEXION = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + BBDD;

    public static String fecha;
    public static String indicativo;
    public static String nombre;
    public static String provincia;
    public static Integer altitud;
    public static Double tmed;
    public static Double prec;
    public static Double tmin;
    public static String horatmin;
    public static Double tmax;
    public static String horatmax;
    public static Integer dir;
    public static Double velmedia;
    public static Double racha;
    public static String horaracha;
    public static Double sol;
    public static Double presMax;
    public static String horaPresMax;
    public static Double presMin;
    public static String horaPresMin;

    public static void main(String[] args) throws SQLException {
        // Llamamos al metodo encargado de cargar la interfaz del usuario que llamara a los distintos metodos para realizar los ejercicios
        interfazUsuario();
    }

    // Metodo encargado de cargar la interfaz del usuario que llamara a los distintos metodos para realizar los ejercicios
    private static void interfazUsuario() throws SQLException {
        int option = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una opción del 1 al 3");
        System.out.println("1.- Ejercicio 1" + "\n" + "2.- Ejercicio 2" + "\n" + "3.- Insertar datos en la BBDD" + "\n");

        if (!sc.hasNextInt()) {
            System.out.println("Error, debes introducir una opción del 1 al 3");
            sc.nextLine();
        } else {
            option = Integer.parseInt(sc.nextLine());
        }
        switch (option) {
            case 1:
                ejercicio1();
                break;
            case 2:
                ejercicio2();
                break;
            case 3:
                cargarTeruelJSON();
                cargarCalandaJSON();
                cargarCalamochaJSON();
                cargarAlcanizJSON();
                System.out.println("Se han cargado los datos de: (Teruel.json, Alcañiz.json, calanda.json y calamocha.json) en memoria \n");
                insertarBBDD();
                consultasEjercicio2BBDD();
                break;
            default:
                break;
        }
    }

    // Metodo encargado de hacer las consultas del ejercicio 2 pero atacando a la base de datos y no a los datos en memoria
    private static void consultasEjercicio2BBDD() {
        String sql;
        try {
            Connection miConexion = DriverManager.getConnection(CADENA_CONEXION, USER, PASS);
            Statement miStatement = miConexion.createStatement();

            // Tiempo inicial de ejecucion
            Long timeExIni = System.currentTimeMillis();

            // Consulta - 01 Temperatura mínima. Muestra el lugar donde se produjo, la fecha y la hora
            System.out.println("Consulta - 01 Temperatura mínima. Muestra el lugar donde se produjo, la fecha y la hora");
            sql = "SELECT tmin, nombre , fecha, horatmin FROM datosclima ORDER BY tmin ASC LIMIT 1";
            ResultSet miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + miResultSet.getString(2) +
                        miResultSet.getString(3) + miResultSet.getString(4) + "\n");
            }

            // Consulta - 02 Temperatura máxima. Muestra el lugar donde se produjo, la fecha y la hora
            System.out.println("Consulta - 2. Temperatura máxima. Muestra el lugar donde se produjo, la fecha y la hora");
            sql = "SELECT tmax, nombre, fecha, horatmax FROM datosclima ORDER BY tmax  DESC LIMIT 1";
            miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2) + " " +
                        miResultSet.getString(3) + " " + miResultSet.getString(4) + "\n");
            }

            // Consulta - 03 Precipitaciones máximas. Muestra el lugar donde se produjo, la fecha y la hora
            System.out.println("Consulta - 03 Precipitaciones máximas. Muestra el lugar donde se produjo, la fecha y la hora");
            sql = "SELECT prec, nombre, fecha FROM datosclima ORDER BY prec  DESC LIMIT 1";
            miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2) + " " +
                        miResultSet.getString(3) + "\n");
            }

            // Consulta - 04 Presión atmosférica mayor, Muestra el lugar donde se produjo, la fecha y la hora
            System.out.println("Consulta - 04 Presión atmosférica mayor, Muestra el lugar donde se produjo, la fecha y la hora");
            sql = "SELECT presMax, nombre, fecha, horapresmax FROM datosclima ORDER BY presMax  DESC LIMIT 1";
            miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2) + " " +
                        miResultSet.getString(3) + " " + miResultSet.getString(4) + "\n");
            }

            // Consulta - 05 Mayor racha de viento. Muestra el lugar donde se produjo, la fecha y la hora
            System.out.println("Consulta - 05 Mayor racha de viento. Muestra el lugar donde se produjo, la fecha y la hora");
            sql = "SELECT racha, nombre, fecha, horaracha FROM datosclima ORDER BY racha  DESC LIMIT 1";
            miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2) + " " +
                        miResultSet.getString(3) + " " + miResultSet.getString(4) + "\n");
            }

            // Consulta - 06 Mayor velocidad media del viento. Muestra el lugar donde se produjo, la fecha y la hora
            System.out.println("Consulta - 06 Mayor velocidad media del viento. Muestra el lugar donde se produjo, la fecha y la hora");
            sql = "SELECT velmedia, nombre, fecha FROM datosclima ORDER BY velmedia  DESC LIMIT 1";
            miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2) + " " +
                        miResultSet.getString(3) + "\n");
            }
            // Consulta - 07 Calcula el total de precipitaciones producidas
            System.out.println("Consulta - 07 Calcula el total de precipitaciones producidas");
            sql = "SELECT sum(prec) FROM datosclima";
            miResultSet = miStatement.executeQuery(sql);
            while (miResultSet.next()) {
                System.out.println(miResultSet.getString(1) + "\n");
            }

            // Tiempo final de ejecucion
            Long timeExEnd = System.currentTimeMillis();
            System.out.println("El tiempo de ejecución de las consultas en local ha sido de " + (timeExEnd - timeExIni) + " milisegundos");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo encargado de insertar los datos en la base de datos
    private static void insertarBBDD() throws SQLException {
        String sql = "INSERT INTO DatosClima (fecha,indicativo,nombre,provincia,altitud,tmed,prec,tmin,horatmin,tmax,horatmax,dir,velmedia,racha,horaracha,sol,presMax,horaPresMax,presMin,horaPresMin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection miConexion = DriverManager.getConnection(CADENA_CONEXION, USER, PASS);
        PreparedStatement miStatement = miConexion.prepareStatement(sql);
        try {
            miConexion.setAutoCommit(false);
            for (DatosClima dc : lstClimaData
            ) {
                fecha = dc.getFecha();
                miStatement.setString(1, fecha);

                indicativo = dc.getIndicativo();
                miStatement.setString(2, indicativo);

                nombre = dc.getNombre();
                miStatement.setString(3, nombre);

                provincia = dc.getProvincia();
                miStatement.setString(4, provincia);

                altitud = dc.getAltitud();
                miStatement.setInt(5, altitud);

                tmed = dc.getTmed();
                miStatement.setDouble(6, tmed);

                prec = dc.getPrec();
                miStatement.setDouble(7, prec);

                tmin = dc.getTmin();
                miStatement.setDouble(8, tmin);

                horatmin = dc.getHoratmin();
                miStatement.setString(9, horatmin);

                tmax = dc.getTmax();
                miStatement.setDouble(10, tmax);

                horatmax = dc.getHoratmax();
                miStatement.setString(11, horatmax);

                dir = dc.getDir();
                miStatement.setInt(12, dir);

                velmedia = dc.getVelmedia();
                miStatement.setDouble(13, velmedia);

                racha = dc.getRacha();
                miStatement.setDouble(14, racha);

                horaracha = dc.getHoraracha();
                miStatement.setString(15, horaracha);

                sol = dc.getSol();
                miStatement.setDouble(16, sol);

                presMax = dc.getPresMax();
                miStatement.setDouble(17, presMax);

                horaPresMax = dc.getHoraPresMax();
                miStatement.setString(18, horaPresMax);

                presMin = dc.getPresMin();
                miStatement.setDouble(19, presMin);

                horaPresMin = dc.getHoraPresMin();
                miStatement.setString(20, horaPresMin);

                miStatement.executeUpdate();
            }

            miConexion.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            miConexion.setAutoCommit(true);
            miStatement.close();
            miConexion.close();
        }
    }

    // Metodo encargado de realizar el ejercicio 1
    private static void ejercicio1() {
        leerCSVyEscribirXML();
        mostrarConsultasPantallaYTxt();
        construirJSONIngenieriaInfoTeruel();
    }

    // Metodo encargado de realizar el ejercicio 2
    private static void ejercicio2() {
        cargarTeruelJSON();
        cargarCalandaJSON();
        cargarCalamochaJSON();
        cargarAlcanizJSON();
        System.out.println("Se han cargado los datos de: (Teruel.json, Alcañiz.json, calanda.json y calamocha.json) en memoria \n");
        consultasClima();
    }

    // Metodo encargado de hacer las consultas del ejercicio 2 con los datos en memoria
    private static void consultasClima() {
        // Tiempo inicial de ejecucion
        Long timeExIni = System.currentTimeMillis();

        // Consulta 01 - Temperatura mínima. Muestra el lugar donde se produjo, la fecha y la hora
        System.out.println("Consulta 01 - Temperatura mínima. Muestra el lugar donde se produjo, la fecha y la hora");
        DatosClima consultaClima01 = lstClimaData.stream().min(Comparator.comparingDouble(v -> v.getTmin())).get();
        System.out.println("Se produjo en: " + consultaClima01.getNombre() + " a las " + consultaClima01.getHoratmin() + " en la fecha " + consultaClima01.getFecha() + "\n");

        // Consulta 02 - Temperatura máxima.Muestra el lugar donde se produjo, la fecha y la hora
        System.out.println("Consulta 02 - Temperatura máxima.Muestra el lugar donde se produjo, la fecha y la hora");
        DatosClima consultaClima02 = lstClimaData.stream().max(Comparator.comparingDouble(v -> v.getTmax())).get();
        System.out.println("Se produjo en: " + consultaClima02.getNombre() + " a las " + consultaClima02.getHoratmax() + " en la fecha " + consultaClima02.getFecha() + "\n");

        // Consulta 03 - Precipitaciones máximas. Muestra el lugar donde se produjo, la fecha y la hora
        System.out.println("Consulta 03 - Precipitaciones máximas. Muestra el lugar donde se produjo, la fecha y la hora");
        DatosClima consultaClima03 = lstClimaData.stream().max(Comparator.comparingDouble(v -> v.getPrec())).get();
        System.out.println("Se produjeron en: " + consultaClima03.getNombre() + " en la fecha " + consultaClima03.getFecha() + " .No existen registros de la hora" + "\n");

        // Consulta 04 - Presión atmosférica mayor, Muestra el lugar donde se produjo, la fecha y la hora
        System.out.println("Consulta 04 - Presión atmosférica mayor, Muestra el lugar donde se produjo, la fecha y la hora");
        DatosClima consultaClima04 = lstClimaData.stream().max(Comparator.comparingDouble(v -> v.getPresMax())).get();
        System.out.println("Se produjo en: " + consultaClima04.getNombre() + " a las " + consultaClima04.getHoratmax() + " en la fecha " + consultaClima04.getFecha() + "\n");

        // Consulta 05 - Mayor racha de viento. Muestra el lugar donde se produjo, la fecha y la hora
        System.out.println("Consulta 05 - Mayor racha de viento. Muestra el lugar donde se produjo, la fecha y la hora");
        DatosClima consultaClima05 = lstClimaData.stream().max(Comparator.comparingDouble(v -> v.getRacha())).get();
        System.out.println("Se produjo en: " + consultaClima05.getNombre() + " a las " + consultaClima05.getHoratmax() + " en la fecha " + consultaClima05.getFecha() + "\n");

        // Consulta 06 - Mayor velocidad media del viento. Muestra el lugar donde se produjo, la fecha y la hora
        System.out.println("Consulta 06 - Mayor velocidad media del viento. Muestra el lugar donde se produjo, la fecha y la hora");
        DatosClima consultaClima06 = lstClimaData.stream().max(Comparator.comparingDouble(v -> v.getVelmedia())).get();
        System.out.println("Se produjo en: " + consultaClima06.getNombre() + " a las " + consultaClima06.getHoratmax() + " en la fecha " + consultaClima06.getFecha() + "\n");

        // Consulta 07 - Calcula el total de precipitaciones producidas
        System.out.println("Consulta 07 - Calcula el total de precipitaciones producidas");
        Double consultaClima07 = lstClimaData.stream().mapToDouble(v -> v.getPrec()).sum();
        System.out.println("El total de precipitaciones es: " + consultaClima07 + "\n");

        // Tiempo final de ejecucion
        Long timeExEnd = System.currentTimeMillis();
        System.out.println("El tiempo de ejecución de las consultas en local ha sido de " + (timeExEnd - timeExIni) + " milisegundos");
    }

    // Metodo encargado de cargar los datos del JSON de Alcañiz
    private static void cargarAlcanizJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().serializeNulls().registerTypeAdapter(DatosClima.class, new Deserializador());
        Gson gson = gsonBuilder.serializeNulls().create();
        try (Reader reader = new FileReader("data/Alcañiz.json")) {
            List<DatosClima> datosAlcañiz = gson.fromJson(reader, new TypeToken<List<DatosClima>>() {
            }.getType());
            lstClimaData.addAll(datosAlcañiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo encargado de cargar los datos del JSON de Calamocha
    private static void cargarCalamochaJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().serializeNulls().registerTypeAdapter(DatosClima.class, new Deserializador());
        Gson gson = gsonBuilder.serializeNulls().create();
        try (Reader reader = new FileReader("data/calamocha.json")) {
            List<DatosClima> datosCalamocha = gson.fromJson(reader, new TypeToken<List<DatosClima>>() {
            }.getType());
            lstClimaData.addAll(datosCalamocha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo encargado de cargar los datos del JSON de Calanda
    private static void cargarCalandaJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().serializeNulls().registerTypeAdapter(DatosClima.class, new Deserializador());
        Gson gson = gsonBuilder.serializeNulls().create();
        try (Reader reader = new FileReader("data/calanda.json")) {
            List<DatosClima> datosCalanda = gson.fromJson(reader, new TypeToken<List<DatosClima>>() {
            }.getType());
            lstClimaData.addAll(datosCalanda);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo encargado de cargar los datos del JSON de Teruel
    private static void cargarTeruelJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().serializeNulls().registerTypeAdapter(DatosClima.class, new Deserializador());
        Gson gson = gsonBuilder.serializeNulls().create();
        try (Reader reader = new FileReader("data/Teruel.json")) {
            List<DatosClima> datosClima = gson.fromJson(reader, new TypeToken<List<DatosClima>>() {
            }.getType());
            lstClimaData.addAll(datosClima);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo encargado de almacenar los resultados de la consulta que saca los datos pedidos para IngenieriaInfoTeruel.json
    private static void construirJSONIngenieriaInfoTeruel() {
        ArrayList<DatosUnizar> consultaTeruel = (ArrayList<DatosUnizar>) lstData.stream().filter(v -> v.getLocalidad().equals("Teruel") && v.getEstudio().equals("Grado: Ingeniería Informática")).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("\nFichero creado: IngInformaticaTeruel.json\n");
        try {
            File json = new File("out/IngInformaticaTeruel.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(json, consultaTeruel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo encargado de hacer las consultas del ejercicio 1, mostrarlas en pantalla y guardarlas en el .txt
    private static void mostrarConsultasPantallaYTxt() {
        // Consulta 01 - Encuentra las asignaturas con mayor número de suspensos.
        System.out.println("Consulta 01 - Encuentra las asignaturas con mayor número de suspensos");
        ArrayList<DatosUnizar> consulta01 = (ArrayList<DatosUnizar>) lstData.stream().filter(v -> v.getTasa_exito() != null).min(Comparator.comparingDouble(v -> v.getTasa_exito())).stream().collect(Collectors.toList());
        for (DatosUnizar tmp : consulta01) {
            System.out.println(tmp.getAsignatura());
        }

        System.out.println();

        // Consulta 02 - Todas las asignaturas que se pueden estudiar en Teruel
        System.out.println("Consulta 02 - Todas las asignaturas que se pueden estudiar en Teruel");
        ArrayList<DatosUnizar> consulta02 = (ArrayList<DatosUnizar>) lstData.stream().filter(v -> v.getLocalidad().equals("Teruel")).collect(Collectors.toList());
        for (DatosUnizar tmp : consulta02) {
            System.out.println(tmp.getAsignatura());
        }

        System.out.println();

        // Consulta 03 - De todas las asignaturas que puedes estudiar en Teruel, cual es la que tiene una mayor
        //numero de suspensos: (menor tasa de éxito)
        System.out.println("Consulta 03 - De todas las asignaturas que puedes estudiar en Teruel, cual es la que tiene una mayor\n" + "numero de suspensos: (menor tasa de éxito)");
        DatosUnizar consulta03 = lstData.stream().filter(v -> v.getLocalidad().equals("Teruel")).filter(v -> v.getTasa_exito() != null).min(Comparator.comparingDouble(v -> v.getTasa_exito())).get();
        System.out.println(consulta03.getAsignatura());

        System.out.println();

        // Consulta 04 - Asignatura de Teruel con mayor número de presentados
        System.out.println("Consulta 04 - Asignatura de Teruel con mayor número de presentados");
        DatosUnizar cuonsulta04 = lstData.stream().filter(v -> v.getLocalidad().equals("Teruel")).max(Comparator.comparingInt(v -> v.getAlumnos_presentados())).get();
        System.out.println(cuonsulta04.getAsignatura());

        System.out.println();

        // Consulta 05 - Asignatura con menor tasa de éxito que se estudia en la carrera de Grado: Ingeniería Informática
        System.out.println("Consulta 05 - Asignatura con menor tasa de éxito que se estudia en la carrera de Grado: Ingeniería Informática");
        DatosUnizar consulta05 = lstData.stream().filter(v -> v.getEstudio().equals("Grado: Ingeniería Informática") && v.getTasa_exito() != null).min(Comparator.comparingDouble(v -> v.getTasa_exito())).get();
        System.out.println(consulta05.getAsignatura());

        System.out.println();

        // Consulta 06 - Encuentra la asignatura optativa más difícil, (menor tasa de éxito)
        System.out.println("Consulta 06 - Encuentra la asignatura optativa más difícil, (menor tasa de éxito)");
        DatosUnizar consulta06 = lstData.stream().filter(v -> v.getClase_asignatura().equals("Optativa") && v.getTasa_exito() != null).min(Comparator.comparingDouble(v -> v.getTasa_exito())).get();
        System.out.println(consulta06.getAsignatura());

        System.out.println();

        // Consulta 07 - Encuentra la asignatura más fácil que puedes estudiar en la Facultad de Derecho
        System.out.println("Consulta 07 - Encuentra la asignatura más fácil que puedes estudiar en la Facultad de Derecho");
        DatosUnizar consulta07 = lstData.stream().filter(v -> v.getCentro().equals("Facultad de Derecho") && v.getTasa_exito() != null).max(Comparator.comparingDouble(v -> v.getTasa_exito())).get();
        System.out.println(consulta07.getAsignatura());

        System.out.println();


        // Meter las consultas en un txt
        try {
            File consultasTxt = new File("out/UZ_dataEJ1_consultas.txt");
            FileWriter writer = new FileWriter(consultasTxt, false);
            if (consultasTxt.exists()) {
                consultasTxt.createNewFile();

                System.out.println("Fichero creado: " + consultasTxt.getName() + "\n");

                // Consulta 01 a txt
                writer.write("Consulta 01 - Encuentra las asignaturas con mayor número de suspensos \n");
                for (DatosUnizar tmp : consulta01) {
                    writer.write(tmp.getAsignatura() + "\n");
                }

                writer.write("\n");

                // Consulta 02 a txt
                writer.write("Consulta 02 - Todas las asignaturas que se pueden estudiar en Teruel \n");
                for (DatosUnizar tmp : consulta02) {
                    writer.write(tmp.getAsignatura() + "\n");
                }

                writer.write("\n");

                // Consulta 03 a txt
                writer.write("Consulta 03 - De todas las asignaturas que puedes estudiar en Teruel, cual es la que tiene una mayor numero de suspensos: (menor tasa de éxito) \n");
                writer.write(consulta03.getAsignatura() + "\n");

                writer.write("\n");

                // Consulta 04 a txt
                writer.write("Consulta 04 - Asignatura de Teruel con mayor número de presentados \n");
                writer.write(cuonsulta04.getAsignatura() + "\n");

                writer.write("\n");

                // Consulta 05 a txt
                writer.write("Consulta 05 - Asignatura con menor tasa de éxito que se estudia en la carrera de Grado: Ingeniería Informática \n");
                writer.write(consulta05.getAsignatura() + "\n");

                writer.write("\n");

                // Consulta 06 a txt
                writer.write("Consulta 06 - Encuentra la asignatura optativa más difícil, (menor tasa de éxito) \n");
                writer.write(consulta06.getAsignatura() + "\n");

                writer.write("\n");

                // Consulta 07 a txt
                writer.write("Consulta 07 - Encuentra la asignatura más fácil que puedes estudiar en la Facultad de Derecho \n");
                writer.write(consulta07.getAsignatura() + "\n");

                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    // Metodo encargado de leer los datos del CSV y de crear y escribir el XML correspondiente a dichos datos
    private static void leerCSVyEscribirXML() {
        int contador = 0;
        try {
            File csv = new File("data/UZ_data.csv");
            FileReader reader = new FileReader(csv);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            String[] tempArray;

            Controlador controlador = new Controlador("out/UZ_dataEJ1.xml");
            controlador.initData();

            while ((line = bufferedReader.readLine()) != null) {
                if (contador == 0) {
                    contador++;
                } else {
                    tempArray = line.split(DELIMITER);
                    DatosUnizar datosUnizar = new DatosUnizar();

                    datosUnizar.setTipo_estudio(tempArray[1]);
                    datosUnizar.setEstudio(tempArray[2]);
                    datosUnizar.setLocalidad(tempArray[3]);
                    datosUnizar.setCentro(tempArray[4]);
                    datosUnizar.setAsignatura(tempArray[5]);
                    datosUnizar.setTipo_asignatura(tempArray[6]);
                    datosUnizar.setClase_asignatura(tempArray[7]);
                    datosUnizar.setFecha_actualizacion(tempArray[15]);
                    if (!tempArray[0].isBlank()) {
                        datosUnizar.setCurso_academico(Integer.parseInt(tempArray[0]));
                    } else {
                        datosUnizar.setCurso_academico(null);
                    }
                    if (!tempArray[8].isBlank()) {
                        datosUnizar.setTasa_exito(Double.parseDouble(tempArray[8]));
                    } else {
                        datosUnizar.setTasa_exito(null);
                    }
                    if (!tempArray[9].isBlank()) {
                        datosUnizar.setTasa_rendimiento(Double.parseDouble(tempArray[9]));
                    } else {
                        datosUnizar.setTasa_rendimiento(null);
                    }
                    if (!tempArray[10].isBlank()) {
                        datosUnizar.setTasa_evaluacion(Double.parseDouble(tempArray[10]));
                    } else {
                        datosUnizar.setTasa_evaluacion(null);
                    }
                    if (!tempArray[11].isBlank()) {
                        datosUnizar.setAlumnos_evaluados(Integer.parseInt(tempArray[11]));
                    } else {
                        datosUnizar.setAlumnos_evaluados(null);
                    }
                    if (!tempArray[12].isBlank()) {
                        datosUnizar.setAlumnos_superados(Integer.parseInt(tempArray[12]));
                    } else {
                        datosUnizar.setAlumnos_superados(null);
                    }
                    if (!tempArray[13].isBlank()) {
                        datosUnizar.setAlumnos_presentados(Integer.parseInt(tempArray[13]));
                    } else {
                        datosUnizar.setAlumnos_presentados(null);
                    }
                    if (!tempArray[14].isBlank()) {
                        datosUnizar.setMedia_convocatorias_consumidas(Double.valueOf(tempArray[14]));
                    } else {
                        datosUnizar.setMedia_convocatorias_consumidas(null);
                    }
                    controlador.addDatosUnizar(datosUnizar);

                    lstData.add(datosUnizar);
                }
            }
            controlador.writeXMLFile("out/UZ_dataEJ1.xml");
            bufferedReader.close();
            System.out.println("Fichero creado: UZ_dataEJ1.xml\n");
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}