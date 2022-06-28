# Custom Camunda with Keycloak Integration

## Description
This project runs as standalone application with `Camunda BPM` and enable all features of camunda bpm.

It implements custom endpoints to get data from `Camunda` tables. From this project, all camunda endpoints are 
available, along with that, few custom endpoints implemented. For example, I have implemented comments customisation and used best 
practice to pull data from `H2 database` using `MyBatis` which is recommended by camunda community. Anyone can follow 
this example and implement desired custom endpoints. Also, there is an example endpoint implemented for activity instances.

Apart from the customisation of endpoints, I have also implemented the integration of `Keycloak` with this application. So, when user 
run this application, it will directly go to keycloak login page. After providing keycloak authentication, successful 
user will redirect to camunda dashboard. After that, all camunda features will be available. But for this there are some keycloak 
constraints that need to provide.

## Features
- Custom endpoints implementation - create comment, get all comments by process ID, get all comments by process ID with 
grouping of task ID.
- Keycloak integration
- NB - Only custom REST endpoints are implemented here, no UX related work is done.


## Constraints for database and keycloak integration
All information related to database, keycloak server and spring security oauth2 is resides in `application.yaml` file.


## Sample output for REST endpoints
### Get comments of a process grouping by task
Input : `/api/process/962c5aad-4ae8-11ec-ad37-00dbdf8ca9fb/comments-pretty`

Output :
```
[
    {
        "id": "97373b00-4ae8-11ec-ad37-00dbdf8ca9fb",
        "name": "Say hello to\ndemo",
        "assignee": null,
        "created": "2021-11-21T16:32:17.148+00:00",
        "due": null,
        "followUp": null,
        "delegationState": null,
        "description": null,
        "executionId": "962c5aad-4ae8-11ec-ad37-00dbdf8ca9fb",
        "owner": null,
        "parentTaskId": null,
        "priority": 50,
        "processDefinitionId": "my-project-process:1:2772b684-4ae8-11ec-8b9b-00dbdf8ca9fb",
        "processInstanceId": "962c5aad-4ae8-11ec-ad37-00dbdf8ca9fb",
        "taskDefinitionKey": "say-hello",
        "caseExecutionId": null,
        "caseInstanceId": null,
        "caseDefinitionId": null,
        "suspended": true,
        "formKey": null,
        "tenantId": null,
        "commentDtos": [
            {
                "links": [],
                "id": "a730e401-4bac-11ec-97f3-00dbdf8ca9fb",
                "userId": null,
                "time": "2021-11-22T15:55:45.203+00:00",
                "taskId": "97373b00-4ae8-11ec-ad37-00dbdf8ca9fb",
                "message": "Another comment of say-hello task",
                "removalTime": null,
                "rootProcessInstanceId": "962c5aad-4ae8-11ec-ad37-00dbdf8ca9fb"
            },
            {
                "links": [],
                "id": "c29e5310-4ba7-11ec-be8e-00dbdf8ca9fb",
                "userId": null,
                "time": "2021-11-22T15:20:43.823+00:00",
                "taskId": "97373b00-4ae8-11ec-ad37-00dbdf8ca9fb",
                "message": "a task comment",
                "removalTime": null,
                "rootProcessInstanceId": "962c5aad-4ae8-11ec-ad37-00dbdf8ca9fb"
            }
        ]
    }
]
```
