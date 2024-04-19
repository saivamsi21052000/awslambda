package com.app.easy2excel;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    @Override
    public APIGatewayProxyResponseEvent  handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        EmployeeService employeeService = new EmployeeService();
        switch (apiGatewayRequest.getHttpMethod()) {

            case "POST":
                return employeeService.saveEmployee(apiGatewayRequest, context);

            case "GET":
                if (apiGatewayRequest.getPathParameters() != null) {
                    return employeeService.getEmployeeById(apiGatewayRequest, context);
                }
                return employeeService.getEmployees(apiGatewayRequest, context);
            case "DELETE":
                if (apiGatewayRequest.getPathParameters() != null) {
                    return employeeService.deleteEmployeeById(apiGatewayRequest, context);
                }
            case "PUT":
                if (apiGatewayRequest.getPathParameters() != null) {
                    return employeeService.updateEmployee(apiGatewayRequest, context);
                }
            default:
                throw new Error("Unsupported Methods:::" + apiGatewayRequest.getHttpMethod());

        }
    }
 }
