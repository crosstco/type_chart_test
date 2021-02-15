import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public class TypeChart {

    // TypeChart implemented as a HashMap for constant access time.
    private static HashMap<TypeMatchup, Effectiveness> typeChart;

    // Generate TypeChart on first access
    static {
        generateTypeChart("resources/gen2-5_matchups.json");
    }

    // Returns the effectivenes of an attacker on a defender. The primary use of the Type Chart.
    public static Effectiveness getMatchupEffectiveness(Type attacker, Type defender) {
        TypeMatchup matchup = new TypeMatchup(attacker, defender);
        return typeChart.get(matchup);
    }

    // Print every matchup and relative effectiveness for every entry in the chart. Not formatted or organized however.
    public static void printTypeChart() {
        typeChart.entrySet().forEach(entry->{
            System.out.println("Attacker: " + entry.getKey().getAttacker() + " Defender: " + entry.getKey().getDefender() + " " + entry.getValue());
        });
    }

    // Generate a type chart for the given
    private static void generateTypeChart(String matchupFilePath) {

        // Create new HashMap to hold the values
        typeChart = new HashMap<>();

        // Populate the Map with default "EFFECTIVE" values.
        for (Type attacker : Type.values()) {
            for (Type defender : Type.values()) {
                TypeMatchup matchup = new TypeMatchup(attacker, defender);
                typeChart.put(matchup, Effectiveness.EFFECTIVE);
            }
        }

        // Create list of unique matchups
        List <AttackInformation> attackInformationList = null;

        // Extract JSON attack information from provided JSON file using Jackson.
        try {
            File matchupFile = new File(matchupFilePath);
            ObjectMapper mapper = new ObjectMapper();
            attackInformationList = mapper.readValue(matchupFile, new TypeReference<List<AttackInformation>>(){});
        } catch (Exception e) {
            System.out.println("Error parsing matchup information");
            e.printStackTrace();
            exit(-1);
        }

        // Generate special matchups from information list.
        for (AttackInformation attackInformation : attackInformationList) {

            // Extract attack type
            Type attacker = Type.valueOf(attackInformation.getAttacker().toUpperCase());

            TypeMatchup matchup;

            // Populate super effective attacks for attacker type if they exist (i.e. in the case of NORMAL, they do not.)
            if (attackInformation.getSuperEffective() != null) {
                for (String defenderString : attackInformation.getSuperEffective()) {
                    Type defender = Type.valueOf(defenderString.toUpperCase());
                    matchup = new TypeMatchup(attacker, defender);
                    typeChart.put(matchup, Effectiveness.SUPER_EFFECTIVE);
                }
            }

            // Populate not very effective attacks for attacker type if they exist.
            if (attackInformation.getNotVeryEffective() != null) {
                for (String defenderString : attackInformation.getNotVeryEffective()) {
                    Type defender = Type.valueOf(defenderString.toUpperCase());
                    matchup = new TypeMatchup(attacker, defender);
                    typeChart.put(matchup, Effectiveness.NOT_VERY_EFFECTIVE);
                }
            }

            // Populate ineffective attacks for attacker type if they exist.
            if (attackInformation.getNotEffective() != null) {
                for (String defenderString : attackInformation.getNotEffective()) {
                    Type defender = Type.valueOf(defenderString.toUpperCase());
                    matchup = new TypeMatchup(attacker, defender);
                    typeChart.put(matchup, Effectiveness.NOT_EFFECTIVE);
                }
            }
        }
        // END CHART GENERATION
    }

    // Wrapper class for the intersection between attacker and defender
    private static class TypeMatchup {

        private Type attacker;
        private Type defender;

        public TypeMatchup(Type attacker, Type defender) {
            this.attacker = attacker;
            this.defender = defender;
        }

        public Type getAttacker() {
            return attacker;
        }

        public Type getDefender() {
            return defender;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TypeMatchup that = (TypeMatchup) o;
            return attacker == that.attacker && defender == that.defender;
        }

        @Override
        public int hashCode() {
            return Objects.hash(attacker, defender);
        }
    }
}
