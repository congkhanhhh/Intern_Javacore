package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataClose {
    public void closeCon(Connection connection){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closePst(PreparedStatement preparedStatement){
        try{
            if(preparedStatement != null && !preparedStatement.isClosed()){
                preparedStatement.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void closeRs(ResultSet resultSet){
        try{
            if(resultSet != null && !resultSet.isClosed()){
                resultSet.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
