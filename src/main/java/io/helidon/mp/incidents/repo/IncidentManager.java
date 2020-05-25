package io.helidon.mp.incidents.repo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import io.helidon.mp.incidents.model.Incident;


@ApplicationScoped
public class IncidentManager {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private AtomicInteger incidentIdGenerator = new AtomicInteger(0);
    
    // in-memory data for now
 	private final CopyOnWriteArrayList<Incident> incidentList = new CopyOnWriteArrayList<Incident>();
 	   

    public IncidentManager() {
		JsonbConfig config = new JsonbConfig().withFormatting(Boolean.TRUE);

		Jsonb jsonb = JsonbBuilder.create(config);

		// pull sample data from a JSON file, located in resources directory
		incidentList.addAll(jsonb.fromJson(IncidentManager.class.getResourceAsStream("/incidents.json"),
				new CopyOnWriteArrayList<Incident>() {
				}.getClass().getGenericSuperclass()));		
    }

    private String getNextId() {
        String date = LocalDate.now().format(formatter);
        return String.format("%04d-%s", incidentIdGenerator.incrementAndGet(), date);
    }

    public List<Incident> getAll() {
        return incidentList;
    }
    
    public Incident get(String id) {
		Incident match;
		match = incidentList.stream().filter(i -> i.getId().equals(id)).findFirst().get();
		
		return match;
    }
    
    public String add(Incident incident) {
        String id = getNextId();
        incident.setId(id);
        incidentList.add(incident);       
        return id;
    }
	
    public void deleteById(String id) {
		int matchIndex;
		matchIndex = incidentList.stream().filter(i -> i.getId().equals(id)).findFirst().map(i -> incidentList.indexOf(i)).get();
		
		incidentList.remove(matchIndex);		

	}

	public String update(Incident updatedIncident) {
		deleteById(updatedIncident.getId());
		
		incidentList.add(updatedIncident);
		
		return updatedIncident.getId();
	}
	
	public boolean isIdFound(String id) {
		Incident match;
		try {
			match = incidentList.stream().filter(i -> i.getId().equals(id)).findFirst().get();
		}catch(NoSuchElementException e) {
			match = null;
		}
		

		return match != null ? true : false;
	}
    
	public List<Incident> getByStatus(String status) {
		List<Incident> matchList = incidentList.stream().filter((i) -> (i.getStatus().contains(status)))
				.collect(Collectors.toList());

		return matchList;
	}    
	
	public List<Incident> getByProduct(String product) {
		List<Incident> matchList = incidentList.stream().filter((i) -> (i.getProduct().contains(product)))
				.collect(Collectors.toList());

		return matchList;
	}

	public List<Incident> getByCustomer(String customer) {
		List<Incident> matchList = incidentList.stream().filter((i) -> (i.getCustomer().contains(customer)))
				.collect(Collectors.toList());

		return matchList;
	}

	public List<Incident> getByClaimDate(String claimDate) {
		List<Incident> matchList = incidentList.stream().filter((i) -> (i.getClaimDate().contains(claimDate)))
				.collect(Collectors.toList());

		return matchList;
	}
}
