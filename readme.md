# Cinema

![example workflow](https://github.com/semura823/cinema-project/actions/workflows/ci.yml/badge.svg)

## Controllers

1. Films
   1. [Getting premieres](https://github.com/SeMurA823/cinema-project#getting-premieres)
   2. [Getting film description](https://github.com/SeMurA823/cinema-project#getting-film-description)
   3. [Getting upcoming film screenings](https://github.com/SeMurA823/cinema-project#getting-upcoming-film-screenings)
   4. [Getting archive films](https://github.com/SeMurA823/cinema-project#getting-archive-films)
   5. [Adding a movie](https://github.com/SeMurA823/cinema-project#adding-a-movie-for-admin)
   6. [Delete a movie](https://github.com/SeMurA823/cinema-project#delete-a-movie-for-admin)
   7. [Disable a movie](https://github.com/SeMurA823/cinema-project#disable-a-movie-for-admin)
2. Booking
   1. [Create reservation](https://github.com/SeMurA823/cinema-project#create-reservation-for-customer)
   2. [List of reserved seats](https://github.com/SeMurA823/cinema-project#list-of-reserved-seats-for-customer)
   3. [Cancel Reservation](https://github.com/SeMurA823/cinema-project#cancel-reservation-for-customer)

* ### Getting premieres
  `/api/films/premiers?size={size}&page={page}`

  **Params**

    * `size` - page size <span style="color:red">(required)</span>

    * `page` - page number <span style="color:red">(required)</span>

  **Return**

  Premieres List in JSON-format


* ### Getting film description
  `/api/films/{id}`

  **Params**

    * `id` - film id <span style="color:red">(required)</span>

  **Return**

  Film description in JSON-format

* ### Getting upcoming film screenings
  `/api/screenings/{id}?start={start}&end={end}`

  If start date isn't specified, the start date is the current time

  If end date isn't specified, the end date is the end of that day

  If start and end dates aren't specified, the start date is the current time and the end date is the end of the that
  day

  **Params**

    * `id` - film id <span style="color:red">(required)</span>
    * `start` - start date (Format: `yyyy-MM-dd`)
    * `end` - end date (Format: `yyyy-MM-dd`)

  **Return**

  Film screenings list in JSON-format

* ### Getting archive films
  `/api/films/archive?page={page}&size={size}`

  **Params**

    * `page` - page size <span style="color:red">(required)</span>
    * `size` - page number <span style="color:red">(required)</span>

  **Return**

  List of archived films in JSON-format

* ### Adding a movie <span style="color:red">(for admin)</span>
  `/api/films/add`

  **Params**

    * `none`

  **Input**

  Example:

  ```json
  {
    "name":"Добрыня Никитич и Змей Горыныч",
    "localPremiere":"2006-03-16",
    "worldPremiere":"2006-03-16",
    "ageLimit":"0+",
    "plot":"Пока храбрый воин Добрыня Никитич собирал с тугар дань, в Киеве похитили любимую племянницу князя - Забаву Путятичну. Вопреки воле князя, Добрыня с женихом Забавы Елисеем отправляется на ее поиски. В пути их ждет много приключений...",
    "countries":[
      "ru"
    ]
  }
  ```

  **Return**

  Film description in JSON-format

* ### Delete a movie <span style="color:red">(for admin)</span>
  `/api/films/{id}/delete`

  **Params**
    * `id` - film id <span style="color:red">(required)</span>

  **Return**

  Status entity

* ### Disable a movie <span style="color:red">(for admin)</span>
  `/api/films/{id}/disable`

  Disabling an entity without deleting

  **Params**
    * `id` - film id <span style="color:red">(required)</span>

  **Return**

  Status entity

* ### Create reservation <span style="color:red">(for customer)</span>
  `/api/reserve/{screening}/create`

  Seat reservation

  **Params**

  * `screening` - film screening id

  **Input**
  ```json
  {
    "num": 12,
    "row": 2
  }
  ```

  **Return**

  Booking description
  ```json
  {
    "id": 10,
    "seat": {
        "id": 27,
        "row": "2",
        "number": "12",
        "disabled": false
    },
    "expiryDate": "2021-11-05T19:07:10.674+00:00",
    "filmScreening": {
        "id": 1,
        "hall": {
            "id": 1,
            "name": "Зал 1"
        },
        "date": "2021-11-04T06:00:00.000+00:00"
    }
  }
  ```

* ### List of reserved seats <span style="color:red">(for customer)</span>
  `/api/reserve/`

  Getting list of reserved seats

  **Params**

  `none`

  **Return**

  Booking description
  ```json
  {
    "content": [
        {
            "id": 10,
            "seat": {
                "id": 27,
                "row": "2",
                "number": "12",
                "disabled": false
            },
            "expiryDate": "2021-11-05T19:07:10.674+00:00",
            "filmScreening": {
                "id": 1,
                "hall": {
                    "id": 1,
                    "name": "Зал 1"
                },
                "date": "2021-11-04T06:00:00.000+00:00"
            }
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "pageNumber": 0,
        "pageSize": 10,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "first": true,
    "number": 0,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "numberOfElements": 1,
    "size": 10,
    "empty": false
  }
  ```

* ### Cancel Reservation <span style="color:red">(for customer)</span>
  `/api/reserve/{reserve}/cancel`
  
  Cancel reservation

  **Params**

  * `reserve` - booking ID <span style="color:red">(required)</span>

  **Return**

  Booking status

## Security

AuthorizationType: Bearer

**Login:**
```json
{
    "username":"username",
    "password":"123456"
}
```

OR

**Login by refresh token**
```json
{
    "username":"token_auth",
    "password":"MGYyYmI3ZWItMjk2OS00NDEzLWI1ZTgtMWNmMTRiMTE4MjNhOis3OTg3Mzg0OTgxNw=="
}
```
**Return:**
```json
{
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIrNzk4NzM4NDk4MTciLCJleHAiOjE2MzcxNzc0MjksImlhdCI6MTYzNzE3NDQyOX0.CDeMyj5R8qYHOP7NGj9ADDnf0Nw3-0PPDEUBUjDaqbY",
    "refresh_token": "MTYxOTdjYzQtZjJjYS00NTBlLWI1NmItMTc5NThlZjg5YWVhOis3OTg3Mzg0OTgxNw==",
    "expires_in": "2021-11-17T19:30:29.000+00:00"
}
```

## Entity-Relationship model

![Entity-Relationship model](/erd.png)
