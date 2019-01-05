package sonu.finds.ipl2019.Model;

public class TabularModel {
    int position,inning;
    String name,matches_played,runs_scored,hs,avg,fours,sixes,fifty,hund;

    public TabularModel(int position, String name, String matches_played,int inning, String runs_scored, String hs, String avg, String fifty, String hund, String fours, String sixes) {
        this.position = position;
        this.name = name;
        this.matches_played = matches_played;
        this.runs_scored = runs_scored;
        this.hs = hs;
        this.avg = avg;
        this.fours = fours;
        this.sixes = sixes;
        this.fifty = fifty;
        this.hund = hund;
        this.inning =inning;
    }

    public int getPosition() {
        return position;
    }

    public int getInning() {
        return inning;
    }

    public void setInning(int inning) {
        this.inning = inning;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatches_played() {
        return matches_played;
    }

    public void setMatches_played(String matches_played) {
        this.matches_played = matches_played;
    }

    public String getRuns_scored() {
        return runs_scored;
    }

    public void setRuns_scored(String runs_scored) {
        this.runs_scored = runs_scored;
    }

    public String getHs() {
        return hs;
    }

    public void setHs(String hs) {
        this.hs = hs;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
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

    public String getFifty() {
        return fifty;
    }

    public void setFifty(String fifty) {
        this.fifty = fifty;
    }

    public String getHund() {
        return hund;
    }

    public void setHund(String hund) {
        this.hund = hund;
    }
}
