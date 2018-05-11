
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.net.*;

public class client {
    static double endDate;
    static double startDate;

    public static void islem(int port) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String deger, kontrol = "as", gelenDeger;
        int secim = 0;
        try {
            socket = new Socket("localhost", port);
        } catch (Exception e) {
            System.out.println("Port hatasi");
        }
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            System.out.println("********************");
            System.out.println("1-Toplama");
            System.out.println("2-Faktoriyel");
            System.out.println("********************");
            System.out.println("Seciminizi yapiniz");
            Scanner input = new Scanner(System.in);
            try {
                secim = input.nextInt();
            } catch (Exception e) {
                System.out.println("Lutfen gecerli bir tercih giriniz");


            }
            if (secim == 1 || secim == 2)
                break;
        }

        System.out.println("Server'a gönderilecek sayi( e'ye basarsaniz program sonlandirilir) :");
        BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
        while ((deger = data.readLine()) != null) {
            if (deger.trim().equalsIgnoreCase("e")) {
                System.exit(0);                //programı tamamlamak için e harfine basılabilir.
            }
            //Aşşağıda da görüldüğü gibi tüm bilgiyi @ işareti ile ayırarak sunucu ya gönderiyorum
            if (secim == 1) {
                kontrol = "@tp@" + InetAddress.getLocalHost() + "@" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "@" + "kullanici bilgisi";
            } else if (secim == 2) {   //Bu if else blookları ile kullanıcının seçtiği işleme göre kullanıcının girsiği sayının sonuna bir ifade ekleniyor
                kontrol = "@fk@" + InetAddress.getLocalHost() + "@" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "@" + "kullanici bilgisi";
            }
            startDate = System.nanoTime();
            out.println(deger + kontrol);
            if (secim == 1) {
                while ((gelenDeger = in.readLine()) != null) {
                    System.out.println("Serverden gelen sonuc :" + gelenDeger);
                    endDate = System.nanoTime();
                    if (gelenDeger.length() == 1) {
                        System.out.println("ok");   //Toplama işleminde rakam tek ise ok yazıp yeniden rakam istiyor.
                        break;
                    }
                }
                System.out.println("Server'a gönderilecek sayi( e'ye basarsaniz program sonlandirilir) :");
            } else if (secim == 2) {
                System.out.println("Serverden gelen sonuc :" + in.readLine()); // Burası ise kullanıcının faktöryel işlemi seçtiği zaman sunucudan gelen sonucun gösterildiği yer
                endDate = System.nanoTime(); //burası sunucudan sonucun döndüğü yer
                System.out.println("ok");
                System.out.println("Server'a gönderilecek sayi( e'ye basarsaniz program sonlandirilir) :");
            }
            dosyayaYaz();
        }
        out.close();
        in.close();
        data.close();
        socket.close();

    }

    public static void main(String arg[]) throws IOException {
        try {
            islem(7888);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dosyayaYaz();
    }

    public static void dosyayaYaz() throws IOException {
        File dosya = new File("C:\\Users\\haliltprkk\\IdeaProjects\\SocketProgramming\\src\\clientLogs.txt");
        if (!dosya.exists()) {
            try {
                dosya.createNewFile();
            } catch (IOException e) {
                System.out.println("Hata olustu");
            }
        }
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()); //burda sistem saatini alıyorum
        double delay = (endDate - startDate) / 1000000000; //burda gecikmeyi 1 000 000 000 bölerek nanosaniye cinsinden gecikmeyi hesaplıyorum
        currentTime += "  " + InetAddress.getLocalHost() + " " + String.valueOf(delay);
        ;
        FileWriter fileWriter = new FileWriter(dosya, true);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write(System.lineSeparator() + currentTime); //burda bir satır boşluk bırakarak dosyaya yazıyorum
        bWriter.close();

    }

}
