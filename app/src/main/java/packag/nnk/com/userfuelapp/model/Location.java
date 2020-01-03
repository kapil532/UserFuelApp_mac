package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {



        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;


        @SerializedName("city")
        @Expose
        private String city;





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

        public String getCity() {
        return city;
    }

        public void setCity(String city) {
        this.city = city;
    }

    }