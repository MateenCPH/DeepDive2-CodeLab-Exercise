# Hints
1. Spend time analyzing the problems
You are free to choose the data model you find most suitable for the task. You don’t necessarily need need to have relationships between all entities. You can also choose to store the data in a different way than described in the assignment. The most important thing is that the functionality is there. And then we are looking forward to seeing your solution and arguments. Make a lot of drawings and sketches to come to your solution. Pen and paper, however antique is sounds, is a good tool for this.

2. API Key for TMDb
You will need an API key from TMDb to fetch movie data. In case you don’t have one, you can get one by signing up here.
Rememeber never to push your api key to Github. Instead, you can use environment variables in IntelliJ to store the api key. You can access the environment variable in Java like this:

  String apiKey = System.getenv("API_KEY");

3. Clarify Entity Relationships
Think about the relationship between movies and actors/directors (one movie can have many actors, but can one actor be in multiple movies?). You might want to use a many-to-many relationship here - or would you rather stay with a one-to-many to avoid complexity - and then live with some duplicates.

4. API Json Data Structure
Make sure you review the structure of the TMDb API responses for movies, actors, directors, and genres. You’ll need this information to design your DTOs and entity mappings.

5. Database Constraints
Consider adding relevant constraints like unique, not null, or length to your entities to ensure data integrity.

6. Error Handling
Make sure to handle errors gracefully, such as what happens if a movie can’t be found in the database. You can throw custom exceptions or return informative error messages.

7. DTO vs Entity Mapping Clarity
Ensure that DTOs are only used for communication with external systems (like the TMDb API or the main method / tests), while entities should only be used for communication with the database.

8. Version Control and Collaboration
As you collaborate in groups, it might help to follow a Git branching strategy (e.g., developer branch and feature branches) to avoid conflicts. Make sure everyone commits regularly and uses descriptive commit messages.
Also remember to use pull requests for code reviews as needed. It will be easier to delegate work the further you get into the project. In the beginning, it might be a good idea to work together on the same parts of the project to get a common understanding of the project.
