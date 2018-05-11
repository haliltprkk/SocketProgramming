
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author haliltprkk
 */
public class server {


    static String gelenTumBilgi;

    public static void islem(int random) throws IOException {
        String clientGelen;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(random);   //Bu try catch bloğu ile portun açılıp açılmadığını kontrol ediyorum
        } catch (Exception e) {
            System.out.println("Port hatasi!");
        }
        System.out.println("Sunucu hazir");
        clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);            //Burda serverdan clientt'a doğru bir akış oluşturuyorum
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Burda ise Clientdan sunucu bir stream oluşturuyorum.
        while ((clientGelen = in.readLine()) != null) {
            clientGelen = clientGelen.trim();
            gelenTumBilgi = clientGelen.trim();
            dosyayaYaz();
            String parts[] = clientGelen.split("@");   //Burda kullanıcıdan gelen sayıyı ile kullanıcının istediği işlemi anlamak için split ediyıtum
            if (parts[1].equals("tp")) {
                //System.out.println("Client dan gelen veri :" + clientGelen);
                int i = 0, sum = 0;
                while (i < parts[0].length()) {
                    sum = sum + Integer.parseInt(String.valueOf(clientGelen.charAt(i)));  //Eğer işlem toplama ise ifadeyi boş olana kadar ayırıp integer' a çevirip sum değişkeni yardımı ile topluyorum
                    i++;
                }
                out.println(sum);
                while (sum >= 10) {

                    String sayiString = String.valueOf(sum);
                    sum = 0;
                    for (int j = 0; j < sayiString.length(); j++) {
                        sum += Integer.parseInt(String.valueOf(sayiString.charAt(j))); //Burda ise bulunan toplam iki basamaklı ise işlemi tekrar ediorum

                    }
                    out.println(sum);
                }
            }
            if (parts[1].equals("fk")) {
                int i;
                double fact = 1;
                for (i = 1; i <= Integer.parseInt(parts[0]); i++) {
                    fact = fact * i;
                }
                System.out.println("client dan gelen veri :" + clientGelen);   //Bu blok ise kullanıcı fatöriyel işlemi seçtiği zaman devreye giriyor.
                out.println(fact);
            }
        }
        out.close();
        in.close();
        clientSocket.close();       //Son olarak soketler ve in,out okuyucu ve yazıcıları kapatılıyor.
        serverSocket.close();
    }

    public static void main(String arg[]) throws IOException {
        try {
            islem(7842);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dosyayaYaz() throws IOException {
        File dosya = new File("C:\\Users\\haliltprkk\\IdeaProjects\\SocketProgramming\\src\\serverLogs.txt");
        if (!dosya.exists()) {
            try {
                dosya.createNewFile();
            } catch (IOException e) {
                System.out.println("Hata olustu");
            }
        }

        String logs[] = gelenTumBilgi.split("@"); //burda @ işareti ile ayrıştırma yapıp bilgiyi dizinin indislerinde tutuyorum
        FileWriter fileWriter = new FileWriter(dosya, true);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write(System.lineSeparator() + logs[2] + "  " + logs[3] + "  " + logs[4]); //burda bir satır boşluk bırakarak dosyaya yazıyorum
        bWriter.close();


    }

}

