/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runProgramC;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author jcsoriano
 */
public class RunExternProgram {

    public String ejecutarCodExtern(String dirArchivo, String dirScanner) {
        String salida = "";
        try {
            String execute = "cmd /c " + dirScanner + " " + dirArchivo;
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(execute);

            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String line = null;

            while ((line = input.readLine()) != null) {
                salida += line + "\n";
            }
            int exitVal = pr.waitFor();
            System.out.println("Exited with error code " + exitVal);

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return salida;
    }

}
