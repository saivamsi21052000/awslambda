package com.app.easy2excel.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="employee")
public class Employee {

    @DynamoDBHashKey(attributeName = "empId")
    private String empId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "email")
    private String email;
    
    @DynamoDBAttribute(attributeName = "address")
    private String address;
    
    @DynamoDBAttribute(attributeName = "phoneNumber")
    private String phoneNumber;
    
    @DynamoDBAttribute(attributeName = "domain")
    private String domain;

    @DynamoDBAttribute(attributeName = "age")
    private Integer age;
    
       
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDomain() {
        return domain;
    }
    
    public Integer getAge() {
        return age;
    }
    
}
