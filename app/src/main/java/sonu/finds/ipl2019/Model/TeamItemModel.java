package sonu.finds.ipl2019.Model;

public class TeamItemModel {
    String high_score,low_socre,total_fours,total_sixes,total_most_sixes_inng,team_most_four_inng,
    team_most_wickets_inng,team_total_wickets,team_item_title,button_value;
    String player_name,player_image,match_no,team1_image,team_2_image,date_time,venu;
    String result_declare,team1_name,team2_name,highlights_link,score_team1_score,score_team2_score,over_team1_over,over_team2_over;

    public TeamItemModel(String high_score, String low_socre, String total_fours, String total_sixes, String total_most_sixes_inng, String team_most_four_inng, String team_most_wickets_inng, String team_total_wickets, String team_item_title,String button_value) {
        this.high_score = high_score;
        this.low_socre = low_socre;
        this.total_fours = total_fours;
        this.total_sixes = total_sixes;
        this.total_most_sixes_inng = total_most_sixes_inng;
        this.team_most_four_inng = team_most_four_inng;
        this.team_most_wickets_inng = team_most_wickets_inng;
        this.team_total_wickets = team_total_wickets;
        this.team_item_title = team_item_title;
        this.button_value = button_value;
    }

    public TeamItemModel(String player_name, String player_image) {

        this.player_image = player_image;
        this.player_name  = player_name;
    }

    public TeamItemModel(String match_no, String team1_image, String team2_image, String date_time, String venu) {

        this.match_no = match_no;
        this.team1_image = team1_image;
        this.team_2_image = team2_image;
        this.date_time = date_time;
        this.venu = venu;
    }

    public TeamItemModel(String match_no, String results_declare, String team1_name, String team2_name, String highlights_link, String date_time, String score_team1_score, String over_team1_over, String score_team2_score, String over_team2_over, int i) {
    this.match_no = match_no;
    this.result_declare = results_declare;
    this.team1_name = team1_name;
    this.team2_name = team2_name;
    this.highlights_link = highlights_link;
    this.date_time = date_time;
    this.score_team1_score = score_team1_score;
    this.score_team2_score = score_team2_score;
    this.over_team1_over = over_team1_over;
    this.over_team2_over = over_team2_over;

    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_image() {
        return player_image;
    }

    public void setPlayer_image(String player_image) {
        this.player_image = player_image;
    }

    public String getMatch_no() {
        return match_no;
    }

    public void setMatch_no(String match_no) {
        this.match_no = match_no;
    }

    public String getTeam1_image() {
        return team1_image;
    }

    public void setTeam1_image(String team1_image) {
        this.team1_image = team1_image;
    }

    public String getTeam_2_image() {
        return team_2_image;
    }

    public void setTeam_2_image(String team_2_image) {
        this.team_2_image = team_2_image;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getVenu() {
        return venu;
    }

    public void setVenu(String venu) {
        this.venu = venu;
    }

    public String getResult_declare() {
        return result_declare;
    }

    public void setResult_declare(String result_declare) {
        this.result_declare = result_declare;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getHighlights_link() {
        return highlights_link;
    }

    public void setHighlights_link(String highlights_link) {
        this.highlights_link = highlights_link;
    }

    public String getScore_team1_score() {
        return score_team1_score;
    }

    public void setScore_team1_score(String score_team1_score) {
        this.score_team1_score = score_team1_score;
    }

    public String getScore_team2_score() {
        return score_team2_score;
    }

    public void setScore_team2_score(String score_team2_score) {
        this.score_team2_score = score_team2_score;
    }

    public String getOver_team1_over() {
        return over_team1_over;
    }

    public void setOver_team1_over(String over_team1_over) {
        this.over_team1_over = over_team1_over;
    }

    public String getOver_team2_over() {
        return over_team2_over;
    }

    public void setOver_team2_over(String over_team2_over) {
        this.over_team2_over = over_team2_over;
    }

    public String getButton_value() {
        return button_value;
    }

    public void setButton_value(String button_value) {
        this.button_value = button_value;
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
