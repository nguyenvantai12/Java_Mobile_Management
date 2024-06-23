package doan.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.db.Handler;
import doan.models.Customer;
import doan.models.Message;
import doan.models.Product;
import doan.services.LogService;

public class ClientHandler extends Thread {
	private final DataInputStream input;
	private final DataOutputStream output;
	private final Socket client;
	private final Handler requestHandler;
	private LogService logService;
	private Customer current;

	ClientHandler(Socket s, Connection db) throws IOException {
		client = s;
		input = new DataInputStream(s.getInputStream());
		output = new DataOutputStream(s.getOutputStream());
		requestHandler = new Handler(db);
	}

	@Override
	public void run() {
		String request;
		String response = "";
		String isErr = "";
		ObjectMapper mapper = new ObjectMapper();
		while (true) {
			try {
				request = input.readUTF();
				Message<?> message = mapper.readValue(request, Message.class);
				if (message.getHead().equals("EXIT")) {
					if (logService != null) {
						logService.AddLog("Đã đăng xuất");
						logService.Save();
					} else
						System.out.println("");
					client.close();
					return;
				}
				LogHandler(message);
				Message<?> handledMessage = requestHandler.Handle(message);
				response = mapper.writeValueAsString(handledMessage);
				isErr = handledMessage.getObject();
 				if (message.getHead().equals("LOGIN")) {
					current = mapper.readValue(handledMessage.getObject(), Customer.class);
					logService = new LogService(current, client);
					logService.AddLog("Đã đăng nhập");
				}
			} catch (IOException | SQLException | ClassCastException e) {
				e.printStackTrace();
				try {
					response = mapper.writeValueAsString(new Message<>("ERROR", String.class, isErr));
				} catch (JsonProcessingException ex) {
					ex.printStackTrace();
				}
			} finally {
				try {
					if (!client.isClosed())
						output.writeUTF(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void LogHandler(Message<?> msg) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		switch (msg.getHead()) {
		case "GETCUSTOMERORDERS":
			logService.AddLog("Xem đơn hàng");
			break;
		case "GETALL":
			logService.AddLog("Xem danh sách sản phẩm");
			break;
		case "FIND":
			Product product = mapper.readValue(msg.getObject(), Product.class);
			logService.AddLog("người dùng đang tìm kiếm: '" + product.getName() + "'");
			break;
		}
	}
}
