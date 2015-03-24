package com.application.yuchia.myapplication;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD)
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");  //驅動程式-第四類

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");     //驅動程式-第一類: JDBC-ODBC橋接器
            //至 設定-控制台-系統管理工具-資料來源(ODBC)
            //-> 選擇系統資料來源名稱->新增->選Oracle...->完成
            //-> [Data Source Name(資料來源名稱)\輸入:dsn1] [TNS Service Name\選擇:ORCL2] [User ID\輸入:scott] [OK] -> 確定
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
            Connection con =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
            //Connection con =  DriverManager.getConnection("jdbc:odbc:dsn1", "scott", "tiger");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * from emp2");

            while (rs.next()) {
                String str1 = rs.getString(1);
                String str2 = rs.getString(2);

                System.out.print(" EMPNO= " + str1);
                System.out.print(" ENAME= " + str2);
                System.out.print("\n");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
