package doan.services;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;

import doan.models.Message;

 
public interface IService
{
    Message<?> Execute(String command, String objectJson, Class<?> type) throws SQLException, JsonProcessingException;
}
