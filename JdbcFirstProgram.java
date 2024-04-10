import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcFirstProgram {
   public JdbcFirstProgram() {
   }

   public static void init() {
      try {
         Connection var0 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skumari", "skumari", "skumari");

         try {
            System.out.println("Connected to postgres database");
            Statement var1 = var0.createStatement();

            try {
               var1.executeUpdate("drop table if exists ValueTable\n");
               var1.executeUpdate("CREATE TABLE ValueTable (\n    ID SERIAL PRIMARY KEY,\n    Category VARCHAR(50),\n    Value VARCHAR(100)\n);\n");
               var1.executeUpdate("INSERT INTO ValueTable (Category, Value) VALUES\n('Category1', 'Value1'),\n('Category1', 'Value2'),\n('Category1', 'Value3'),\n('Category2', 'Value4'),\n('Category2', 'Value5'),\n('Category2', 'Value6'),\n('Category3', 'Value7'),\n('Category3', 'Value8'),\n('Category3', 'Value9'),\n('Category4', 'DummyValue1'),\n('Category4', 'DummyValue2'),\n('Category4', 'DummyValue3'),\n('CategoryAdmin','VeryImp');\n");
            } catch (Throwable var8) {
               if (var1 != null) {
                  try {
                     var1.close();
                  } catch (Throwable var6) {
                     var8.addSuppressed(var6);
                  }
               }

               throw var8;
            }

            if (var1 != null) {
               var1.close();
            }

            System.out.println("done with making dummy table");
            System.out.println("Table contains category with its value");
            System.out.println("Enter the category by which you want the results to be filtered by");
            System.out.println("Available category:");

            for(int var11 = 1; var11 <= 4; ++var11) {
               System.out.println("Category" + var11);
            }

            Scanner var12 = new Scanner(System.in);

            try {
               String var2 = var12.nextLine();
               usingPreparedStatement(var0, var2);
            } catch (Throwable var7) {
               try {
                  var12.close();
               } catch (Throwable var5) {
                  var7.addSuppressed(var5);
               }

               throw var7;
            }

            var12.close();
         } catch (Throwable var9) {
            if (var0 != null) {
               try {
                  var0.close();
               } catch (Throwable var4) {
                  var9.addSuppressed(var4);
               }
            }

            throw var9;
         }

         if (var0 != null) {
            var0.close();
         }
      } catch (SQLException var10) {
         System.out.println(var10);
      }

   }

   public static void usingStatement(Connection var0, String var1) throws SQLException {
      Statement var2 = var0.createStatement();
      String var3 = "SELECT * FROM ValueTable WHERE Category = '" + var1 + "'";
      System.out.println(var3);
      ResultSet var4 = var2.executeQuery(var3);

      try {
         while(var4.next()) {
            PrintStream var10000 = System.out;
            String var10001 = var4.getString(1);
            var10000.println(var10001 + " " + var4.getString(2));
         }
      } catch (Throwable var8) {
         if (var4 != null) {
            try {
               var4.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (var4 != null) {
         var4.close();
      }

   }

   public static void usingPreparedStatement(Connection var0, String var1) throws SQLException {
      PreparedStatement var2 = var0.prepareStatement("select * from ValueTable where Category = ?");
      var2.setString(1, var1);
      ResultSet var3 = var2.executeQuery();

      try {
         while(var3.next()) {
            PrintStream var10000 = System.out;
            String var10001 = var3.getString(1);
            var10000.println(var10001 + " " + var3.getString(2));
         }
      } catch (Throwable var7) {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }
         }

         throw var7;
      }

      if (var3 != null) {
         var3.close();
      }

   }

   public static void main(String[] var0) {
      try {
         Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException var2) {
         System.out.println("PostgreSQL JDBC Driver not found.");
         var2.printStackTrace();
         return;
      }

      init();
   }
}
