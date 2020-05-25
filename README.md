# Helidon Incidents MP Example

This example implements REST services using MicroProfile for a CRUD application for Incidents.

## Build and run

With JDK8+
```bash
mvn package
java -jar target/helidon-mp-incidents.jar
```

## Exercise the CRUD application

```
 Get default all incidents:
 curl -X GET http://localhost:8080/incidents
 The message is returned as a list of JSON objects.
  
	[
	    {
	        "approved": true,
	        "claimDate": "05-02-2020",
	        "comments": "In-house repair looking at this",
	        "customer": "Smithers Corp",
	        "customerId": "46997",
	        "id": "11131",
	        "product": "Idea tablet",
	        "status": "In-progress",
	        "subject": "Display blank",
	        "summary": "When powering up unit, the display is blank"
	    }, ...
    ]
    
 Get incident for id=11129:
 curl -X GET http://localhost:8080/incidents/11129
 
 The message is returned as a JSON object...
  
	{
	    "approved": true,
	    "claimDate": "05-25-2021",
	    "comments": "Manufacturer updated",
	    "customer": "Tech Experts Up",
	    "customerId": "46995",
	    "id": "11129",
	    "product": "Toshiba Laptop Computer",
	    "status": "In-progress",
	    "subject": "Keys are sticking",
	    "summary": "Customer reports that keys are sticking after spilling a diet soda on it"
	}
	
	
 Update incident with id=11129
 curl -X PUT -H "Content-Type: application/json" -d '{ "id": "11129" ,"approved": true, "claimDate": "05-25-2021", "comments": "Manufacturer updated", "customer": "Tech Experts Up", "customerId": "46995", "product": "Toshiba Laptop Computer", "status": "In-progress", "subject": "Keys are sticking", "summary": "Customer reports that keys are sticking after spilling a diet soda on it"}' http://localhost:8080/incidents

 The expected response is a 201 code.


 Add new incident
 curl -X POST -H "Content-Type: application/json" -d '{"approved": true, "claimDate": "05-25-2021", "comments": "Assign manufacturer for this incident", "customer": "iShop Q", "customerId": "47886", "product": "Docking Station Lenovo Yoga", "status": "New", "subject": "USB ports not recognizing devices", "summary": "Customer reports that none of the USB ports are working after connecting devices"}' http://localhost:8080/incidents
 
 The expected response is a 201 code.
 
 ```

## Try searching by parameter

Try with different values for the search criteria...

```
 Get incidents by status
 curl -X GET http://localhost:8080/incidents/status/In-Progress
 
 Get incidents by product
 curl -X GET http://localhost:8080/incidents/product/Toshiba
 
 Get incidents by customer
 curl -X GET http://localhost:8080/incidents/customer/Tech
  
 Get incidents by claim date
 curl -X GET http://localhost:8080/incidents/claim/04-28-2020

```

## Build the Docker Image

```
docker build -t helidon-mp-incidents .
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 helidon-mp-incidents:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                         # Verify which cluster
kubectl get pods                             # Verify connectivity to cluster
kubectl create -f app.yaml               # Deploy application
kubectl get service helidon-mp-incidents  # Verify deployed service
```
