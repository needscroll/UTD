package Main;

public class Job implements Comparable<Job>{

    String title;
    int start;
    int duration;

    public Job(String input)
    {
        String cleaned = clean(input);
        title = Character.toString(cleaned.charAt(0));
        start = Character.getNumericValue(cleaned.charAt(1));
        duration = Character.getNumericValue(cleaned.charAt(2));
        //this may need to be changed later
    }

    public Job(String title, int start, int duration)
    {
        this.title = title;
        this.start = start;
        this.duration = duration;
    }

    public String getTitle()
    {
        return title;
    }

    public int getStart() {
        return start;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void decrementDuration()
    {
        duration--;
    }

    public String toString()
    {
        return title+ ":" + start + ":" + duration;
    }

    private String clean(String input)
    {
        return input.replaceAll("\\s+","");
    }

    @Override
    public int compareTo(Job o) {
        return this.start - o.start;
    }
}
