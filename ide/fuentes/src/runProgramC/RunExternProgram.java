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
        String os = System.getProperty("os.name").toLowerCase();
        String osVersion = System.getProperty("os.arch").toLowerCase();
        String osArch = System.getProperty("os.version").toLowerCase();
        String execute = "";
        try {
            if (isWindows(os)) {
                execute = "cmd /c " + dirScanner + " " + dirArchivo + " param.txt";
            } else if (isUnix(os)) {
                execute = "./scanner/objeto/scanner ejemplo1.txt param.txt";
            }
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

    public boolean isWindows(String os) {
        return (os.indexOf("win") >= 0);
    }

    public boolean isUnix(String os) {
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0);

    }
}
