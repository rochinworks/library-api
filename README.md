## Running the project
`./gradlew build`
`java -jar ./build/lib/books-0.0.1-SNAPSHOT.jar`

### Running with docker
`docker build -t {tag} .`
`docker run -it {tag}`

## Endpoints

### Create Book
- url: localhost:8081/books
- method: POST
- request body: {"title": "", "author": ""}

### Get Book by Id
- url: localhost:8081/books/:id
- method: GET

### Get All Books
- url: localhost:8081/books
- method: GET

### Update Book by Id
- url: localhost:8081/books/:id
- method: PUT
- request body: {"title": "", "author": ""}

### Delete Book by Id
- url: localhost:8081/books/:id
- method: DELETE
