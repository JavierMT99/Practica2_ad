CREATE DATABASE IF NOT EXISTS Clima;
USE Clima;
CREATE TABLE IF NOT EXISTS DatosClima (
                                        fecha VARCHAR(255),
                                        indicativo VARCHAR(255),
                                        nombre VARCHAR(255),
                                        provincia VARCHAR(255),
                                        altitud INT,
                                        tmed DOUBLE,
                                        prec DOUBLE,
                                        tmin DOUBLE,
                                        horatmin VARCHAR(255),
                                        tmax DOUBLE,
                                        horatmax VARCHAR(255),
                                        dir INT,
                                        velmedia DOUBLE,
                                        racha DOUBLE,
                                        horaracha VARCHAR(255),
                                        sol DOUBLE,
                                        presMax DOUBLE,
                                        horaPresMax VARCHAR(255),
                                        presMin DOUBLE,
                                        horaPresMin VARCHAR(255)
                                       );