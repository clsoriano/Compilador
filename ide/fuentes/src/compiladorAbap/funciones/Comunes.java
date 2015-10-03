/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorAbap.funciones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import runProgramC.RunExternProgram;

/**
 *
 * @author jcsoriano
 */
public class Comunes {

    private RunExternProgram runExt = new RunExternProgram();

    public void ventAviso(String titulo, String mensaje) {
        BorderPane root = new BorderPane();
        Stage ventAviso = new Stage();
        VBox boxLabel = new VBox();
        VBox boxBtn = new VBox();
        Label msj = new Label(mensaje);
        int tam = mensaje.length();
        Button btn = new Button("Aceptar");
        salirBtn(btn, ventAviso);
        boxLabel.getChildren().add(msj);
        boxLabel.setTranslateX((tam * 3) / 2);
        boxBtn.setTranslateX(tam * 4);
        boxBtn.setTranslateY(-10);
        boxBtn.getChildren().add(btn);
        Scene scene = new Scene(root, (tam * 10), 80);
        root.setCenter(boxLabel);
        root.setBottom(boxBtn);
        ventAviso.setTitle(titulo);
        ventAviso.setScene(scene);
        ventAviso.show();
    }
    
    public void ventAviso2(String titulo, String mensaje) {
        BorderPane root = new BorderPane();
        Stage ventAviso = new Stage();
        VBox boxText = new VBox();
        VBox boxBtn = new VBox();
        TextArea msj = new TextArea(mensaje);
        int tam = mensaje.length();
        Button btn = new Button("Aceptar");
        salirBtn(btn, ventAviso);
        boxText.getChildren().add(msj);
        //boxText.setTranslateX((tam * 3) / 2);
        //boxBtn.setTranslateX(tam * 4);
        //boxBtn.setTranslateY(-10);
        boxBtn.getChildren().add(btn);
        Scene scene = new Scene(root, 600, 300);
        root.setCenter(boxText);
        root.setBottom(boxBtn);
        ventAviso.setTitle(titulo);
        ventAviso.setScene(scene);
        ventAviso.show();
    }
    

    public void salirBtn(Button btn, Stage stage) {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
    }

