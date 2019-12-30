
package packag.nnk.com.userfuelapp.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bunk implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("petrolBunkName")
    @Expose
    private String petrolBunkName;
    @SerializedName("petrolBunkAddress")
    @Expose
    private String petrolBunkAddress;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("compoundCode")
    @Expose
    private String compoundCode;
    @SerializedName("bunkType")
    @Expose
    private String bunkType;
    @SerializedName("iconId")
    @Expose
    private String iconId;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("bunkIdentificationNumber")
    @Expose
    private Object bunkIdentificationNumber;
    @SerializedName("version")
    @Expose
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetrolBunkName() {
        return petrolBunkName;
    }

    public void setPetrolBunkName(String petrolBunkName) {
        this.petrolBunkName = petrolBunkName;
    }

    public String getPetrolBunkAddress() {
        return petrolBunkAddress;
    }

    public void setPetrolBunkAddress(String petrolBunkAddress) {
        this.petrolBunkAddress = petrolBunkAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getBunkType() {
        return bunkType;
    }

    public void setBunkType(String bunkType) {
        this.bunkType = bunkType;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getBunkIdentificationNumber() {
        return bunkIdentificationNumber;
    }

    public void setBunkIdentificationNumber(Object bunkIdentificationNumber) {
        this.bunkIdentificationNumber = bunkIdentificationNumber;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
