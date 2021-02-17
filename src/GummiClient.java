import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

import static java.lang.System.exit;

public class GummiClient {

    public static void main(String[] args) {

        // Input Validation
        if (args.length <= 0 || args.length > 2) {
            System.out.println("Usage: GummiClient type1 [type2]");
            exit(-1);
        }

        // Check if supplied type[s] are valid.
        for (String arg : args) {
            if (!Type.contains(arg.toUpperCase())) {
                System.out.printf("\"%s\" is not a valid type.", arg);
                exit(-1);
            }
        }

        List<Gummi> gummiList = generateGummiList("resources/gummis.json");

        Map<Gummi, Integer> gummiIQMap = new HashMap<>();

        for (Gummi gummi : gummiList) {
            int totalGummiIQScore = 0;
            for (String typeString : args) {
                Type type = Type.valueOf(typeString.toUpperCase());
                totalGummiIQScore += gummiIQScore(gummi, type);
            }
            gummiIQMap.put(gummi, totalGummiIQScore);
        }

        Map<Gummi, Integer> sortedDescending = new LinkedHashMap<>();

        gummiIQMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedDescending.put(x.getKey(), x.getValue()));

        System.out.println();
        System.out.println("IQ Points Per Gummi");
        System.out.println("-------------------");
        for (Map.Entry<Gummi, Integer> entry : sortedDescending.entrySet()) {
            System.out.printf("%s Gummi: %d\n", entry.getKey().getName(), entry.getValue());
        }
    }

    private static int gummiIQScore(Gummi gummi, Type type) {
        if (gummi.getType() == type)
            return 7;

        Effectiveness gummiAttack = TypeChart.getMatchupEffectiveness(gummi.getType(), type);

        switch (gummiAttack) {
            case SUPER_EFFECTIVE:
                return 4;

            case NOT_VERY_EFFECTIVE:
                return 2;

            case NOT_EFFECTIVE:
                return 1;

            case EFFECTIVE:
            default:
                return 3;
        }
    }

    private static List<Gummi> generateGummiList(String gummiFilePath) {
        // Gummi List
        List<Gummi> gummiList = null;

        // Extract JSON attack information from provided JSON file using Jackson.
        try {
            File gummiFile = new File(gummiFilePath);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

            gummiList = mapper.readValue(gummiFile, new TypeReference<>(){});

        } catch (Exception e) {
            System.out.println("Error parsing gummi information");
            e.printStackTrace();
            exit(-1);
        }

        return gummiList;
    }
}
