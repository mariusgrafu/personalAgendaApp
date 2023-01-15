# Personal Agenda App
University Project

10 Business Requirements:

The users of the Personal Agenda App will be able to:

1) login and register
2) edit their personal information (including username, email and password)
3) add and edit events - set a date, time and location
4) add tasks - set a start date, deadline and description
5) create categories for their tasks and events
6) add notes for the tasks they have
7) invite other users to the events they set
8) add collaborators for tasks - collaborators should be able to view the tasks as their own, including the available notes
9) retrieve tasks/events by category
10) retrieve tasks/events by date

5 Main features:

1) User should be able to login and register - the login process should generate a Token which the users should then supply in the Headers of their requests in order to authenticate. The email address should be unique - there should not be 2 or more accounts with the same email address. 
2) Users should be able to add and edit events - the identity of the user making the request is validated with a supplied Token. Users should be able to define a date and time for the event, as well as a location and category.
3) Users should be able to add and edit tasks - the identity of the user making the request is validated with a supplied Token. Users should be able to define a date and time for the task, as well as a deadline, a description and a category.
4) Users should be able to create new categories which later they could use to categorise their events and tasks - Categories are global (available for all users). Categories have a name, which should be unique.
5) Users should be able to retrieve their tasks and events, either by supplying a date or a category.

---

Can be tested either through the Postman Collection attached (`Personal Agenda APP.postman_collection.json`), or through SwaggerUI API Documentation available at `http://localhost:8080/swagger-ui.html`