    public void salir(MenuItem mItem, Stage stage) {
        mItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage.close();
            }
        });
    }

    public void nuevo(MenuItem mItem, TextField dirArchivo, TextArea txtA) {
        mItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dirArchivo.setText("");
                txtA.setText("");
            }
        });
    }

    public void habilitarMenGuardar(TextArea txtA, MenuItem mItem) {
        txtA.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                mItem.setDisable(false);
            }
        });
    }

    public void abrir(MenuItem mItem, Stage stage, TextArea txtArea, TextField dirArchivo) {
        mItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String userDirectoryString = System.getProperty("user.dir");
                File userDirectory = new File(userDirectoryString);
                if (!userDirectory.canRead()) {
                    userDirectory = new File("../");
                }
                FileChooser archivoSelect = new FileChooser();
                archivoSelect.setInitialDirectory(userDirectory);
                File archivo;
                archivoSelect.setTitle("Código Fuente");
                archivo = archivoSelect.showOpenDialog(stage);
                txtArea.setText(leerArchivo(archivo.getPath()));
                dirArchivo.setText(archivo.getPath());
            }
        });
    }

    public void resaltaPal(TextArea txtA) {

        //implementar resaltar palabras reservadas o texto
    }

    public void guardarCode(Stage stage, MenuItem mItem, TextField dirArchivo, TextArea txt) {
        mItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                guardarNewSaveArch(dirArchivo, txt, stage);
                mItem.setDisable(true);
            }
        });
    }

    public void guardar(Button btn, String dirArchivo, HashMap<String, Object> paramG) {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                escribirEnArchivo(dirArchivo, paramG);
                ventAviso("Almacenado", "Se almaceno la configuración.");
            }
        });
    }

    /**
     * ********************************LECTURA DE
     * ARCHIVOS******************************
     */
    public String leerArchivo(String dirArchivo) {
        FileReader leerArch;
        BufferedReader buff = null;
        String contenido = "";
        try {
            leerArch = new FileReader(dirArchivo);
            buff = new BufferedReader(leerArch);
            String linea;
            while ((linea = buff.readLine()) != null) {
                contenido += linea + "\n";
            }
        } catch (Exception ex) {
            System.err.format("Error leyendo archivo", ex);
        } finally {
            try {
                buff.close();
            } catch (Exception ex) {
                System.err.format("Error cerrando archivo", ex);
            }
        }

        return contenido;
    }

    public String leerArchivoParam(String dirArchivo, String valor) {
        FileReader leerArch = null;
        BufferedReader buff = null;
        String contenido = "";
        String val = "";
        try {
            leerArch = new FileReader(dirArchivo);
            buff = new BufferedReader(leerArch);
            String linea;
            String[] archDat;
            while ((linea = buff.readLine()) != null) {
                contenido = linea + "\n";
                archDat = contenido.split(";");
                if (archDat[0].equals(valor)) {
                    val = archDat[1];
                    break;
                }

            }
        } catch (Exception ex) {
            System.err.format("Error leyendo archivo", ex);
        } finally {
            try {
                leerArch.close();
                buff.close();
            } catch (Exception ex) {
                System.err.format("Error cerrando archivo", ex);
            }
        }

        return val;
    }

    /**
     * **************************ESCRITURA-ARCHIVO
     *
     **************************
     * @param dirArchivo direccion del archivo
     * @param paramG parametros que poseen los valores que se almaceran en el
     * archivo
     */
    public void escribirEnArchivo(String dirArchivo, HashMap<String, Object> paramG) {
        FileWriter fw = null;
        FileReader fr = null;
        BufferedWriter buffW = null;
        BufferedReader buffR = null;
        String linea;
        String[] archDat;
        String cad = "";
        try {
            fr = new FileReader(dirArchivo);
            buffR = new BufferedReader(fr);
            while ((linea = buffR.readLine()) != null) {
                archDat = linea.split(";");
                if (paramG.get(archDat[0]) != null) {
                    TextField txt = (TextField) paramG.get(archDat[0]);
                    cad += archDat[0] + ";" + txt.getText() + "\n";
                } else {
                    linea += "\n";
                    cad += linea;
                }
            }
            fr.close();
            buffR.close();
            fw = new FileWriter(dirArchivo);
            buffW = new BufferedWriter(fw);
            buffW.write(cad);
            buffW.close();
            fw.close();
        } catch (Exception ex) {
            System.err.format("Error en guardado de archivo", ex);
        }

    }

    /**
     * *****GUARDAR O CREAR NUEVO ARCHIV
     *
     * @param dirArchivo ***** @param cad
     * @param cad
     * @param stage
     */
    public void guardarNewSaveArch(TextField dirArchivo, TextArea txt, Stage stage) {
        try {
            FileWriter fw;
            BufferedWriter buffW;
            if (dirArchivo.getText() != null && !dirArchivo.getText().equals("")) {
                fw = new FileWriter(dirArchivo.getText());
                buffW = new BufferedWriter(fw);
                buffW.write(txt.getText());
                buffW.close();
                fw.close();
            } else {
                FileChooser fc = new FileChooser();
                File archivo;
                fc.setTitle("Almacenar en");
                archivo = fc.showSaveDialog(stage);
                dirArchivo.setText(archivo.getAbsolutePath());
                fw = new FileWriter(archivo.getAbsolutePath());
                buffW = new BufferedWriter(fw);
                buffW.write(txt.getText());
                buffW.close();
                fw.close();
            }
        } catch (Exception ex) {
            System.err.format("Error en almacenar archivo", ex);
        }

    }

    public void ubicacionScanner(Button btn, Stage stage, TextField txt, HashMap<String, Object> paramG) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String userDirectoryString = System.getProperty("user.dir");
                File userDirectory = new File(userDirectoryString);
                if (!userDirectory.canRead()) {
                    userDirectory = new File("../");
                }
                FileChooser archivoSelect = new FileChooser();
                archivoSelect.setInitialDirectory(userDirectory);
                File archivo;
                archivoSelect.setTitle("Código Scanner");
                archivo = archivoSelect.showOpenDialog(stage);
                if (archivo != null) {
                    txt.setText(archivo.getPath());
                    escribirEnArchivo("param.txt", paramG);
                    ventAviso("Almacenado", "Se almaceno direccion de scanner.");
                }
            }
        });
    }

    public void ventSecundaria(MenuItem mItem, int func, int tamH, int tamW, TextField txt) {
        mItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (func == 1) {
                    ventScanner("Ubicacion Scanner", tamH, tamW, txt);
                } else if (func == 2) {
                    ventParam("Configuración de parámetros", tamH, tamW);
                }
            }
        });
    }

    /**
     * *********************SUB-VENTANAS*********************
     */
    public void ventScanner(String title, int tamH, int tamW, TextField txtDirScanner) {
        BorderPane root = new BorderPane();
        HashMap<String, Object> paramG = new HashMap<>();
        Stage ventScanner = new Stage();
        VBox box = new VBox();
        Scene scene = new Scene(root, tamH, tamW);
        Label label;
        Button btn;
        TextField txt;
        label = new Label("Ubicacion de Scanner:");
        box.getChildren().add(label);
        txt = new TextField();
        txt.setMaxWidth(250);
        box.getChildren().add(txt);

        btn = new Button("Agregar");
        box.getChildren().add(btn);
        txt.setText(leerArchivoParam("param.txt", "UBSCAN"));
        paramG.put("UBSCAN", txt);
        ubicacionScanner(btn, ventScanner, txt, paramG);
        root.setCenter(box);
        txtDirScanner.setText(txt.getText());
        ventScanner.setTitle(title);
        ventScanner.setScene(scene);
        ventScanner.show();
    }

    public void ventParam(String title, int tamH, int tamW) {
        BorderPane root = new BorderPane();
        Stage ventParam = new Stage();
        VBox boxTxt = new VBox();
        VBox boxLabel = new VBox();
        VBox boxBtn = new VBox();
        HashMap<String, Object> paramG = new HashMap<>();
        Scene scene = new Scene(root, tamH, tamW);
        Label label1, label2, label3;
        Button btn;
        TextField txt1, txt2, txt3;
        label1 = new Label("MAXLINEA:");
        label1.setTranslateY(10);
        label2 = new Label("MAXDIGIT:");
        label2.setTranslateY(30);
        label3 = new Label("MAXID:");
        label3.setTranslateY(50);
        boxLabel.getChildren().addAll(label1, label2, label3);
        boxLabel.setTranslateX(40);

        txt1 = new TextField();
        txt1.setMaxWidth(100);
        txt1.setTranslateY(5);
        txt2 = new TextField();
        txt2.setMaxWidth(100);
        txt2.setTranslateY(18);
        txt3 = new TextField();
        txt3.setMaxWidth(100);
        txt3.setTranslateY(28);
        boxTxt.getChildren().addAll(txt1, txt2, txt3);
        boxTxt.setTranslateX(40);

        btn = new Button("Guardar");
        btn.setMaxWidth(80);
        paramG.put("MAXLINEA", txt1);
        paramG.put("MAXDIGIT", txt2);
        paramG.put("MAXID", txt3);
        guardar(btn, "param.txt", paramG);
        boxBtn.getChildren().add(btn);
        boxBtn.setTranslateX(40 + (tamW / 2));
        boxBtn.setTranslateY(-5);
        txt1.setText(leerArchivoParam("param.txt", "MAXLINEA"));
        txt2.setText(leerArchivoParam("param.txt", "MAXDIGIT"));
        txt3.setText(leerArchivoParam("param.txt", "MAXID"));
        root.setCenter(boxTxt);
        root.setLeft(boxLabel);
        root.setBottom(boxBtn);
        ventParam.setTitle(title);
        ventParam.setScene(scene);
        ventParam.show();
    }

    public void ejecutarScanner(MenuItem mItem, TextField dirArchivo) {
        mItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String dirScanner = leerArchivoParam("param.txt", "UBSCAN");
                if (dirScanner != null && !dirScanner.equals("")) {
                    if (dirArchivo != null && !dirArchivo.getText().equals("")) {
                        System.out.println("Archivo scanner:" + dirScanner);
                        System.out.println("Archivo scanner:" + dirArchivo);
                        String salida = runExt.ejecutarCodExtern(dirArchivo.getText(), dirScanner);
                        ventAviso2("Compilador", salida);
                    } else {
                        ventAviso("Aviso", "Error: No se encontro el archivo fuente");
                    }
                } else {
                    ventAviso("Aviso", "Error: No se encontro el archivo scanner");
                }
            }
        });
    }
}
