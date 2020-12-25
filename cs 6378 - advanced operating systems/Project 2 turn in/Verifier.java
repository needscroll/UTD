import java.util.ArrayList;

public class Verifier {

    public static String filename = "CriticalSectionLog.txt";

    public static void main(String[] args)
    {
        UserIO io = new UserIO();
        ArrayList<String> timestamps = io.getFileContents(filename);
        ArrayList<Integer> vectorSums = new ArrayList<>();

        for (String s : timestamps)
        {
            String vector = s.split("\\s+")[1];
            String[] splitVector = vector.split(":");
            int total = 0;
            for (String s1 : splitVector)
            {
                total += Integer.valueOf(s1);
            }
            vectorSums.add(total);
        }

        boolean fail = false;
        for (int i = 0; i < vectorSums.size() - 1; i++)
        {
            if (vectorSums.get(i) > vectorSums.get(i + 1))
            {
                System.out.println("ERROR: CRITICAL SECTION OVERLAP");
                fail = true;
            }
        }

        if (!fail)
        {
            System.out.println("SUCSSESS: No Critical Section Overlap");
        }
    }
}
