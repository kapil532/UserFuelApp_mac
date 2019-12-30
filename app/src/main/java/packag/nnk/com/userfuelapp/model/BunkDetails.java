package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BunkDetails {

    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("bunk_address")
    @Expose
    private String bunkAddress;
    @SerializedName("bunk_id")
    @Expose
    private String bunkId;
    @SerializedName("bunk_identification_number")
    @Expose
    private String bunkIdentificationNumber;
    @SerializedName("bunk_name")
    @Expose
    private String bunkName;
    @SerializedName("bunk_type")
    @Expose
    private String bunkType;
    @SerializedName("compound_code")
    @Expose
    private String compoundCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("icon_id")
    @Expose
    private String iconId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("version")
    @Expose
    private Integer version;

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getBunkAddress() {
        return bunkAddress;
    }

    public void setBunkAddress(String bunkAddress) {
        this.bunkAddress = bunkAddress;
    }

    public String getBunkId() {
        return bunkId;
    }

    public void setBunkId(String bunkId) {
        this.bunkId = bunkId;
    }

    public String getBunkIdentificationNumber() {
        return bunkIdentificationNumber;
    }

    public void setBunkIdentificationNumber(String bunkIdentificationNumber) {
        this.bunkIdentificationNumber = bunkIdentificationNumber;
    }

    public String getBunkName() {
        return bunkName;
    }

    public void setBunkName(String bunkName) {
        this.bunkName = bunkName;
    }

    public String getBunkType() {
        return bunkType;
    }

    public void setBunkType(String bunkType) {
        this.bunkType = bunkType;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}