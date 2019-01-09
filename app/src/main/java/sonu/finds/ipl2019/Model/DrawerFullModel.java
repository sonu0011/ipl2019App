package sonu.finds.ipl2019.Model;

public class DrawerFullModel {
    private String match_no,team1image,team2image,date,venu,result_declare
            ,team1_Name,team2_Name,highlits,score_team1_score,over_team1_over,score_team2_score,over_team2_over;


    //schdeule
    public DrawerFullModel(String match_no, String team1image, String team2image, String date, String venu) {
        this.match_no = match_no;
        this.team1image = team1image;
        this.team2image = team2image;
        this.date = date;
        this.venu = venu;
    }
    //highlits
    public DrawerFullModel(String match_no,String result_declare,String team1_Name,String team2_Name,
                           String highlits,String date,
                           String score_team1_score,String over_team1_over,
                           String score_team2_score,String over_team2_over){
        this.match_no =match_no;
        this.result_declare =result_declare;
        this.team1_Name =team1_Name;
        this.team2_Name = team2_Name;
        this.highlits =highlits;
        this.date =date;
        this.score_team1_score =score_team1_score;
        this.over_team1_over =over_team1_over;
        this.score_team2_score =score_team2_score;
        this.over_team2_over =over_team2_over;

    }

    public String getResult_declare() {
        return result_declare;
    }

    public void setResult_declare(String result_declare) {
        this.result_declare = result_declare;
    }

    public String getTeam1_Name() {
        return team1_Name;
    }

    public void setTeam1_Name(String team1_Name) {
        this.team1_Name = team1_Name;
    }

    public String getTeam2_Name() {
        return team2_Name;
    }

    public void setTeam2_Name(String team2_Name) {
        this.team2_Name = team2_Name;
    }

    public String getHighlits() {
        return highlits;
    }

    public void setHighlits(String highlits) {
        this.highlits = highlits;
    }

    public String getScore_team1_score() {
        return score_team1_score;
    }

    public void setScore_team1_score(String score_team1_score) {
        this.score_team1_score = score_team1_score;
    }

    public String getOver_team1_over() {
        return over_team1_over;
    }

    public void setOver_team1_over(String over_team1_over) {
        this.over_team1_over = over_team1_over;
    }

    public String getScore_team2_score() {
        return score_team2_score;
    }

    public void setScore_team2_score(String score_team2_score) {
        this.score_team2_score = score_team2_score;
    }

    public String getOver_team2_over() {
        return over_team2_over;
    }

    public void setOver_team2_over(String over_team2_over) {
        this.over_team2_over = over_team2_over;
    }

    public String getMatch_no() {
        return match_no;
    }

    public void setMatch_no(String match_no) {
        this.match_no = match_no;
    }

    public String getTeam1image() {
        return team1image;
    }

    public void setTeam1image(String team1image) {
        this.team1image = team1image;
    }

    public String getTeam2image() {
        return team2image;
    }

    public void setTeam2image(String team2image) {
        this.team2image = team2image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenu() {
        return venu;
    }

    public void setVenu(String venu) {
        this.venu = venu;
    }
}
