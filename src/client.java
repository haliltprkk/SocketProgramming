
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {

    public static void islem(int random) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String deger, kontrol = "as", gelenDeger;
        int secim = 0;
        try {
            socket = new Socket("localhost", random);
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
            secim = input.nextInt();
            if (secim == 1 || secim == 2)
                break;
        }

        System.out.println("Server'a gönderlecek sayi :");
        BufferedReader data = new BufferedReader(new InputStreamReader(System.in));
        while ((deger = data.readLine()) != null) {
            if (secim == 1) {
                kontrol = "-tp";
            } else if (secim == 2) {
                kontrol = "-fk";
            }
            out.println(deger + kontrol);
            if (secim == 1) {
                while ((gelenDeger = in.readLine()) != null) {
                    System.out.println("Serverden gelen sonuc :" + gelenDeger);
                    if (gelenDeger.length() == 1) {
                        System.out.println("ok");
                        break;
                    }
                }
                System.out.println("Serverden gelen sonuc :" + gelenDeger);
                System.out.println("Server a gonderilecek sayiyi giriniz : ");
            } else if (secim == 2) {
                System.out.println("Serverden gelen sonuc :" + in.readLine());
                System.out.println("Server a gonderilecek sayiyi giriniz : ");

            }

        }
        out.close();
        in.close();
        data.close();
        socket.close();

    }

    public static void main(String arg[]) {
        try {
            islem(7006);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
