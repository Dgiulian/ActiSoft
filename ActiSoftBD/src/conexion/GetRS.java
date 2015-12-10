/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Manuel
 */
public class GetRS {

    public String getRSString(ResultSet rs, int index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getString(index);
            } else {
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public int getRSInt(ResultSet rs, int index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getInt(index);
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public Double getRSDouble(ResultSet rs, int index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getDouble(index);
            } else {
                return 0.0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
    }

    public Blob getRsBlob(ResultSet rs, int index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getBlob(index);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public Date getRsDate(ResultSet rs, int index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getDate(index);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getRSString(ResultSet rs, String index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getString(index);
            } else {
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public int getRSInt(ResultSet rs, String index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getInt(index);
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public Double getRSDouble(ResultSet rs, String index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getDouble(index);
            } else {
                return 0.0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
    }

    public Blob getRsBlob(ResultSet rs, String index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getBlob(index);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public Date getRsDate(ResultSet rs, String index) {
        try {
            if (rs.getObject(index) != null) {
                return rs.getDate(index);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetRS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
