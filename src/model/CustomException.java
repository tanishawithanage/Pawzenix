package model;

import java.sql.SQLException;

/**
 * An error with a message that is safe to show to the user.
 * The controllers throw it for wrong input, and the DAO classes throw it for database problems.
 */
public class CustomException extends Exception
{
    public CustomException(String message)
    {
        super(message);
    }

    // Used by the DAO classes: turns a database error into a message the user can understand.
    public CustomException(SQLException ex)
    {
        super(makeMessage(ex));
    }

    private static String makeMessage(SQLException ex)
    {
        int code = ex.getErrorCode();
        String state = ex.getSQLState();

        if (code == 1062)
        {
            return "This record already exists (service name or linked appointment).";
        }
        else if (code == 1451)
        {
            return "This record is used by another record and cannot be deleted.";
        }
        else if (code == 1452)
        {
            return "A selected record no longer exists. Refresh and select it again.";
        }
        else if (state != null && state.startsWith("08"))
        {
            // state 08... means the connection to MySQL failed
            return "Cannot connect to MySQL. Check XAMPP and DBConnection.\n" + ex.getMessage();
        }
        else
        {
            return ex.getMessage();
        }
    }
}
