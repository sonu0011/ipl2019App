package sonu.finds.ipl2019.Model;

public class BattingLeadersModel {
    int posotion;
    String image_url,name,facility;

    public BattingLeadersModel(int posotion, String image_url, String name, String facility) {
        this.posotion = posotion;
        this.image_url = image_url;
        this.name = name;
        this.facility = facility;
    }

    public int getPosotion() {
        return posotion;
    }

    public void setPosotion(int posotion) {
        this.posotion = posotion;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    @Override
    public String toString() {
        return "BattingLeadersModel{" +
                "posotion=" + posotion +
                ", image_url='" + image_url + '\'' +
                ", name='" + name + '\'' +
                ", facility='" + facility + '\'' +
                '}';
    }
}
