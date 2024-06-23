package doan.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;

import doan.db.DbContext;

 
public class Server {
    public static void main(String[] args) {
        try {
            try (ServerSocket ss = new ServerSocket(2222)) {
                System.out.println("Máy chủ đang chạy");
                String connectionString = "jdbc:mysql://localhost:3306/Shop";
                DbContext db = new DbContext(connectionString, "root", "123456789");
                if (args.length > 0) {
                    LinkedHashSet<String> argsSet = new LinkedHashSet<>(Arrays.asList(args));
                    for (String arg : argsSet) {
                        if (arg.equals("-init"))
                            db.Initialize();
                    }
                }
                while (true) {
                    Socket client = ss.accept();
                    System.out.println("Người dùng tham gia mới");

                    Thread t = new ClientHandler(client, db.getConnection());
                    t.start();
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
