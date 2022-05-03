package com.cutti.etiquetas.data;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BuscaRazao {
    Connection con;

    public String retornaRazao(String manifesto, String codCliente) {
        String ip = "192.168.10.236", port = "1433", db = "WMS_SAP_INTEGRATOR", username = "wms", password = "wms";
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databaseName=" + db + ";user=" + username + ";" + "password=" + password + ";";
            con = DriverManager.getConnection(connectURL);

            String query = "select CustomerCode, LoadNumber, CustomerName \n" +
                    "from [order] " +
                    "where CustomerCode = "+ codCliente +" and " +
                    "LoadNumber = "+ manifesto +" and " +
                    "ProcessedAt is not null";
            Statement smt = con.createStatement();
            ResultSet set = smt.executeQuery(query);

            if (set.next()) {
                String razao = set.getString("CustomerName");
                return razao;
            }
            else{
                return "ERRO";
            }

        } catch (Exception e) {
            Log.e("Error :", e.getMessage());
            return "ERRO";
        }
    }

    @SuppressLint("NewApi")
    public boolean verifica(String manifesto, String codCliente) {
        String ip = "192.168.10.236", port = "1433", db = "WMS_SAP_INTEGRATOR", username = "wms", password = "wms";
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databaseName=" + db + ";user=" + username + ";" + "password=" + password + ";";
            con = DriverManager.getConnection(connectURL);

            String query = "select CustomerCode, LoadNumber, CustomerName \n" +
                    "from [order] " +
                    "where CustomerCode = "+ codCliente +" and " +
                    "LoadNumber = "+ manifesto +" and " +
                    "ProcessedAt is not null";
            Statement smt = con.createStatement();
            ResultSet set = smt.executeQuery(query);

            if (set.next()) {
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            Log.e("Error :", e.getMessage());
            return false;
        }
    }

}
