package io.helidon.mp.incidents.model;

public class Incident {
	private String id;
	private String product;
	private String subject;
	private String summary;
	private String customer;
	private String customerId;
	private String claimDate;
	private Boolean approved;	
	private String status;
	private String comments;
	
	public String getId() {
		return id;
	}
	public String getProduct() {
		return product;
	}
	public String getSubject() {
		return subject;
	}
	public String getSummary() {
		return summary;
	}
	public String getCustomer() {
		return customer;
	}
	public String getCustomerId() {
		return customerId;
	}
	public String getClaimDate() {
		return claimDate;
	}
	public Boolean getApproved() {
		return approved;
	}
	public String getStatus() {
		return status;
	}
	public String getComments() {
		return comments;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	
}
