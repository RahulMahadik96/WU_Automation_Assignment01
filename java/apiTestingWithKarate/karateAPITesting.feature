Feature: Restful-Booker Testing

  Background: 
    * url 'https://restful-booker.herokuapp.com'
    * def token = 'someToken'
    
Scenario: Create Auto token
    Given path '/auth' 
    And request
    """
   {
    "username" : "admin",
    "password" : "password123"
    }
    """
    When method POST
    Then status 200
    And def token = response.token

  Scenario: Get booking details
    Given path '/booking/1'
    When method 	GET
    Then status 200
    And print response
    
  Scenario: Create booking
    Given path '/booking'
    And header Authorization = 'Bearer ' + token
    And request
    """
   {
    "firstname" : "Jim",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
     },
    "additionalneeds" : "Breakfast"
    }
    """
    When method POST
    Then status 200
    And print response
    

  Scenario: Update booking
    Given path '/booking/2960'
    And header Authorization = 'Bearer ' + token
    And request {"name":"Morpheus", "lastname" : "Brown"}
    When method PUT
    Then status 200
    And print response

  Scenario: Update Booking using Patch
    Given path '/booking/2960'
    And header Authorization = 'Bearer ' + token
    And request {"name":"Ram"}
    When method PATCH
    Then status 200
    And print response
     
  Scenario: Delete Booking using Delete API
    Given path '/booking/2960'
    And header Authorization = 'Bearer ' + token
    When method DELETE
    Then status 200
    And print response
