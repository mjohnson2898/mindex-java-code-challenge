# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## New Endponts Created

### ReportingStructure

#### Endpoints

```
* getReport
  * HTTP Method: GET
  * URL: localhost:8080/report/{id}
  * Response: ReportingStructure
```
For the endpoint, "id" refers to an "employeeId"

The ReportingStructure has a JSON schema of:

```json
{
  "type" : "ReportingStructure",
  "properties" : {
    "employee": {
      "type": "Employee",
      "properties" : {
        "employeeId": {
          "type": "string"
        },
        "firstName": {
          "type": "string"
        },
        "lastName": {
          "type": "string"
        },
        "position": {
          "type": "string"
        },
        "department": {
          "type": "string"
        },
        "directReports": {
          "type": "array",
          "items" : "string"
        }
      }
    },
    "numberOfReports": {
      "type": "int"
    }
  }
}
```

### Compensation

#### Endpoints

```
* CREATE
    * HTTP Method: POST
    * URL: localhost:8080/compensation
    * Payload: Compensation
    * Response: Compensation
* READ
    * HTTP Method: GET
    * URL: localhost:8080/compensation/{id}
    * Response: Compensation
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

Compensation has a JSON Schema of:
```json
{
  "type" : "Compensation",
  "properties" : {
    "employee": {
      "type": "Employee",
      "properties" : {
        "employeeId": {
          "type": "string"
        },
        "firstName": {
          "type": "string"
        },
        "lastName": {
          "type": "string"
        },
        "position": {
          "type": "string"
        },
        "department": {
          "type": "string"
        },
        "directReports": {
          "type": "array",
          "items" : "string"
        }
      }
    },
    "salary": {
      "type": "long"
    },
    "effectiveDate" : {
      "type" : "Date"
    }
  }
}
```
For effectiveDate, the value has the format of yyyy-MM-dd
