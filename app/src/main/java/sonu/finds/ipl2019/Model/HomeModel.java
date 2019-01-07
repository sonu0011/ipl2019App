package sonu.finds.ipl2019.Model;

public class HomeModel {
    private String image_url,heading,teamimage_url;
    int team_id;
    String drawer_heading,drawer_bg,drawer_icon;

    public HomeModel(String drawer_heading, String drawer_bg, String drawer_icon) {
        this.drawer_heading = drawer_heading;
        this.drawer_bg = drawer_bg;
        this.drawer_icon = drawer_icon;
    }

    public String getDrawer_heading() {
        return drawer_heading;
    }

    public void setDrawer_heading(String drawer_heading) {
        this.drawer_heading = drawer_heading;
    }

    public String getDrawer_bg() {
        return drawer_bg;
    }

    public void setDrawer_bg(String drawer_bg) {
        this.drawer_bg = drawer_bg;
    }

    public String getDrawer_icon() {
        return drawer_icon;
    }

    public void setDrawer_icon(String drawer_icon) {
        this.drawer_icon = drawer_icon;
    }

    public HomeModel(String image_url, String heading) {
        this.image_url = image_url;
        this.heading = heading;
    }

    public HomeModel(String teamimageurl,int team_id) {
        this.teamimage_url  =teamimageurl;
        this.team_id =team_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeamimage_url() {
        return teamimage_url;
    }

    public void setTeamimage_url(String teamimage_url) {
        this.teamimage_url = teamimage_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
