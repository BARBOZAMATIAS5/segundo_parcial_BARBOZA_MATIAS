/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manejador_archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author gabri
 */
public class ArchivoSerializable <T extends Serializable> implements ArchivoManager<T>{
    
    private final File archivo;
    
    public ArchivoSerializable(String path){
        this.archivo = new File(path);
    }
    
    /// guardar una lista de T
    @Override
    public void guardarLista(List<T> lista, String s) throws IOException{
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream(archivo));
            out.writeObject(lista);
        } finally{
            if(out != null){
                out.close();
            }
        }
    }
    
    /// cargar lista de t
    public List<T> cargarLista() throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        
        if (!archivo.exists()) return null;
        
        try{
            in = new ObjectInputStream(new FileInputStream(archivo));
           
            return (List<T>)in.readObject();
        } finally{
            if (in != null){
                in.close();
            }
        }
    }
}
