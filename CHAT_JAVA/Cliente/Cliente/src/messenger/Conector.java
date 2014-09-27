/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messenger;

import java.net.*;
import java.io.*;

/**
 *
 * @author mint
 */
public class Conector extends Thread {
    
    private Socket s;
    private ServerSocket ss;                    //Variable Servidor
    private InputStreamReader entradaSocket;    //Flujo de entrada (vector)
    private DataOutputStream salida;            //Flujo de salida de datos
    private BufferedReader entrada;             //Buffer para leer los mensajes
    final int puerto = 8000;
            
      
    public Conector(String ip){
        try{
            this.s = new Socket(ip,this.puerto);                    
            
            //Creacion de entrada de datos para lectura de mensajes
            this.entradaSocket = new InputStreamReader(s.getInputStream());
            this.entrada = new BufferedReader(entradaSocket);        //entrada se utiliza para leer datos
            
            //Creacion de salida de datos para envio de mensajes
            this.salida = new DataOutputStream(s.getOutputStream());
            this.salida.writeUTF("Conectado-n \n");
            
        }catch(Exception e){};
        
    }
    
     public void run(){
        String texto;
        while(true){
            try{
            texto = entrada.readLine();
            VentanaCliente.jTextArea1.setText(VentanaCliente.jTextArea1.getText()+"\n"+texto);
            }catch(IOException e){}; 
        
        }
    }
     
    public void enviarMSG(String msg){
        
        System.out.println("enviando");
        try{
            this.salida = new DataOutputStream(s.getOutputStream());
            this.salida.writeUTF(msg+"\n");            
        }catch(IOException e){
        System.out.println("Problema al enviar");
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
