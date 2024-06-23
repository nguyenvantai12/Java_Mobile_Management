package doan.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.Category;
import doan.models.Message;
import doan.repository.ICategoryRepository;

public class CategoryService implements IService {
	ICategoryRepository categoryRepository;
	ObjectMapper objectMapper = new ObjectMapper();

	public CategoryService(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;

	}

	@Override
	public Message<?> Execute(String command, String objectJson, Class<?> type)
			throws SQLException, JsonProcessingException {
		Category category = null;
		 
		if (objectJson != null) {
 
			category = objectMapper.readValue(objectJson, Category.class);
		}
		if(command.equals("GETALL")) {
			return getAllCategory();
		}
		switch (command) {
			case "DELETE":
				return deleteCategory(category.getId());
			case "GETALL":
				return getAllCategory();
			case "CREATE":
				return createCategory(category);
			case "UPDATE":
				return updateCategory(category);
			default:
				return new Message<>("ERROR", String.class, "Không tìm thấy thao tác");
		}
	}

	private Message<?> getAllCategory() throws SQLException, JsonProcessingException {
		ResultSet res = categoryRepository.GetAll();
		List<Category> products = categoryRepository.ProcessData(res);
		String productsJson = objectMapper.writeValueAsString(products);
		return new Message<>("SUCCESS", List.class, productsJson);
	}

	private Message<?> createCategory(Category cate) throws SQLException, JsonProcessingException {
		categoryRepository.Create(cate);
		String res = "Sản phẩm tạo thành công";
		return new Message<>("SUCCESS", String.class, res);
	}

	private Message<?> updateCategory(Category cate) throws SQLException {
		categoryRepository.Update(cate);
		return new Message<>("SUCCESS", String.class, "Sản phẩm đã được cập nhật thành công");
	}

	private Message<?> deleteCategory(Integer cate) throws SQLException, JsonProcessingException {
		categoryRepository.Delete(cate);
		String res = "Xóa thành công";
		return new Message<>("SUCCESS", String.class, res);
	}

}
