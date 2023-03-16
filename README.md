# FileService

## Used Technologies:
     SpringBoot, JPA, H2 , lombok  (Java version 17)

## Assumptions: 

-	Only one file can be uploaded at a time.      

-	The accepted file extensions are txt, json, xml, log, cfg, ini, rtf, and md. To add further text file options, update the "isTextFile" method under "FileUploadHelper".

-	The maximum file size is defined in the application.properties file as follows:
    - spring.servlet.multipart.max-file-size=2MB
    - spring.servlet.multipart.max-request-size=2MB

    - This can be updated if necessary, but make sure that the web server also supports the new file size.

-	Empty files cannot be uploaded.

- **On upload**:

    - The file itself is copied to a folder defined in the configuration class, i.e., StorageProperties. Currently, this is "upload-dir".

    - Metadata information, including the path of the file, is stored on the fly in the H2 table. To maintain consistency between the data in the database, all files under "upload-dir" are deleted at the start of the application.

- There are two on-the-fly tables: "files" and "longest_hundred".
  - You can see the column properties, including the maximum allowed length, under the "FileEntity" and "LongestLineEntity" classes.

  - The constrains are determined based on the H2 limitations: http://www.h2database.com/html/advanced.html#limits_limitations


## End Points: 

•	Upload a text file and store it:
 http://localhost:8080/fileService/upload  

•	One random line: (from recently uploaded file): 
http://localhost:8080/fileService/random-line

•	One Random Line Backwards (choose all file):
http://localhost:8080/fileService/random-line-backwards

•	Longest 100 Lines (of all files): 
http://localhost:8080/fileService/longest-100

•	20 Longest Lines of One File (from randomly chosen file)
http://localhost:8080/fileService/longest-20




## Design Decisions:

- Used DTO classes and mappers specific to each DTO. Although this may not be necessary for this small project, using DTOs has some advantages, such as hiding the implementation details of domain objects (JPA entities) and being an efficient projection for read operations. It is especially better to use them when the requested data does not need to be changed.

- To increase performance and prevent memory-related errors during file upload and reading, the following were applied:

  - “MultipartFile” is used as a parameter of the upload controller.

  - While reading lines from “MultipartFile”, “BufferedReader” is used instead of getting all bytes. “BufferedReader” reads the file line by line, without having to load the entire content into memory. 

  - “BufferedReade”r is used instead of the "Scanner" class because it has several advantages, such as having a larger buffer memory than Scanner and working with multiple threads.

- To increase performance while selecting a random line “Reservoir sampling algorithm” is implemented. It requires only a single pass over the data without needing to store the entire dataset in memory and is also suitable for streaming data.

- To increase response performance during “Longest 100 Lines” and “20 Longest Lines” API calls, followings are handled during the upload call:

  - The 20 longest line (excluding blank lines) of the uploaded file stored are in a single column of files table.

  - The 100 longest line among all uploaded files are evaluated and saved to “longest_hundred” table.

  - PriorityQueue is used while evaluating 20 and 100 longest lines.

  - Moreover, since upload service handle multiple transactions, it is annotated as   @Transactional(rollbackFor = Exception.class)

- Followings are implemented for Exception Handling: 
  - Global Exception handling is adapted. Controller Advisor class and custom exception classes are implemented.

  - In order to make Error responses clearer for Rest calls, “ApiError” classes are implemented, you can find them under “com.springboot.rest.exception”. Although some of the implemented methods may not be critical for this project, they have been included to demonstrate best practices.


## Possible Further implementations:

•	Hypermedia as the Engine of Application State (HATEOAS) can be applied to the project to improve discoverability while reducing coupling between clients and server. With HATEOAS, clients can dynamically discover and navigate the available resources of an API.

•	Swagger can be used to generate interactive API documentation. 

•	Test coverage can be increased by implementing further unit and integration tests.

•	Scalability and performance: If the API is going to be used in a microservice architecture, performance and scalability will be a concern especially while uploading huge files. One way to improve this is to split the upload service into smaller, more focused services, and then use message queues like Kafka to perform some operations asynchronously in the background. This will help to improve the responsiveness of the API and reduce the likelihood of timeouts.



