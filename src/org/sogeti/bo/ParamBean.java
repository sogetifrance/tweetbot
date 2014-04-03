package org.sogeti.bo;


import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class ParamBean {

	// Ojet de parametrage
	@Id
	private Long id;
	private String screenname;
	private String consumerKey;
	private String consumerSecret;
	private String accesToken;
	private String tokenSecret;
	private String criterian1;
	private String criterian1conditions;
	private String criterian2;
	private String criterian2conditions;
	private String criterian3;
	private String criterian3conditions;
	private String scoreOk;

	public ParamBean() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccesToken() {
		return accesToken;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getCriterian1() {
		return criterian1;
	}

	public void setCriterian1(String criterian1) {
		this.criterian1 = criterian1;
	}

	public String getCriterian1conditions() {
		return criterian1conditions;
	}

	public void setCriterian1conditions(String criterian1conditions) {
		this.criterian1conditions = criterian1conditions;
	}

	public String getCriterian2() {
		return criterian2;
	}

	public void setCriterian2(String criterian2) {
		this.criterian2 = criterian2;
	}

	public String getCriterian2conditions() {
		return criterian2conditions;
	}

	public void setCriterian2conditions(String criterian2conditions) {
		this.criterian2conditions = criterian2conditions;
	}

	public String getCriterian3() {
		return criterian3;
	}

	public void setCriterian3(String criterian3) {
		this.criterian3 = criterian3;
	}

	public String getCriterian3conditions() {
		return criterian3conditions;
	}

	public void setCriterian3conditions(String criterian3conditions) {
		this.criterian3conditions = criterian3conditions;
	}

	public String getScoreOk() {
		return scoreOk;
	}

	public void setScoreOk(String scoreOk) {
		this.scoreOk = scoreOk;
	}
}
