/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_unix.practica03beta5unix;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
/**
 *
 * @author jost-ale
 */
public class Cliente {
    public static void main(String[] args){
        
        final int PUERTO_SERVIDOR = 5001;
        byte[] buffer = {};
        DatagramPacket pregunta = null;
        
        try {
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            
            try (DatagramSocket socketUDP = new DatagramSocket()) {
                Scanner mensaje = new Scanner(System.in);
                System.out.println("Inserte una cadena de texto: ");
                String cadena = mensaje.nextLine();
                String cad = null;
                
                for(; /*!"".equals(cadena)*/ true ;){
                    buffer = new byte[1024];
                    buffer = cadena.getBytes();
                    
                    pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
                    
                    if(!"".equals(cadena)){
                        socketUDP.send(pregunta);
                    
                        DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                        socketUDP.receive(peticion);
                        //System.out.println();
                        cad = new String(peticion.getData(), 0, peticion.getLength());
                        System.out.println("Recibo la cadena: " + cad);
                        System.out.println();

                        System.out.println("Inserte una cadena de texto: ");
                        cadena = mensaje.nextLine();
                    }else{
                        pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
                        socketUDP.send(pregunta);
                        socketUDP.close();
                        break;
                    }
                    
                }
            }
            
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}