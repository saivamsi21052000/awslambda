package com.app.easy2excel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.app.easy2excel.entity.Employee;

import java.util.List;
import java.util.Map;

public class EmployeeService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;

    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        Employee employee = Utility.convertStringToObj(apiGatewayRequest.getBody(),context);
        dynamoDBMapper.save(employee);
        jsonBody = Utility.convertObjToString(employee,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return createAPIResponse(jsonBody,201,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent getEmployeeById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        Employee employee =   dynamoDBMapper.load(Employee.class,empId)  ;
        if(employee!=null) {
            jsonBody = Utility.convertObjToString(employee, context);
            context.getLogger().log("fetch employee By ID:::" + jsonBody);
             return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Employee Not Found Exception :" + empId;
             return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
       
    }

    public APIGatewayProxyResponseEvent getEmployees(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        List<Employee> employees = dynamoDBMapper.scan(Employee.class,new DynamoDBScanExpression());
        jsonBody =  Utility.convertListOfObjToString(employees,context);
        context.getLogger().log("fetch employee List:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent deleteEmployeeById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        Employee employee =  dynamoDBMapper.load(Employee.class,empId)  ;
        if(employee!=null) {
            dynamoDBMapper.delete(employee);
            context.getLogger().log("data deleted successfully :::" + empId);
            return createAPIResponse("data deleted successfully." + empId,200,Utility.createHeaders());
        }else{
            jsonBody = "Employee Not Found Exception :" + empId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
    }


    private void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }
    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }
    
    
    
    public APIGatewayProxyResponseEvent updateEmployee(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        initDynamoDB();
        
        String empId = apiGatewayRequest.getPathParameters().get("empId");
        
        // Check if the employee exists
        Employee existingEmployee = dynamoDBMapper.load(Employee.class, empId);
        if (existingEmployee == null) {
            String errorMessage = "Employee with ID " + empId + " not found.";
            context.getLogger().log(errorMessage);
            return createAPIResponse(errorMessage, 404, Utility.createHeaders());
        }
        
        // Convert the request body to Employee object
        Employee updatedEmployee = Utility.convertStringToObj(apiGatewayRequest.getBody(), context);
        
        // Update the existingEmployee with the new values
        existingEmployee.setEmpId(updatedEmployee.getEmpId());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setDomain(updatedEmployee.getDomain());
        existingEmployee.setAge(updatedEmployee.getAge());
        
        // You can add more properties to update based on your Employee class
        
        // Save the updated employee to DynamoDB
        dynamoDBMapper.save(existingEmployee);
        
        String jsonBody = Utility.convertObjToString(existingEmployee, context);
        context.getLogger().log("Employee updated successfully: " + jsonBody);
        return createAPIResponse(jsonBody, 200, Utility.createHeaders());
    }

}
