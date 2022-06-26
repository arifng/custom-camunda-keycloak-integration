# Custom Camunda with Keycloak Integration
## Description
This project implements custom endpoints to get data from camunda tables. From this project, all camunda endpoints are 
available, along with that, few endpoints implemented. For example, I implemented comments customisation and using best 
practice I pulled data from H2 database using MyBatis which is recommended by camunda community. Anyone can follow 
this example and implement desired custom endpoints.

Apart from the customisation of endpoints, I also implement integration of keycloak with this application. So, when user 
run this application, it will directly go to keycloak login page. After providing keycloak authentication, successful 
user go to camunda dashboard. After that, all camunda features will be available. But for this there are some keycloak 
constraints that need to provide.

## Features of this project
- Custom endpoints implementation - create comment, get all comments by process ID, get all comments by process ID with 
grouping of task ID.
- Keycloak integration


## Constraints for database and keycloak integration
All information related to database, keycloak server and spring security oauth2 is resides in `application.yaml` file.
