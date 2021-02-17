// Wrapper object to hold JSON information for use with Jackson

public class AttackInformation {

    private String attacker;
    private Type[] superEffective;
    private Type[] notVeryEffective;
    private Type[] notEffective;


    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public Type[] getSuperEffective() {
        return superEffective;
    }

    public void setSuperEffective(Type[] superEffective) {
        this.superEffective = superEffective;
    }

    public Type[] getNotVeryEffective() {
        return notVeryEffective;
    }

    public void setNotVeryEffective(Type[] notVeryEffective) {
        this.notVeryEffective = notVeryEffective;
    }

    public Type[] getNotEffective() {
        return notEffective;
    }

    public void setNotEffective(Type[] notEffective) {
        this.notEffective = notEffective;
    }
}
