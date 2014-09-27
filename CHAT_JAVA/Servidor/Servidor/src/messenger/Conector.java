/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messenger;

import java.net.*;
import java.io.*;
import messenger.VentanaServidor;

/**
 *
 * @author mint
 */
public class Conector extends Thread{
    
    private Socket s;
    private ServerSocket ss;                    //Variable Servidor
    private InputStreamReader entradaSocket;    //Flujo de entrada (vector)
    private DataOutputStream salida;            //Flujo de salida de datos
    private BufferedReader entrada;             //Buffer para leer los mensajes
    final int puerto = 8000;
    
    public Conector(String nombre){
        super(nombre);
    }
                
    public void enviarMSG(String msg){
        
        try{
            this.salida.writeUTF(msg+"\n");
            
        }catch(IOException e){}; 
    }
    
    public void run(){
        String text = "test";
        try{
            this.ss = new ServerSocket(puerto);
            this.s = ss.accept();
            
            //Creacion de entrada de datos para lectura de mensajes
            this.entradaSocket = new InputStreamReader(s.getInputStream());
            this.entrada = new BufferedReader(entradaSocket);        //entrada se utiliza para leer datos
            
            //Creacion de salida de datos para envio de mensajes
            this.salida = new DataOutputStream(s.getOutputStream());
            
            while(true){
            
                text = this.entrada.readLine();
                System.out.println(text);
                VentanaServidor.jTextArea1.setText(VentanaServidor.jTextArea1.getText()+"\n"+text);
            }
            
        }catch(IOException e){
            System.out.println("Algun tipo de error ha ocurrido");
        };
    
    
    }
    
    public String leerMSG(){
        
        try{
            return this.entrada.readLine();
            
        }catch(IOException e){};
        return null;
    }
    
    public void desconectar(){
        
        try{
            s.close();
            
        }catch(Exception e){};
        try{
            ss.close();
            
        }catch(Exception e){};
        
    }
}
