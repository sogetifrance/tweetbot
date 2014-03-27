package org.sogeti.service.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestServiceResponse {
	public String nomService;
    public Boolean serviceRuning;

    public RestServiceResponse(String nomService, Boolean serviceRuning) {
        this.nomService = nomService;
        this.serviceRuning = serviceRuning;
    }
    

	public String getNomService() {
		return nomService;
	}

	public void setNomService(String nomService) {
		this.nomService = nomService;
	}

	public Boolean getServiceRuning() {
		return serviceRuning;
	}

	public void setServiceRuning(Boolean serviceRuning) {
		this.serviceRuning = serviceRuning;
	}
	
}
