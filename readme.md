# Cinema

![example workflow](https://github.com/semura823/cinema-project/actions/workflows/ci.yml/badge.svg)

### Controllers

* ### Getting premieres
  `/api/films/premiers?size={size}&page={page}`

  **Params**

  * **size** - page size

  * **page** - page number

  **Return**

  * Premieres List in JSON-format


* ### Getting film description
  `/api/films/{id}`

  **Params**

  * **id** - film id

  **Return**

  * Film description in JSON-format