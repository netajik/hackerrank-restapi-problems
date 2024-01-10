### Rest API Implementation
Use the HTTP GET method to retrieve information from a database of patient medical records.
Query https://jsonmock.hackerrank.com/api/medical_records to find all the records.

The query result is paginated and can be further accessed by appending to the query string ?page=num where num is the page number, beginning with 1.

The query response from the API is a JSON with the following five fields
- page the current page
- per page the maximum number of results per page
- total the total number of records in the search result 
- total pages: the total number of pages to query in order to get all the results
- data an array of JSON objects containing medical records


The data field in the response contains a list of medical records with the following schema:

- id the unique ID of the record
- timestamp the timestamp when the record was generated on UTC as milliseconds)
- userid the id of the user for whom the transaction has been recorded
- userName: the name of the patient for whom the transaction has been recorded
- userDob the date of birth of user in format DD-MM-YYYY
-  vital object: the vitals of the user 
      - vitals.bloodPressureDiastole the diastolic pressure reading of the user, mmHg 
      - vitals.blood PressureSystole the systolic pressure reading of the user, mmHg 
      - vitals pulse the pulse rate of the user, beats per minute 
      - vitals.breathingRate the breathing rate of the user, breaths per minute 
      - vitals.body Temperature the body temperature of the user, degrees Fahrenheit 
   
- diagnosis object, the diagnosis for the user 
  - diagnosis ict the id of the condition diagnosed 
  - diagnosis name the name of the condition diagnosed 
  - diagnosis severity: the severity of the condition diagnosed
- doctor: object, the doctor who diagnosed the condition
  - doctor.idt the id of the doctor who diagnosed the condition
  - doctor.name the name of the doctor who diagnosed the condition
- meta object, the meta information of the user
  - meta.height The current height of the user. centimeters
  - meta weight. The current weight of the user, pounds

Return the number of records that have bloodPressureDiastole in the inclusive range of lowerlimit to upperlime.

- Function Description
- Complete the function healthCheckup in the editor below.
- healthCheckup has the following parameters:
  - int lowerlimit: lower limit of the range of blood PressureDiastole
  - int upperlimit: upper limit of the range of blood PressureDiastole

- Returns 
  - int: the number of records that qualify as fit

Note: Please review the hea in the code stub to see available libraries for API requests in the selected language. Required libraries can be imported in order to solve the question. Check our full list of
        supported libraries at https://www.hackerrank.com/environment