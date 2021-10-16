# sargent-disc

Tech assignment

# Configuration
The application contains an application.properties file with the following parameter: 
> file.location=(path to search directory)

This should be set to the search directory. The directory must allow the application to access, if it cannot,
Please check the java security permissions on the location and allow access.
  
# Start up
  
The project is a maven spring boot project and their is a pom file in the root directory.
Download and open in your favourite IDE and execute 
   > mvn clean install 
  
Alternitavely and executable jar will be created in the /target directory of the download location.
It can be run on the command line as follows:
  > java -jar target/sergent-disc-0.0.1-SNAPSHOT.jar

I have commited some dummy files and folders so the tests will run. feel free to add to this but do not remove any.
  
# Testing
You can use Postman or any API test tool to test the following API's
  
> /sergent-disc/v0.1/file/content  

-  returns the file content given a file path as a request parameter
> /sergent-disc/v0.1/file/content  
> fileName='src/main/resources/test-files/jmeter.log'

- returns a list of files matching the searchCriteria given as a parameter
  
searchCriteria is a comma separated list as in: 
> searchCriteria='Furniture, Washroom'
