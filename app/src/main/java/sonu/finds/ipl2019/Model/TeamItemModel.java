package sonu.finds.ipl2019.Model;

public class TeamItemModel {
    String high_score,low_socre,total_fours,total_sixes,total_most_sixes_inng,team_most_four_inng,
    team_most_wickets_inng,team_total_wickets,team_item_title;

    public TeamItemModel(String high_score, String low_socre, String total_fours, String total_sixes, String total_most_sixes_inng, String team_most_four_inng, String team_most_wickets_inng, String team_total_wickets, String team_item_title) {
        this.high_score = high_score;
        this.low_socre = low_socre;
        this.total_fours = total_fours;
        this.total_sixes = total_sixes;
        this.total_most_sixes_inng = total_most_sixes_inng;
        this.team_most_four_inng = team_most_four_inng;
        this.team_most_wickets_inng = team_most_wickets_inng;
        this.team_total_wickets = team_total_wickets;
        this.team_item_title = team_item_title;
    }

    public String getHigh_score() {
        return high_score;
    }

    public void setHigh_score(String high_score) {
        this.high_score = high_score;
    }

    public String getLow_socre() {
        return low_socre;
    }

    public void setLow_socre(String low_socre) {
        this.low_socre = low_socre;
    }

    public String getTotal_fours() {
        return total_fours;
    }

    public void setTotal_fours(String total_fours) {
        this.total_fours = total_fours;
    }

    public String getTotal_sixes() {
        return total_sixes;
    }

    public void setTotal_sixes(String total_sixes) {
        this.total_sixes = total_sixes;
    }

    public String getTotal_most_sixes_inng() {
        return total_most_sixes_inng;
    }

    public void setTotal_most_sixes_inng(String total_most_sixes_inng) {
        this.total_most_sixes_inng = total_most_sixes_inng;
    }

    public String getTeam_most_four_inng() {
        return team_most_four_inng;
    }

    public void setTeam_most_four_inng(String team_most_four_inng) {
        this.team_most_four_inng = team_most_four_inng;
    }

    public String getTeam_most_wickets_inng() {
        return team_most_wickets_inng;
    }

    public void setTeam_most_wickets_inng(String team_most_wickets_inng) {
        this.team_most_wickets_inng = team_most_wickets_inng;
    }

    public String getTeam_total_wickets() {
        return team_total_wickets;
    }

    public void setTeam_total_wickets(String team_total_wickets) {
        this.team_total_wickets = team_total_wickets;
    }

    public String getTeam_item_title() {
        return team_item_title;
    }

    public void setTeam_item_title(String team_item_title) {
        this.team_item_title = team_item_title;
    }
}
