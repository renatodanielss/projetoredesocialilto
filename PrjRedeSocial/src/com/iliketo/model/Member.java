package com.iliketo.model;

import com.iliketo.model.ContentILiketo;
import com.iliketo.model.annotation.ColumnILiketo;
import com.iliketo.model.annotation.FileILiketo;
import com.iliketo.model.annotation.IdILiketo;

/**
 * Classe de modelo
 * Contém os dados de um Membro
 * Dados/modelos ILiketo
 * 
 * @author osvaldimar.costa
 *
 */
public class Member extends ContentILiketo{

	@IdILiketo
	@ColumnILiketo(name = "id_member")
	private String idMember;
	
	@ColumnILiketo(name = "full_name")
	private String fullName;
	
	@ColumnILiketo(name = "nickname")
	private String nickname;
	
	@ColumnILiketo(name = "country")
	private String country;
	
	@ColumnILiketo(name = "date_of_birth")
	private String dateOfBirth;
	
	@ColumnILiketo(name = "gender")
	private String gender;
	
	@ColumnILiketo(name = "cpf_tax_id")
	private String cpfTaxId;
	
	@ColumnILiketo(name = "email")
	private String email;
	
	@ColumnILiketo(name = "confirm_email")
	private String confirmEmail;
	
	@ColumnILiketo(name = "alternate_email")
	private String alternateEmail;
	
	@ColumnILiketo(name = "email_i_like_to")
	private String emailILiketo;
	
	@ColumnILiketo(name = "visibilityProfile")
	private String visibilityProfile;
	
	@ColumnILiketo(name = "address")
	private String address;
	
	@ColumnILiketo(name = "city")
	private String city;
	
	@ColumnILiketo(name = "state_province")
	private String stateProvince;
	
	@ColumnILiketo(name = "zip_postal_code")
	private String zipPostalCode;
	
	@ColumnILiketo(name = "country_code")
	private String countryCode;
	
	@ColumnILiketo(name = "phone_number")
	private String phoneNumber;
	
	@ColumnILiketo(name = "email_notification")
	private String emailNotification;
	
	@ColumnILiketo(name = "account_type")
	private String accountType;
	
	@ColumnILiketo(name = "used_space")
	private String usedSpace;
	
	@ColumnILiketo(name = "storage_type")
	private String storageType;
	
	@FileILiketo
	@ColumnILiketo(name = "path_photo_member")
	private String pathPhoto;
	
	public Member(){
		
	}

	public String getIdMember() {
		return idMember;
	}

	public void setIdMember(String idMember) {
		this.idMember = idMember;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCpfTaxId() {
		return cpfTaxId;
	}

	public void setCpfTaxId(String cpfTaxId) {
		this.cpfTaxId = cpfTaxId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getEmailILiketo() {
		return emailILiketo;
	}

	public void setEmailILiketo(String emailILiketo) {
		this.emailILiketo = emailILiketo;
	}

	public String getVisibilityProfile() {
		return visibilityProfile;
	}

	public void setVisibilityProfile(String visibilityProfile) {
		this.visibilityProfile = visibilityProfile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getZipPostalCode() {
		return zipPostalCode;
	}

	public void setZipPostalCode(String zipPostalCode) {
		this.zipPostalCode = zipPostalCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(String usedSpace) {
		this.usedSpace = usedSpace;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}
	
	
}
