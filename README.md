# Custom Camunda with Keycloak Integration

## Description
This project implements custom endpoints to get data from `Camunda` tables. From this project, all camunda endpoints are 
available, along with that, few custom endpoints implemented. For example, I have implemented comments customisation and used best 
practice to pull data from `H2 database` using `MyBatis` which is recommended by camunda community. Anyone can follow 
this example and implement desired custom endpoints.

Apart from the customisation of endpoints, I have also implemented the integration of `Keycloak` with this application. So, when user 
run this application, it will directly go to keycloak login page. After providing keycloak authentication, successful 
user will redirect to camunda dashboard. After that, all camunda features will be available. But for this there are some keycloak 
constraints that need to provide.

## Features of this project
- Custom endpoints implementation - create comment, get all comments by process ID, get all comments by process ID with 
grouping of task ID.
- Keycloak integration
- NB - Only custom REST endpoints are implemented here, no UX related work is done.


## Constraints for database and keycloak integration
All information related to database, keycloak server and spring security oauth2 is resides in `application.yaml` file.
