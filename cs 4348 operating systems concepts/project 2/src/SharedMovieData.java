import java.util.ArrayList;

class SharedMovieData
{
    public static ArrayList<Movie> moviesFinalData = new ArrayList<Movie>();
    public static ArrayList<Movie> moviesActualData = new ArrayList<Movie>();

    public static void initMovie(String[] data)
    {
        ArrayList<Movie> result = new ArrayList<Movie>();
        for(String s: data)
        {
            String[] split = s.split("\t");
            if (split.length > 1 && split[0] != null && split[1] != null)
            {
                moviesFinalData.add(new Movie(split[0], Integer.parseInt(split[1])));
                moviesActualData.add(new Movie(split[0], 0));
            }
        }
    }

    public static Movie[] getFinalData()
    {
        return moviesFinalData.toArray(new Movie[1]);
    }

    public static Movie[] getActualData()
    {
        return moviesActualData.toArray(new Movie[1]);
    }

    public static void incrementMovie(String name)
    {
        for (int i = 0; i < moviesActualData.size(); i++)
        {
            if (moviesActualData.get(i).matches(name))
            {
                moviesActualData.get(i).incrementSeats();
            }
        }
    }

    public static void decrementMovie(String name)
    {
        for (int i = 0; i < moviesActualData.size(); i++)
        {
            if (moviesActualData.get(i).matches(name))
            {
                moviesActualData.get(i).decrementSeats();
            }
        }
    }

    public static boolean isFull(String name)
    {
        for (int i = 0; i < moviesActualData.size(); i++)
        {
            if (moviesActualData.get(i).matches(name))
            {
                return moviesActualData.get(i).getSeats() >= moviesFinalData.get(i).getSeats();
            }
        }
        return true;
    }

    public static Movie getRandomMovie()
    {
        int random = (int) (Math.random() * moviesFinalData.size());
        return moviesFinalData.get(random);
    }
}