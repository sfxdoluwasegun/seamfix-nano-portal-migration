package com.nano.portal.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Liferay WorkflowStatus properties.
 * 
 * @author segz
 *
 */

public enum WorkflowStatus {
	
	ANY(-1),
	ACTIVE(0), 
	PENDING(1), 
	DRAFT(2), 
	EXPIRED(3), 
	DENIED(4), 
	INACTIVE(5), 
	INCOMPLETE(6);
	
	private int status ;
	
	private WorkflowStatus(int status){
		this.status = status;
	}
	
	public static WorkflowStatus fromCode(Integer status){
		if (status != null)
			for (WorkflowStatus workflowStatus : WorkflowStatus.values()){
				if (workflowStatus.getStatus().equals(status))
					return workflowStatus;
			}
		
		return null;
	}
	
	public static List<Integer> literals(){
		List<Integer> literals = new ArrayList<Integer>();

		for (WorkflowStatus workflowStatus : WorkflowStatus.values()){
			literals.add(workflowStatus.getStatus());
		}
		
		Collections.sort(literals, new Comparator<Integer>() {
			public int compare(Integer a, Integer b){
				return a.compareTo(b);
			}
		});
		
		return literals;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
