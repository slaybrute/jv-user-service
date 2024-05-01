# User Service API

## Description
This User Service API is designed for managing user data. It enables CRUD operations for user records in a database along with specific searches based on user birth dates.

## Features
- **Create User**: Registers a new user with necessary validations on the fields such as email and birth date (user must be 18+ years old).
- **Update User**: Allows updating any single or all fields of a user record based on the user's email.
- **Delete User**: Supports deleting a user by their unique ID or email.
- **Search for Users**: Enables searching for users within a specific birth date range, with validations on the date inputs.

## API Endpoints

### User Management

#### Create a User
- **Endpoint**: \`POST /users\`
- **Description**: Registers a new user in the database, ensuring all required fields are valid and the user meets age requirements (18+ years old).
- **Payload**:
  \`\`\`json
  {
  "email": "example@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "birthDate": "05-12-2003,
  "address": "123 Main St",
  "phoneNumber": "+380682584565"
  }
  \`\`\`

#### Update a User by Email
- **Endpoint**: \`PUT /users/{email}\`
- **Description**: Updates any user fields for a user identified by their email.
- **Parameters**:
  - \`email\`: User's email as URL path variable.
- **Payload**:
  \`\`\`json
  {
  "firstName": "Jane",
  "lastName": "Doe",
  "address": "321 New St",
  "phoneNumber": "+380632284565"
  }
  \`\`\`

#### Delete a User by ID
- **Endpoint**: \`DELETE /users/{id}\`
- **Description**: Deletes a user from the database by their unique identifier.
- **Parameters**:
  - \`id\`: Unique identifier of the user.

#### Delete a User by Email
- **Endpoint**: \`DELETE /users/by-email\`
- **Description**: Deletes a user from the database using their email address.
- **Parameters**:
  - \`email\`: Email of the user to be deleted.

#### Search for Users by Birth Date Range
- **Endpoint**: \`GET /users/by-birthdate\`
- **Description**: Searches for users within a specified birth date range, ensuring the "From" date is less than the "To" date.
- **Parameters**:
  - \`from\`: Start date of the birth date range (format: dd-MM-yyyy).
  - \`to\`: End date of the birth date range (format: dd-MM-yyyy).
- **Example Request**:
  \`\`\`
  /users/by-birthdate?from=01-01-1970&to=31-12-1990
  \`\`\`

### Usage Examples

Include usage examples here for how to interact with each endpoint using tools like \`curl\` or any HTTP client.

### Error Handling

Describe common errors that might occur while using these endpoints and how they are handled or should be managed by the client.
