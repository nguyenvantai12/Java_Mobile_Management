package doan.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message<T>
{
    private String head; //operation
    private Class<T> type; //table
    private String object; //object to operate

    public Message()
    {
    }
    public Message(String operation, Class<T> type, String object)
    {
        head = operation;
        this.type = type;
        this.object = object;
    }

    public Class<T> getType()
    {
        return type;
    }

    public void setType(Class<T> type)
    {
        this.type = type;
    }

    public String getHead()
    {
        return head;
    }

    public void setHead(String head)
    {
        this.head = head;
    }

    public String getObject()
    {
        return object;
    }

    public void setObject(String object)
    {
        this.object = object;
    }

    @Override
    public String toString()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage;
        try
        {
            jsonMessage = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return "Error";
        }
        return jsonMessage;
    }
}