package com.fuda.dc.lhtz.web.vo;


public class IcbIndexInfo {
    private String regNum;
    private String companyName;
    private String legalName;
    private String address;
    
    //private String establishTime;
    private String businessScope;

    public IcbIndexInfo() {

    }

    public IcbIndexInfo(String regNum, String companyName, String legalName, String address, String establishTime,
            String businessScope) {
        this.regNum = regNum;
        this.companyName = companyName;
        this.legalName = legalName;
        this.address = address;
        this.businessScope = businessScope;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

}