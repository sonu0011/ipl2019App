package sonu.finds.ipl2019.Model;

public class DrawerFullTableModel {
    private String team_nme,match_played,match_won,match_lost,match_tied,no_results,
    points,net_run_rate,position,player_name,wckts,dots,fours,sixes,catches,stump,
    fair_play_avg,team_logo,awards_count;
    // points table

    public DrawerFullTableModel(String position,String team_nme, String match_played, String match_won, String match_lost, String match_tied, String no_results, String points, String net_run_rate) {
        this.position =position;
        this.team_nme = team_nme;
        this.match_played = match_played;
        this.match_won = match_won;
        this.match_lost = match_lost;
        this.match_tied = match_tied;
        this.no_results = no_results;
        this.points = points;
        this.net_run_rate = net_run_rate;
    }
    //valuable player
    public DrawerFullTableModel(String position,String player_name,String team_nme,String points,
                                String match_played,String wckts,String dots,String fours,String sixes,
                                String catches,String stump){
        this.position =position;
        this.player_name =player_name;
        this.team_nme =team_nme;
        this.points =points;
        this.match_played =match_played;
        this.wckts =wckts;
        this.dots = dots;
        this.fours =fours;
        this.sixes =sixes;
        this.catches  =catches;
        this.stump =stump;

    }
    //fair play award
    public DrawerFullTableModel(String position,String team_nme,String match_played,
                                String points,String fair_play_avg){
        this.position =position;
        this.team_nme =team_nme;
        this.match_played =match_played;
        this.points =points;
        this.fair_play_avg =fair_play_avg;
    }
    // man of the matches award
    public  DrawerFullTableModel (String position,String player_name,String team_logo,
                                  String match_played,String awards_count,int i){
        this.position = position;
        this.player_name =player_name;
        this.team_logo =team_logo;
        this.match_played =match_played;
        this.awards_count =awards_count;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getWckts() {
        return wckts;
    }

    public void setWckts(String wckts) {
        this.wckts = wckts;
    }

    public String getDots() {
        return dots;
    }

    public void setDots(String dots) {
        this.dots = dots;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getCatches() {
        return catches;
    }

    public void setCatches(String catches) {
        this.catches = catches;
    }

    public String getStump() {
        return stump;
    }

    public void setStump(String stump) {
        this.stump = stump;
    }

    public String getFair_play_avg() {
        return fair_play_avg;
    }

    public void setFair_play_avg(String fair_play_avg) {
        this.fair_play_avg = fair_play_avg;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    public String getAwards_count() {
        return awards_count;
    }

    public void setAwards_count(String awards_count) {
        this.awards_count = awards_count;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam_nme() {
        return team_nme;
    }

    public void setTeam_nme(String team_nme) {
        this.team_nme = team_nme;
    }

    public String getMatch_played() {
        return match_played;
    }

    public void setMatch_played(String match_played) {
        this.match_played = match_played;
    }

    public String getMatch_won() {
        return match_won;
    }

    public void setMatch_won(String match_won) {
        this.match_won = match_won;
    }

    public String getMatch_lost() {
        return match_lost;
    }

    public void setMatch_lost(String match_lost) {
        this.match_lost = match_lost;
    }

    public String getMatch_tied() {
        return match_tied;
    }

    public void setMatch_tied(String match_tied) {
        this.match_tied = match_tied;
    }

    public String getNo_results() {
        return no_results;
    }

    public void setNo_results(String no_results) {
        this.no_results = no_results;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getNet_run_rate() {
        return net_run_rate;
    }

    public void setNet_run_rate(String net_run_rate) {
        this.net_run_rate = net_run_rate;
    }
}
