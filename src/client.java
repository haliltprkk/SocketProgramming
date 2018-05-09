
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        islem();
    }

    public static void islem() throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String deger, kontrol = "as";
        int secim = 0;
        try {
            socket = new Socket("localhost", 7810);
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
            System.out.println("Serverden gelen sonuc :" + in.readLine());
            System.out.println("Server a gonderilecek sayiyi giriniz : ");
        }
        out.close();
        in.close();
        data.close();
        socket.close();

    }

}
