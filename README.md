# Skydiving App to book jumps
> **Note:**
>This project is not finished. I am still working on some crucial functionalities as well 
> as improving the user interface.

## About The App
This application is designed for large skydiving drop-zones to help them organize their flights. 
With a user-friendly interface it is easy to plan new flights and decide who can participate in them.
For skydivers, it is now easier to book their jumps. No need to physically go to the "Booking room" and fight for a place on the plane.
They can make the reservation online before they arrive to the drop-zone.
## My Motivation
In 2022, I decided to try a new hobby which was skydiving. At first everything looked good, and I was very happy.
But there was just one thing. When you book the flight you don't know when it's going to be. You'll find out about one hour
or 15 minutes before the flight. This was very inconvenienced because you are not sure if you can eat or do anything else, so you just wait 
until they call your name. In 2023, I started thinking about a project just to do for fun, and then it came to me.
Why not make this whole process of booking a skydiving jump much easier both for the crew and skydivers?
## Users
* Admin
* Manifest
* Tandem_Pilot
* User
### Hierarchy
Admin > Manifest > Instructor > User
## Functionality
* Admin
  1. Approve Manifest users, Instructor User
  2. Delete Manifest user, Instructor User, Users
* Manifest
  1. Create, Read, Delete, Update  a departure
  2. Add and Delete user to/from a departure
  3. Create, Read, Delete, Update Plane
  4. Schedule a tandem jump
  5. Create, Read, Update, Delete Customer
* Instructor
  1. Approve users to activate them
  2. Assign pupil
  3. Plan AFF jump
* User
  1. Reserve jump(if student or higher)
  2. Cancel jump(hour before flight)
  3. Read all departures
## Database

![Alt text](Skydiving-2023-10-21_10-39.png?raw=true "Title")
