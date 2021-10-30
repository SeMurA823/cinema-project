# Cinema

![example workflow](https://github.com/semura823/cinema-project/actions/workflows/ci.yml/badge.svg)

### Controllers

* ### Getting premieres
  `/api/films/premiers?size={size}&page={page}`

  **Params**

  * **size** - page size <span style="color:red">(required)</span>

  * **page** - page number <span style="color:red">(required)</span>

  **Return**

  * Premieres List in JSON-format


* ### Getting film description
  `/api/films/{id}`

  **Params**

  * **id** - film id <span style="color:red">(required)</span>

  **Return**

  * Film description in JSON-format

* ### Getting upcoming film screenings
  `/api/screenings/{id}?start={start}&end={end}`

  If start date isn't specified, the start date is the current time

  If end date isn't specified, the end date is the end of that day

  If start and end dates aren't specified, the start date is the current time and the end date is the end of the that day

  **Params**

  * **id** - film id <span style="color:red">(required)</span>
  * **start** - start date (Format: `yyyy-MM-dd`)
  * **end** - end date (Format: `yyyy-MM-dd`)

  **Return**

  * Film screenings list in JSON-format
