package dmi.sws.dlview.DTO;

public class InstrumentInfoDTO {
    private String IDN;
    private String resourceName;

    public InstrumentInfoDTO(String IDN, String resourceName) {
        this.IDN = IDN;
        this.resourceName = resourceName;
    }

    public String getIDN() {
        return IDN;
    }

    public void setIDN(String IDN) {
        this.IDN = IDN;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "InstrumentInfoDTO{" +
                "IDN='" + IDN + '\'' +
                ", resourceName='" + resourceName + '\'' +
                '}';
    }
}
