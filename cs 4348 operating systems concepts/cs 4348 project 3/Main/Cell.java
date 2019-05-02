package Main;

public class Cell {

    String data;

    public Cell()
    {
        data = "--";
    }

    public void setCell(String data)
    {
        this.data = data;
    }

    public void setCell(boolean marked)
    {
        if (marked)
        {
            data = "X ";
        }
        else
        {
            data = "--";
        }
    }

    public String getAbsoluteData()
    {
        return data.replaceAll("\\s+","");
    }

    public String toString()
    {
        return data;
    }
}
