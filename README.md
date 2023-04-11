# Exercise - Controller protection
* Create a spring boot app with a login system (you can copy from lessons)
* Create entity User
* Create entity Role
* Link each user with one or more roles
* Enable roles in Spring boot
* Create entity Salary related one-to-one to user
* Create a CRUD controller User
* Create a controller Salary with the following endpoint
    * Get all salaries
    * Get user specific salary
    * Create user salary
* Protect the controller, only users with role admin can use the controller

### From related lessons - Orders
* Create entity superclass BaseEntity with auditing data
* Create entity Order related many-to-one to User
* Create a CRUD controller Order
* Protect the controller, only users with role admin and registered can use the controller