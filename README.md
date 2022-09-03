# Social media FindMe application

Some time ago I decided to write a social network using spring to learn it better,
and i have to say that it was a bit difficult at the beginning: it took me a while
to understand this because I felt the need to understand different parts
of it, how they interact and how to choose between options.

Hopefully it can be useful for someone else.

## The starting step

We start from a pretty basic spring boot application with spring security, with PostgreSQL database.
It has a login screen(screenshots will be at the very bottom of the documentation), some users and roles, a home page(register page) 
and a other pages like profile, users feed, friends and so on.

I did not write tests for this application, 
as I plan to study them a little later and write a separate project for this.

### Libraries i have used

As you can see in my pom.xml i mostly used spring starters, also i added dependency for Postgres:

- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-thymeleaf
- postgresql
- lombok

### How it works

To start using the application, the user needs to fill out the form (the photo will be at the bottom of the documentation)
After the user has filled out the form and clicked on the confirmation button, 
the request will go to the user controller to the method that you will see below:
```java
    @PostMapping(path = "/user-registration")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/user/" + user.getId();
    }
```
After that controller will pass out data to the service 
where we encrypt the password and give the user his role and save him to database.
```java
    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleDAO.findRoleByRoleName(RoleName.USER)));
        userDAO.save(user);
        return user;
    }
```
When all registration steps have been completed, the user will have to login to our application, after that he will be redirected to his profile.

### User profile
On his page, the user can change his photo (I store photos in a separate folder on my computer), 
watch and write posts, see his friends, delete them, there are also separate tabs with incoming and outcoming requests.

As you can see on friend profile picture there is messaging button(in the course of studying the issue, 
I realized that it is best to use webhooks for messaging, but I have not figured out with them yet, 
so I added this to my to-do list and plan to figure it out and finish this feature)


## Screenshots of my project(I not so good at html/css)

### Registration/Home page
![1](https://user-images.githubusercontent.com/44270738/180313611-e5e0cf9d-4bcb-471a-b8f9-2d00b4d96414.png)

### Profile page
![profile](https://user-images.githubusercontent.com/44270738/180313942-ae950bfe-cf7f-421d-87f1-0897d7235311.png)

### Login page
![2](https://user-images.githubusercontent.com/44270738/180313990-2ed812b0-6fbe-4b01-814a-101fae75b74d.png)

### User feed page
![user_feed](https://user-images.githubusercontent.com/44270738/180314055-2bf7f5b2-1736-4a6d-8100-fa19db566253.png)

### User friends page
![user_friends](https://user-images.githubusercontent.com/44270738/180314140-87444cb9-4a19-4be3-a78c-192614f315df.png)

### User income requests page
![income](https://user-images.githubusercontent.com/44270738/180314261-9667cd5a-9dc4-4bd3-b558-b29a062e055b.png)

### User outcome requests page
![outcome](https://user-images.githubusercontent.com/44270738/180314324-0b9caf6f-5a55-41e1-a7e3-da4499c73d50.png)

### Friend profile page
![friend_profile](https://user-images.githubusercontent.com/44270738/180314385-46c06c5e-2485-467c-ac1e-185956e56c66.png)
