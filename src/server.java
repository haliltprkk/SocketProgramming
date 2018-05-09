
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author haliltprkk
 */
public class server {


    public static void islem(int random) throws IOException {
        String clientGelen;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(random);

        } catch (Exception e) {
            System.out.println("Port hatasi!");
        }
        System.out.println("Sunucu hazir");
        clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while ((clientGelen = in.readLine()) != null) {
            clientGelen = clientGelen.trim();
            String parts[] = clientGelen.split("-");
            if (parts[1].equals("tp")) {
                //System.out.println("Client dan gelen veri :" + clientGelen);
                int i = 0, sum = 0;
                while (i < clientGelen.length() - 3) {
                    sum = sum + Integer.parseInt(String.valueOf(clientGelen.charAt(i)));
                    i++;
                }
                out.println(sum);
                while (sum >= 10) {

                    String sayiString = String.valueOf(sum);
                    sum = 0;
                    for (int j = 0; j < sayiString.length(); j++) {
                        sum += Integer.parseInt(String.valueOf(sayiString.charAt(j)));

                    }
                    out.println(sum);
                }
            }
            if (parts[1].equals("fk")) {
                int i, fact = 1;
                for (i = 1; i <= Integer.parseInt(parts[0]); i++) {
                    fact = fact * i;
                }
                System.out.println("client dan gelen veri :" + clientGelen);
                out.println(fact);
            }
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String arg[]) {
        try {
            islem(7006);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

