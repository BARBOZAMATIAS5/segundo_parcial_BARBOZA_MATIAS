/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejador_archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author gabri
 */
public class ArchivoTexto<T> implements ArchivoManager<T>{

    @Override
    public void guardarLista(List<T> lista, String s) throws IOException {
        
        File archivo = new File("src/main/ticket.txt");
        
        BufferedWriter bw = null;
        try{
            FileWriter fw = new FileWriter(archivo);
            bw = new BufferedWriter(fw);
            
            bw.write("-----------------------");
            for (T items : lista){
                bw.write(items.toString());
                bw.newLine();
            }
            bw.write("-----------------------");
            bw.newLine();
            if (s != null && !s.isBlank()){
                bw.write(s);
                bw.newLine();
            }
            
        } catch (IOException e){
            System.out.println(e);
        } finally{
            try{
                if(bw != null) bw.close();
            } catch(IOException e){
                System.out.println(e);
            }
        }
    }
    
}
