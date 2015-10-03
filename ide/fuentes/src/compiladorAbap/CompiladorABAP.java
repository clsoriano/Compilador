package compiladorAbap;

import compiladorAbap.funciones.Comunes;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CompiladorABAP extends Application
{
  Comunes cm = new Comunes();

  public void start(Stage ventPpl)
  {
    HashMap txtTitle = new HashMap();
    BorderPane root = new BorderPane();
    TextArea codFuente = new TextArea();
    codFuente.setLayoutY(400.0D);
    VBox topContainer = new VBox();
    VBox bottomContainer = new VBox();
    MenuBar menuPpl = new MenuBar();
    ToolBar toolBar = new ToolBar();
    TextField dirArchivo = new TextField();
    TextField dirScanner = new TextField();

    Menu archivo = new Menu("Archivo");
    MenuItem nuevo = new MenuItem("Nuevo");
    MenuItem abrir = new MenuItem("Abrir");
    MenuItem guardar = new MenuItem("Guardar");
    MenuItem salir = new MenuItem("Salir");
    guardar.setDisable(true);

    this.cm.salir(salir, ventPpl);
    this.cm.abrir(abrir, ventPpl, codFuente, dirArchivo);
    this.cm.guardarCode(ventPpl, guardar, dirArchivo, codFuente);
    this.cm.nuevo(nuevo, dirArchivo, codFuente);
    this.cm.habilitarMenGuardar(codFuente, guardar);
    archivo.getItems().addAll(new MenuItem[] { nuevo, abrir, guardar, salir });

    Menu herra = new Menu("Herramientas");
    MenuItem ejecutar = new MenuItem("Ejecutar Scanner");
    //MenuItem frmtSalida = new MenuItem("Formato de Salida");
    MenuItem mnjParam = new MenuItem("Manejo de parámetros/valores por defecto");

    MenuItem ubScanner = new MenuItem("Ubicar Scanner");

    txtTitle.put("label1", "Ubicacion de Scanner:");
    txtTitle.put("btn1", "Agregar");
    this.cm.ventSecundaria(ubScanner, 1, 250, 80, dirScanner);

    this.cm.ventSecundaria(mnjParam, 2, 300, 150, null);

    herra.getItems().addAll(new MenuItem[] { ubScanner, ejecutar, mnjParam });

    this.cm.ejecutarScanner(ejecutar, dirArchivo);

    Menu ayuda = new Menu("Ayuda");
    MenuItem acercaD = new MenuItem("Acerca de");
    MenuItem documen = new MenuItem("Documentación");
    ayuda.getItems().addAll(new MenuItem[] { acercaD, documen });

    Label msj = new Label("Listo");
    bottomContainer.getChildren().addAll(new Node[] { msj });
    menuPpl.getMenus().addAll(new Menu[] { archivo, herra, ayuda });
    topContainer.getChildren().add(menuPpl);
    topContainer.getChildren().add(toolBar);

    root.setTop(topContainer);
    root.setCenter(codFuente);
    root.setBottom(bottomContainer);

    Scene scene = new Scene(root, 500.0D, 450.0D);

    ventPpl.setTitle("Compilador ABAP");
    ventPpl.setScene(scene);
    ventPpl.show();
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}