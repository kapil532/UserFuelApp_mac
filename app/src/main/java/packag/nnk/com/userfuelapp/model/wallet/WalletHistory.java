
package packag.nnk.com.userfuelapp.model.wallet;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class WalletHistory {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("history")
    @Expose
    private List<History> history = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

}
