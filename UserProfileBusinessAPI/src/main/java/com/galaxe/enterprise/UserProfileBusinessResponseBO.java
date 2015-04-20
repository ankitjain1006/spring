package com.galaxe.enterprise;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="UserProfileResponse")
public class UserProfileBusinessResponseBO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name="FirstName")
	private String firstName;
	
	@XmlElement(name="LastName")
	private String lastName;
	
	@XmlElement(name="Age")
	private int age;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
