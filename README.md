# Spring REST Chat
[![Build Status](https://travis-ci.com/denisRudie/spring-rest-chat.svg?branch=main)](https://travis-ci.com/denisRudie/spring-rest-chat)
[![codecov](https://codecov.io/gh/denisRudie/spring-rest-chat/branch/main/graph/badge.svg)](https://codecov.io/gh/denisRudie/spring-rest-chat)

### О проекте
Проект предоставляет набор API для функционирования чата.

### Technologies:
* Java 14
* Spring (Boot, Data, MVC, Security (JWT), AOP)
* Web
* Postgres
* Junit, Mockito
* Maven

### REST API
#### для всех пользователей:

| команда              | запрос                                                                           | ответ                                              
|----------------------|----------------------------------------------------------------------------------|-------------------------|
| авторизация         | POST ```https://sitename/login```  Body: ```{login: "john" , pwd: "123"}``` | Код состояния:```200 OR 401``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."``` |
| регистрация         | POST ```https://sitename/users/sign-up```  Body: ```{username: "DENIS", password: "123"}``` | Код состояния:```200``` |

#### для авторизированного пользователя:
##### messages:
| команда                                 | запрос                                                                | ответ                                              |
|-----------------------------------------|-----------------------------------------------------------------------|----------------------------------------------------|
| получить все сообщения | GET ```https://sitename/messages/``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."``` | Код состояния:```200``` Header: ```"Content-Type: application/json"```  Body: ```[{"id":1,"created":"","text":"test","author":{"id":1},"room":{"id":1,"name":"room-test"}}]``` |
| получить сообщение по id | GET ```https://sitename/messages/1``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."```  | Код состояния:```200``` Header: ```"Content-Type: application/json"```  Body: ```{"id":1,"created":"","text":"test","author":{"id":1},"room":{"id":1,"name":"room-test"}}``` |
| отправить новое сообщение | POST ```https://sitename/messages/``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q...", "Content-Type: application/json"``` Body: ```{"text":"test","author":{"id":1},"room":{"id":1}}``` | Код состояния:```200``` Header: ```"Content-Type: application/json"``` Body: ```{"id":1,"created":"","text":"test","author":{"id":1},"room":{"id":1,"name":"room-test"}}``` |
| изменить сообщение | PUT ```https://sitename/messages/1```  Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q...", "Content-Type: application/json"``` Body: ```{"id":1,"created":"","text":"test","author":{"id":1},"room":{"id":1,"name":"room-test"}}``` | Код состояния:```200``` |
| удалить сообщение | DELETE ```https://sitename/messages/1``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."``` | Код состояния:```200``` |

##### rooms:
| команда                                 | запрос                                                                | ответ                                              |
|-----------------------------------------|-----------------------------------------------------------------------|----------------------------------------------------|
| получить все чаты | GET ```https://sitename/rooms/``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."``` | Код состояния:```200``` Header: ```"Content-Type: application/json"```  Body: ```[{"id":1,"name":"room-test"}]``` |
| получить чат по id | GET ```https://sitename/rooms/1``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."```  | Код состояния:```200``` Header: ```"Content-Type: application/json"```  Body: ```{"id":1,"name":"room-test"}``` |
| создать новый чат | POST ```https://sitename/rooms/``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q...", "Content-Type: application/json"``` Body: ```{"name":"test"}``` | Код состояния:```200``` Header: ```"Content-Type: application/json"``` Body: ```{"id":1,"name":"room-test"}``` |
| изменить чат | PUT ```https://sitename/rooms/1```  Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q...", "Content-Type: application/json"``` Body: ```{"id":1,"name":"room-test2"}``` | Код состояния:```200``` |
| удалить чат | DELETE ```https://sitename/rooms/1``` Header: ```"Authorization: Bearer eyJ0eXAiOiJKV1Q..."``` | Код состояния:```200``` |