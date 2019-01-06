package sonu.finds.ipl2019.Model;

public class BowlingLeaderModel {
    private int positon, balls_bowled, over_bowled,innings;
    private String image, name, feat, team_name, wicket_taken, runs_given,matches_played,four_wick,five_wick;
    private String strike_rate, average, economy;

    //strike rate average
    public BowlingLeaderModel(int positon, String player_name, String team_name, int balls_bowled,
                              String wicket_taken, String strike_rate) {
        this.positon = positon;
        this.name = player_name;
        this.team_name = team_name;
        this.balls_bowled = balls_bowled;
        this.wicket_taken = wicket_taken;
        this.strike_rate = strike_rate;

    }
    //economy rate

    public BowlingLeaderModel( String player_name,int positon, String team_name, int over_bowled,
                              String runs_given, String economy) {
        this.positon = positon;
        this.name = player_name;
        this.team_name = team_name;
        this.over_bowled  = over_bowled;
        this.runs_given =runs_given;
        this.economy = economy;


    }
    //average
    public BowlingLeaderModel( String player_name,int positon, String team_name, String wicket_taken,
                               String runs_given, String average) {
        this.positon = positon;
        this.name = player_name;
        this.team_name = team_name;
        this.wicket_taken  = wicket_taken;
        this.runs_given =runs_given;
        this.average = average;


    }
    //purple cap
    public BowlingLeaderModel(int positon,String player_name, String team_name,String matches_played
                              ,int innings,int over_bowled,
                               String runs_given,String wicket_taken,String economy
                                ,String average,String strike_rate,String four_wick,String five_wick) {
        this.positon = positon;
        this.name = player_name;
        this.team_name = team_name;
        this.over_bowled  = over_bowled;
        this.runs_given =runs_given;
        this.economy = economy;
        this.matches_played =matches_played;
        this.average =average;
        this.innings =innings;
        this.wicket_taken =wicket_taken;
        this.strike_rate =strike_rate;
        this.five_wick =four_wick;
        this.five_wick =five_wick;


    }

    public int getInnings() {
        return innings;
    }

    public void setInnings(int innings) {
        this.innings = innings;
    }

    public String getMatches_played() {
        return matches_played;
    }

    public void setMatches_played(String matches_played) {
        this.matches_played = matches_played;
    }

    public String getFour_wick() {
        return four_wick;
    }

    public void setFour_wick(String four_wick) {
        this.four_wick = four_wick;
    }

    public String getFive_wick() {
        return five_wick;
    }

    public void setFive_wick(String five_wick) {
        this.five_wick = five_wick;
    }

    //Best Bowling Innings
    public BowlingLeaderModel(int positon, String player_name, String team_name, String matches_played,
                              int innings, int  over_bowled ,String runs_given,
                              String wicket_taken, String average,String economy, String strike_rate
                                ) {
        this.positon = positon;
        this.name = player_name;
        this.team_name = team_name;
        this.wicket_taken = wicket_taken;
        this.strike_rate = strike_rate;
        this.innings =innings;
        this.over_bowled =over_bowled;
        this.runs_given =runs_given;
        this.matches_played =matches_played;
        this.economy =economy;
        this.average =average;


    }



    public int getOver_bowled() {
        return over_bowled;
    }

    public void setOver_bowled(int over_bowled) {
        this.over_bowled = over_bowled;
    }

    public String getRuns_given() {
        return runs_given;
    }

    public void setRuns_given(String runs_given) {
        this.runs_given = runs_given;
    }


    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }

    public int getBalls_bowled() {
        return balls_bowled;
    }

    public void setBalls_bowled(int balls_bowled) {
        this.balls_bowled = balls_bowled;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getWicket_taken() {
        return wicket_taken;
    }

    public void setWicket_taken(String wicket_taken) {
        this.wicket_taken = wicket_taken;
    }

    public String getStrike_rate() {
        return strike_rate;
    }

    public void setStrike_rate(String strike_rate) {
        this.strike_rate = strike_rate;
    }

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeat() {
        return feat;
    }

    public void setFeat(String feat) {
        this.feat = feat;
    }

    public BowlingLeaderModel(int positon, String image, String name, String feat) {

        this.positon = positon;
        this.image = image;
        this.name = name;
        this.feat = feat;
    }
}
