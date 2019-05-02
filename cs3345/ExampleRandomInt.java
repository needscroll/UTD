

import java.util.Random;

public class ExampleRandomInt
{
    public static final int COUNT = 100;

    public static void main(String[] args)
    {
        /* According to the JavaDoc, the no-args constructor of Random "sets the seed of the random number generator to
         * a value very likely to be distinct from any other invocation of this constructor."
         */
        Random random = new Random();

        // OPTION 1:  use the nextInt() method
        for (int i = 0; i < COUNT; ++i) {
            int num = random.nextInt();
            System.out.println(num);
        }

        // OPTION 2:  use the ints(long) method
        random.ints(COUNT)
              .forEach( num -> System.out.println(num) );
    }

}
