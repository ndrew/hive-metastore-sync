package test.java;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wandisco.hivesync.main.HiveSync;

public class DatabasesTest extends AbstractTest {
  @BeforeClass
  public void setup() throws Exception {
    AbstractSuite.fullCleanup("BEFORE TEST CLEANUP");

    Statement s1 = con1.createStatement();
    s1.execute("create database db1");
    s1.execute("create table db1.table1 (col1 int)");
    s1.execute("create database db2");
    s1.execute("create table db2.table2 (col1 int)");
    s1.execute("create database db3");
    s1.execute("create table db3.table3 (col1 int)");
    s1.close();

    Statement s2 = con2.createStatement();
    s2.execute("create database db1");
    s2.execute("create table db1.table1 (col1 int, col2 int)");
    s2.execute("create database db3");
    s2.close();
  }

  @Test
  public void f() throws Exception {
    List<String> dbs = Arrays.asList("db*");
    HiveSync hs = new HiveSync(url1, user1, pass1, url2, user2, pass2, dbs);
    hs.execute();
    Statement s2 = con2.createStatement();
    checkResult(s2, "show databases", new String[] {"db1", "db2", "db3", "default"});
    checkResult(s2, "show tables in db1", new String[] {"table1"});
    checkResult(s2, "describe db1.table1", new String[] {"col1"});
    checkResult(s2, "show tables in db2", new String[] {"table2"});
    checkResult(s2, "describe db2.table2", new String[] {"col1"});
    checkResult(s2, "show tables in db3", new String[] {"table3"});
    checkResult(s2, "describe db3.table3", new String[] {"col1"});
    s2.close();
  }

  @AfterClass
  public void cleanup() throws SQLException {
    AbstractSuite.fullCleanup("AFTER TEST CLEANUP");
  }
}
