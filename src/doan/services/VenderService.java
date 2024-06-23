package doan.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Message;
import doan.models.Vendor;
 import doan.repository.IVendorRepository;

public class VenderService implements IService {
	private IVendorRepository vendorRepository;
	private ObjectMapper objectMapper;

	public VenderService(IVendorRepository vendorRepository) {
		super();
		this.vendorRepository = vendorRepository;
		objectMapper = new ObjectMapper();
	}

	@Override
	public Message<?> Execute(String command, String objectJson, Class<?> type)
			throws SQLException, JsonProcessingException {
		Vendor vendor   = objectMapper.readValue(objectJson, Vendor.class);
		 
	 
			
		 

		switch (command) {
		case "GETALL":
			return GetAllProducts();
		case "DELETE":
			return deleteProduct(vendor.getId());
		case "UPDATE":
			return updateProduct(vendor);
		case "CREATE":
			return CreateProduct(vendor);
		default:
			return new Message<>("ERROR", String.class, "Không tìm thấy thao tác");
		}
	}

	private Message<?> GetAllProducts() throws SQLException, JsonProcessingException {
		ResultSet res = vendorRepository.GetAll();
		List<Vendor> products = vendorRepository.ProcessData(res);
		String productsJson = objectMapper.writeValueAsString(products);
		return new Message<>("SUCCESS", List.class, productsJson);
	}

	private Message<?> CreateProduct(Vendor product) throws SQLException, JsonProcessingException {
		vendorRepository.Create(product);
		String res = "Sản phẩm tạo thành công";
		return new Message<>("SUCCESS", String.class, res);
	}

	private Message<?> updateProduct(Vendor product) throws SQLException {
		vendorRepository.Update(product);
		return new Message<>("SUCCESS", String.class, "Sản phẩm đã được cập nhật thành công");
	}

	private Message<?> deleteProduct(Integer id) throws SQLException {
		vendorRepository.Delete(id);
		return new Message<>("SUCCESS", String.class, "Sản phẩm đã xóa thành công");
	}

}
