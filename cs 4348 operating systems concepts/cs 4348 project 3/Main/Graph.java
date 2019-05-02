package Main;

import java.util.List;

public class Graph {

    Cell[][] cells;
    String title = "";

    public Graph(int rows, int columns, String[] titles)
    {
        cells = new Cell[rows][columns];

        initCells();
        writeNumbers();
        writeTitles(titles);
    }

    //this starts from 1 instead of 0
    /*
    public void setCells(int x, int y, boolean filled)
    {
        cells[x][y].setCell(filled);
    }
    */

    public void writeGraph(List<Job> sortedJobs)
    {
        int counter = 1;
        for (Job job : sortedJobs)
        {
            int row = getRow(job.getTitle());
            for (int j = 0; j < job.getDuration(); j++)
            {
                cells[row][counter].setCell(true);
                counter++;
            }
        }
    }

    public void writeTitle(String title)
    {
        this.title = title;
    }

    public String toString()
    {
        String graph = title + "\n";

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++)
            {
                graph += cells[i][j].toString();
            }
            graph += "\n";
        }

        return graph;
    }

    private int getRow(String title)
    {
        for (int i = 0; i < cells.length; i++)
        {
            if (cells[i][0].getAbsoluteData().equals(title))
            {
                return i;
            }
        }
        System.out.println(title);
        return -1;
    }

    private void writeTitles(String[] titles)
    {
        for (int i = 1; i < cells.length; i++)
        {
            cells[i][0].setCell(titles[i - 1] + " ");
        }
    }

    private void writeNumbers()
    {
        for (int i = 1; i < cells[0].length; i++)
        {
            if (i < 10)
            {
                cells[0][i].setCell(Integer.toString(i) + " ");
            }
            else
            {
                cells[0][i].setCell(Integer.toString(i));
            }
        }
    }

    private void initCells()
    {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++)
            {
                cells[i][j] = new Cell();
            }
        }
    }
}
