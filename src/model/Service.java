package model;

/**
 * One service the centre offers (a row of the services table).
 */
public class Service
{
    private int id;
    private String name;
    private String category;
    private int duration;   // minutes

    public Service()
    {
    }

    public Service(int id, String name, String category, int duration)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.duration = duration;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }
}
