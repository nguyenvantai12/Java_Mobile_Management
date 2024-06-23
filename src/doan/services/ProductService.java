package doan.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.*;
import doan.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductService implements IService {
	private IProductRepository productRepository;
	private ObjectMapper objectMapper;

	public ProductService(IProductRepository productRepository) {
		this.productRepository = productRepository;
		objectMapper = new ObjectMapper();
	}

	@Override
	public Message<?> Execute(String command, String objectJson, Class<?> type)
			throws SQLException, JsonProcessingException {
		Product product=null;
		Integer id=0;
			product = objectMapper.readValue(objectJson, Product.class);
		 
		
		switch (command) {
		case "GETALL":
			return GetAllProducts();
		case "FIND":
			return FindProducts(product);
		case "DELETE":
			return deleteProduct(product.getId());
		case "UPDATE":
			return updateProduct(product);
		case "CREATE":
			return CreateProduct(product);
		default:
			return new Message<>("ERROR", String.class, "Không tìm thấy thao tác");
		}
	}

	private Message<?> FindProducts(Product product) throws SQLException, JsonProcessingException {
		ResultSet res = productRepository.FindProductByNameAndDescriptionContentsAndCategory(product);
		List<Product> products = productRepository.ProcessData(res);
		String productsJson = objectMapper.writeValueAsString(products);
		return new Message<>("SUCCESS", List.class, productsJson);
	}

	private Message<?> GetAllProducts() throws SQLException, JsonProcessingException {
		ResultSet res = productRepository.GetAll();
		List<Product> products = productRepository.ProcessData(res);
		String productsJson = objectMapper.writeValueAsString(products);
		return new Message<>("SUCCESS", List.class, productsJson);
	}

	private Message<?> CreateProduct(Product product) throws SQLException, JsonProcessingException {
		productRepository.Create(product);
		String res = "Sản phẩm tạo thành công";
		return new Message<>("SUCCESS", String.class, res);
	}

    private Message<?> updateProduct(Product product) throws SQLException
    {
    	productRepository.Update(product);
        return new Message<>("SUCCESS", String.class, "Sản phẩm đã được cập nhật thành công");
    }
    private Message<?> deleteProduct(Integer id) throws SQLException
    {
    	productRepository.Delete(id);
        return new Message<>("SUCCESS", String.class, "Sản phẩm đã được cập nhật thành công");
    }
	

}
