// Wrapper object to hold JSON information for use with Jackson

public class AttackInformation {

    private String attacker;
    private String[] superEffective;
    private String[] notVeryEffective;
    private String[] notEffective;

    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public String[] getSuperEffective() {
        return superEffective;
    }

    public void setSuperEffective(String[] superEffective) {
        this.superEffective = superEffective;
    }

    public String[] getNotVeryEffective() {
        return notVeryEffective;
    }

    public void setNotVeryEffective(String[] notVeryEffective) {
        this.notVeryEffective = notVeryEffective;
    }

    public String[] getNotEffective() {
        return notEffective;
    }

    public void setNotEffective(String[] notEffective) {
        this.notEffective = notEffective;
    }
}
