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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jost-ale
 */
public class Servidor {

    public static void main(String[] args) {
        final int PUERTO = 5001;
        byte[] buffer = {};

        try {
            System.out.println("Iniciando el servidor...");
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);

            while (true) {
                buffer = new byte[1024];
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);
                String mensaje = new String(peticion.getData(), 0, peticion.getLength());

                if (mensaje.isEmpty()) {
                    socketUDP.close();
                    break;
                } else {
                    System.out.println("soy el Server y recibo la cadena: " + mensaje);

                    /*lo que el servidor le enviara al cliente*/
                    int puertoCliente = peticion.getPort();
                    InetAddress direccion = peticion.getAddress();

                    buffer = mensaje.getBytes();

                    DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);

                    socketUDP.send(respuesta);
                }

            }

        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
