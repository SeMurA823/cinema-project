# Cinema project

## Methods:

- **AuthN**
    - [login](#login-post)
    - [refresh](#refresh-post)
    - [logout](#logout-post)
    - [registration](#registration-post)

### AuthN

#### Login (POST)

URL: ```/api/auth?login```

request body:

   ```json
{
  "username": "+12345678910",
  "password": "123456",
  "rememberMe": true
} 
   ```

response body:

```json
{
  "clientId": "5eaec02f-d346-4819-8069-2f7522de0d60",
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNzk4NzM4NDk4MTciLCJleHAiOjE2MzgzODg2MjEsImlhdCI6MTYzODM4NTYyMX0.EOeuOS028jUS2rBozX05Yx_axhzAX7RxoQO8Jk84pGw",
  "refreshToken": "NDNjZDQyNGRjN2Y0NDM0YTk0NmZlYmJlOWVkNGZmNTA6Kzc5ODczODQ5ODE3",
  "tokenType": "bearer",
  "expiresIn": 1638388621000
}
```

response headers:

...

Set-Cookie: ```Refresh=ZTNlNDFhZjRkNzFhNDJmNGIyMTI0OWQ5YmRiZWE4MTk6Kzc5ODczODQ5ODE3; Path=/; Max-Age=172800000; Expires=Mon, 24 May 2027 18:54:12 GMT; HttpOnly; SameSite=LAX```

Set-Cookie: ```ClientID=68cde2df-58f7-4a20-8a49-88f4c5949f69; Path=/; Max-Age=2147483647; Expires=Mon, 19 Dec 2089 22:08:19 GMT; HttpOnly; SameSite=LAX```

#### Refresh (POST)

URL: ```/api/auth?refresh```

request cookies:

- ```ClientID=5eaec02f-d346-4819-8069-2f7522de0d60; Path=/; HttpOnly; Expires=Mon, 19 Dec 2089 22:21:08 GMT;```
- ```Refresh=NzkwMjQ4MzFkNTZhNGQ2M2IxNDdkOTdhYjIyZDBhZWQ6Kzc5ODczODQ5ODE3; Path=/; HttpOnly; Expires=Mon, 24 May 2027 19:07:37 GMT;```

response body:

```json
{
  "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNzk4NzM4NDk4MTciLCJleHAiOjE2MzgzODg2NTcsImlhdCI6MTYzODM4NTY1N30.C_r1domvwFDDDFWRg0md1JNOSMshOZrbHQPhrqmGWhU",
  "refresh_token": "NzkwMjQ4MzFkNTZhNGQ2M2IxNDdkOTdhYjIyZDBhZWQ6Kzc5ODczODQ5ODE3",
  "token_type": "bearer",
  "expires_in": 1638388657000
}
```

response headers:

...

Set-Cookie: ```Refresh=NzkwMjQ4MzFkNTZhNGQ2M2IxNDdkOTdhYjIyZDBhZWQ6Kzc5ODczODQ5ODE3; Path=/; Max-Age=172800000; Expires=Mon, 24 May 2027 19:07:37 GMT; HttpOnly; SameSite=LAX```

#### Logout (POST)

URL: ```/api/auth/logout```

request headers:

- **Authorization**: Bearer
  eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNzk4NzM4NDk4MTciLCJleHAiOjE2MzgzODg2NTcsImlhdCI6MTYzODM4NTY1N30.C_r1domvwFDDDFWRg0md1JNOSMshOZrbHQPhrqmGWhU

request cookies:

- ```ClientID=5eaec02f-d346-4819-8069-2f7522de0d60; Path=/; HttpOnly; Expires=Mon, 19 Dec 2089 22:21:08 GMT;```
- ```Refresh=NzkwMjQ4MzFkNTZhNGQ2M2IxNDdkOTdhYjIyZDBhZWQ6Kzc5ODczODQ5ODE3; Path=/; HttpOnly; Expires=Mon, 24 May 2027 19:07:37 GMT;```

response headers:

...

Set-Cookie: ```Refresh=NzkwMjQ4MzFkNTZhNGQ2M2IxNDdkOTdhYjIyZDBhZWQ6Kzc5ODczODQ5ODE3; Path=/; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; HttpOnly; SameSite=LAX```

#### Registration (POST)

URL: ```/api/auth/registration```

request body:

```json
{
    "username":"+12345678910",
    "password":"123456",
    "firstName":"Semapa",
    "lastName":"Semura",
    "birthDate": "2002-08-02",
    "gender": "female"
}
```

response body:

```json
{
  "id": 2,
  "userRoles": [
    {
      "id": 3,
      "role": "CUSTOMER"
    }
  ],
  "firstName": "Semapa",
  "lastName": "Semura",
  "patronymic": null,
  "birthDate": "2002-08-02T00:00:00.000+00:00",
  "gender": "female",
  "enabled": true,
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true
}
```