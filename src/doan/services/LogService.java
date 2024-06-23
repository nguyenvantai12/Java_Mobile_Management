package doan.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import doan.models.Customer;

public class LogService
{
    private BufferedWriter bufferedWriter;
    private String ipAddressAndPort;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm:ss");


    public LogService(Customer customer, Socket client)
    {
        ipAddressAndPort = client.getInetAddress().toString() + ":" + client.getPort();

        try
        {
            File file = new File(customer.getUser().getLogin() + "Logs.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(customer.getUser().getLogin() + "'s logs.\n" +
                    customer.getName() + "   " + customer.getEmail() + "\n" +
                    df.format(LocalDateTime.now()) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddLog(String action)
    {
        String log = "[" + df.format(LocalDateTime.now()) + "] (" + ipAddressAndPort + ") " + action + "\n";
        WriteToFile(log);
        System.out.print(log);
    }

    private void WriteToFile(String data)
    {
        try
        {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Save()
    {
        try
        {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
