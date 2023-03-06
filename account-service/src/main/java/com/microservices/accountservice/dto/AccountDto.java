package com.microservices.accountservice.dto;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long Id;

    private String sfid;

    private boolean IsDeleted;
    private String MasterRecordId;
    private String Name;
    private String LastName;
    private String FirstName;
    private String Salutation;
    private String Type;
    private String RecordTypeId;
    private String ParentId;
    private String BillingStreet;
    private String BillingCity;
    private String BillingState;
    private String BillingPostalCode;
    private String BillingCountry;
    private String BillingLatitude;
    private String BillingLongitude;
    private String BillingGeocodeAccuracy;
    private String BillingAddress;
    private String ShippingStreet;
    private String ShippingCity;
    private String ShippingState;
    private String ShippingPostalCode;
    private String ShippingCountry;
    private String ShippingLatitude;
    private String ShippingLongitude;
    private String ShippingGeocodeAccuracy;
    private String ShippingAddress;
    private String Phone;
    private String Website;
    private String PhotoUrl;
    private String Industry;
    private String NumberOfEmployees;
    private String Description;
    private String OwnerId;
    private String CreatedDate;
    private String CreatedById;
    private String LastModifiedDate;
    private String LastModifiedById;
    private String SystemModstamp;
    private String LastActivityDate;
    private String LastViewedDate;
    private String LastReferencedDate;
    private String PersonContactId;
    private String IsPersonAccount;
    private String PersonMailingStreet;
    private String PersonMailingCity;
    private String PersonMailingState;
    private String PersonMailingPostalCode;
    private String PersonMailingCountry;
    private String PersonMailingLatitude;
    private String PersonMailingLongitude;
    private String PersonMailingGeocodeAccuracy;
    private String PersonMailingAddress;
    private String PersonOtherStreet;
    private String PersonOtherCity;
    private String PersonOtherState;
    private String PersonOtherPostalCode;
    private String PersonOtherCountry;
    private String PersonOtherLatitude;
    private String PersonOtherLongitude;
    private String PersonOtherGeocodeAccuracy;
    private String PersonOtherAddress;
    private String PersonMobilePhone;
    private String PersonHomePhone;
    private String PersonOtherPhone;
    private String PersonAssistantPhone;
    private String PersonEmail;
    private String PersonTitle;
    private String PersonDepartment;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getMasterRecordId() {
        return MasterRecordId;
    }

    public void setMasterRecordId(String masterRecordId) {
        MasterRecordId = masterRecordId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getRecordTypeId() {
        return RecordTypeId;
    }

    public void setRecordTypeId(String recordTypeId) {
        RecordTypeId = recordTypeId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getBillingStreet() {
        return BillingStreet;
    }

    public void setBillingStreet(String billingStreet) {
        BillingStreet = billingStreet;
    }

    public String getBillingCity() {
        return BillingCity;
    }

    public void setBillingCity(String billingCity) {
        BillingCity = billingCity;
    }

    public String getBillingState() {
        return BillingState;
    }

    public void setBillingState(String billingState) {
        BillingState = billingState;
    }

    public String getBillingPostalCode() {
        return BillingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        BillingPostalCode = billingPostalCode;
    }

    public String getBillingCountry() {
        return BillingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        BillingCountry = billingCountry;
    }

    public String getBillingLatitude() {
        return BillingLatitude;
    }

    public void setBillingLatitude(String billingLatitude) {
        BillingLatitude = billingLatitude;
    }

    public String getBillingLongitude() {
        return BillingLongitude;
    }

    public void setBillingLongitude(String billingLongitude) {
        BillingLongitude = billingLongitude;
    }

    public String getBillingGeocodeAccuracy() {
        return BillingGeocodeAccuracy;
    }

    public void setBillingGeocodeAccuracy(String billingGeocodeAccuracy) {
        BillingGeocodeAccuracy = billingGeocodeAccuracy;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getShippingStreet() {
        return ShippingStreet;
    }

    public void setShippingStreet(String shippingStreet) {
        ShippingStreet = shippingStreet;
    }

    public String getShippingCity() {
        return ShippingCity;
    }

    public void setShippingCity(String shippingCity) {
        ShippingCity = shippingCity;
    }

    public String getShippingState() {
        return ShippingState;
    }

    public void setShippingState(String shippingState) {
        ShippingState = shippingState;
    }

    public String getShippingPostalCode() {
        return ShippingPostalCode;
    }

    public void setShippingPostalCode(String shippingPostalCode) {
        ShippingPostalCode = shippingPostalCode;
    }

    public String getShippingCountry() {
        return ShippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        ShippingCountry = shippingCountry;
    }

    public String getShippingLatitude() {
        return ShippingLatitude;
    }

    public void setShippingLatitude(String shippingLatitude) {
        ShippingLatitude = shippingLatitude;
    }

    public String getShippingLongitude() {
        return ShippingLongitude;
    }

    public void setShippingLongitude(String shippingLongitude) {
        ShippingLongitude = shippingLongitude;
    }

    public String getShippingGeocodeAccuracy() {
        return ShippingGeocodeAccuracy;
    }

    public void setShippingGeocodeAccuracy(String shippingGeocodeAccuracy) {
        ShippingGeocodeAccuracy = shippingGeocodeAccuracy;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public String getNumberOfEmployees() {
        return NumberOfEmployees;
    }

    public void setNumberOfEmployees(String numberOfEmployees) {
        NumberOfEmployees = numberOfEmployees;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedById() {
        return CreatedById;
    }

    public void setCreatedById(String createdById) {
        CreatedById = createdById;
    }

    public String getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedById() {
        return LastModifiedById;
    }

    public void setLastModifiedById(String lastModifiedById) {
        LastModifiedById = lastModifiedById;
    }

    public String getSystemModstamp() {
        return SystemModstamp;
    }

    public void setSystemModstamp(String systemModstamp) {
        SystemModstamp = systemModstamp;
    }

    public String getLastActivityDate() {
        return LastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        LastActivityDate = lastActivityDate;
    }

    public String getLastViewedDate() {
        return LastViewedDate;
    }

    public void setLastViewedDate(String lastViewedDate) {
        LastViewedDate = lastViewedDate;
    }

    public String getLastReferencedDate() {
        return LastReferencedDate;
    }

    public void setLastReferencedDate(String lastReferencedDate) {
        LastReferencedDate = lastReferencedDate;
    }

    public String getPersonContactId() {
        return PersonContactId;
    }

    public void setPersonContactId(String personContactId) {
        PersonContactId = personContactId;
    }

    public String getIsPersonAccount() {
        return IsPersonAccount;
    }

    public void setIsPersonAccount(String isPersonAccount) {
        IsPersonAccount = isPersonAccount;
    }

    public String getPersonMailingStreet() {
        return PersonMailingStreet;
    }

    public void setPersonMailingStreet(String personMailingStreet) {
        PersonMailingStreet = personMailingStreet;
    }

    public String getPersonMailingCity() {
        return PersonMailingCity;
    }

    public void setPersonMailingCity(String personMailingCity) {
        PersonMailingCity = personMailingCity;
    }

    public String getPersonMailingState() {
        return PersonMailingState;
    }

    public void setPersonMailingState(String personMailingState) {
        PersonMailingState = personMailingState;
    }

    public String getPersonMailingPostalCode() {
        return PersonMailingPostalCode;
    }

    public void setPersonMailingPostalCode(String personMailingPostalCode) {
        PersonMailingPostalCode = personMailingPostalCode;
    }

    public String getPersonMailingCountry() {
        return PersonMailingCountry;
    }

    public void setPersonMailingCountry(String personMailingCountry) {
        PersonMailingCountry = personMailingCountry;
    }

    public String getPersonMailingLatitude() {
        return PersonMailingLatitude;
    }

    public void setPersonMailingLatitude(String personMailingLatitude) {
        PersonMailingLatitude = personMailingLatitude;
    }

    public String getPersonMailingLongitude() {
        return PersonMailingLongitude;
    }

    public void setPersonMailingLongitude(String personMailingLongitude) {
        PersonMailingLongitude = personMailingLongitude;
    }

    public String getPersonMailingGeocodeAccuracy() {
        return PersonMailingGeocodeAccuracy;
    }

    public void setPersonMailingGeocodeAccuracy(String personMailingGeocodeAccuracy) {
        PersonMailingGeocodeAccuracy = personMailingGeocodeAccuracy;
    }

    public String getPersonMailingAddress() {
        return PersonMailingAddress;
    }

    public void setPersonMailingAddress(String personMailingAddress) {
        PersonMailingAddress = personMailingAddress;
    }

    public String getPersonOtherStreet() {
        return PersonOtherStreet;
    }

    public void setPersonOtherStreet(String personOtherStreet) {
        PersonOtherStreet = personOtherStreet;
    }

    public String getPersonOtherCity() {
        return PersonOtherCity;
    }

    public void setPersonOtherCity(String personOtherCity) {
        PersonOtherCity = personOtherCity;
    }

    public String getPersonOtherState() {
        return PersonOtherState;
    }

    public void setPersonOtherState(String personOtherState) {
        PersonOtherState = personOtherState;
    }

    public String getPersonOtherPostalCode() {
        return PersonOtherPostalCode;
    }

    public void setPersonOtherPostalCode(String personOtherPostalCode) {
        PersonOtherPostalCode = personOtherPostalCode;
    }

    public String getPersonOtherCountry() {
        return PersonOtherCountry;
    }

    public void setPersonOtherCountry(String personOtherCountry) {
        PersonOtherCountry = personOtherCountry;
    }

    public String getPersonOtherLatitude() {
        return PersonOtherLatitude;
    }

    public void setPersonOtherLatitude(String personOtherLatitude) {
        PersonOtherLatitude = personOtherLatitude;
    }

    public String getPersonOtherLongitude() {
        return PersonOtherLongitude;
    }

    public void setPersonOtherLongitude(String personOtherLongitude) {
        PersonOtherLongitude = personOtherLongitude;
    }

    public String getPersonOtherGeocodeAccuracy() {
        return PersonOtherGeocodeAccuracy;
    }

    public void setPersonOtherGeocodeAccuracy(String personOtherGeocodeAccuracy) {
        PersonOtherGeocodeAccuracy = personOtherGeocodeAccuracy;
    }

    public String getPersonOtherAddress() {
        return PersonOtherAddress;
    }

    public void setPersonOtherAddress(String personOtherAddress) {
        PersonOtherAddress = personOtherAddress;
    }

    public String getPersonMobilePhone() {
        return PersonMobilePhone;
    }

    public void setPersonMobilePhone(String personMobilePhone) {
        PersonMobilePhone = personMobilePhone;
    }

    public String getPersonHomePhone() {
        return PersonHomePhone;
    }

    public void setPersonHomePhone(String personHomePhone) {
        PersonHomePhone = personHomePhone;
    }

    public String getPersonOtherPhone() {
        return PersonOtherPhone;
    }

    public void setPersonOtherPhone(String personOtherPhone) {
        PersonOtherPhone = personOtherPhone;
    }

    public String getPersonAssistantPhone() {
        return PersonAssistantPhone;
    }

    public void setPersonAssistantPhone(String personAssistantPhone) {
        PersonAssistantPhone = personAssistantPhone;
    }

    public String getPersonEmail() {
        return PersonEmail;
    }

    public void setPersonEmail(String personEmail) {
        PersonEmail = personEmail;
    }

    public String getPersonTitle() {
        return PersonTitle;
    }

    public void setPersonTitle(String personTitle) {
        PersonTitle = personTitle;
    }

    public String getPersonDepartment() {
        return PersonDepartment;
    }

    public void setPersonDepartment(String personDepartment) {
        PersonDepartment = personDepartment;
    }
}
