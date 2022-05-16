### Instructions to create a new user
   #### Send Postman request through gateway to 
    http://localhost:8082/users-ws/users
    using JSON body example :

    { "firstName":"Ioana",
      "lastName":"Tiriac",
      "password":"12345678",
      "email":"test@test.com"
    }

### To login created user in previous step: 
   #### Send Postman request through gateway to
    http://localhost:8082/users-ws/users/login
    using raw TEXT body example :

    {
      "email":"test@test.com",
      "password":"12345678",
    }

    
