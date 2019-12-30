
package packag.nnk.com.userfuelapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BunkDetailWithLatLang {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bunks")
    @Expose
    private List<Bunk> bunks = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Bunk> getBunks() {
        return bunks;
    }

    public void setBunks(List<Bunk> bunks) {
        this.bunks = bunks;
    }

}
