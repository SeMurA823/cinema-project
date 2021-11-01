# Cinema

![example workflow](https://github.com/semura823/cinema-project/actions/workflows/ci.yml/badge.svg)

## Controllers

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

  If start and end date isn't specified, the start date is the current time and the end date is the end of the that day

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

## Security
  AuthorizationType: Basic
