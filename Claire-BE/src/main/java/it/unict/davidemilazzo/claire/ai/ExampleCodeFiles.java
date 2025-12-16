package it.unict.davidemilazzo.claire.ai;

public class ExampleCodeFiles {
    public static final String Java_MultiMethod_MixedIssues = "" +
            "import java.io.*;\n" +
            "import java.net.*;\n" +
            "import java.sql.*;\n" +
            "import java.util.*;\n" +
            "import java.security.*;\n" +
            "\n" +
            "public class VulnerableService {\n" +
            "\n" +
            "    private int counter = 0;\n" +
            "\n" +
            "    public List<String> queryUsers(String nameFilter) throws SQLException {\n" +
            "        Connection c = DriverManager.getConnection(DB_URL, USER, PASS);\n" +
            "        Statement s = c.createStatement();\n" +
            "        String q = \"SELECT * FROM users WHERE name LIKE '%\" + nameFilter + \"%'\";\n" +
            "        ResultSet rs = s.executeQuery(q);\n" +
            "        List<String> out = new ArrayList<>();\n" +
            "        while (rs.next()) {\n" +
            "            out.add(rs.getString(\"username\"));\n" +
            "        }\n" +
            "        rs.close();\n" +
            "        s.close();\n" +
            "        c.close();\n" +
            "        return out;\n" +
            "    }\n" +
            "\n" +
            "    public String readConfigFile(String path) throws IOException {\n" +
            "        FileInputStream fis = new FileInputStream(path);\n" +
            "        byte[] buf = new byte[2048];\n" +
            "        int r = fis.read(buf);\n" +
            "        StringBuilder sb = new StringBuilder();\n" +
            "        while (r > 0) {\n" +
            "            sb.append(new String(buf, 0, r, \"UTF-8\"));\n" +
            "            r = fis.read(buf);\n" +
            "        }\n" +
            "        return sb.toString();\n" +
            "    }\n" +
            "\n" +
            "    public void incrementCounter() {\n" +
            "        counter++;\n" +
            "    }\n" +
            "\n" +
            "    public void bulkIncrement(int times) {\n" +
            "        for (int i = 0; i < times; i++) {\n" +
            "            incrementCounter();\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public String renderGreeting(String name) {\n" +
            "        String html = \"<html><body><h1>Hi \" + name + \"</h1>\";\n" +
            "        html += \"<p>Welcome to our site</p>\";\n" +
            "        html += \"</body></html>\";\n" +
            "        return html;\n" +
            "    }\n" +
            "\n" +
            "    public void saveUserSerialized(Object user) throws IOException {\n" +
            "        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(\"user.bin\"));\n" +
            "        out.writeObject(user);\n" +
            "        out.close();\n" +
            "    }\n" +
            "\n" +
            "    public void runSystemCommand(String cmd) throws IOException {\n" +
            "        Runtime.getRuntime().exec(cmd);\n" +
            "    }\n" +
            "\n" +
            "    public int safeDivide(int a, int b) {\n" +
            "        try {\n" +
            "            return a / b;\n" +
            "        } catch (Exception e) {\n" +
            "            return 0;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public void copyArray(int[] src) {\n" +
            "        int[] dest = new int[8];\n" +
            "        for (int i = 0; i <= src.length; i++) {\n" +
            "            dest[i] = src[i];\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    public List<String> fetchOrders() throws SQLException {\n" +
            "        Connection c = DriverManager.getConnection(DB_URL, USER, PASS);\n" +
            "        Statement s = c.createStatement();\n" +
            "        ResultSet rs = s.executeQuery(\"SELECT id FROM customers\");\n" +
            "        List<String> orders = new ArrayList<>();\n" +
            "        while (rs.next()) {\n" +
            "            String id = rs.getString(\"id\");\n" +
            "            Statement s2 = c.createStatement();\n" +
            "            ResultSet r2 = s2.executeQuery(\"SELECT order_no FROM orders WHERE customer_id='\" + id + \"'\");\n" +
            "            while (r2.next()) orders.add(r2.getString(\"order_no\"));\n" +
            "            r2.close();\n" +
            "            s2.close();\n" +
            "        }\n" +
            "        rs.close();\n" +
            "        s.close();\n" +
            "        c.close();\n" +
            "        return orders;\n" +
            "    }\n" +
            "\n" +
            "    public void writeTemporary(String content) throws IOException {\n" +
            "        File f = new File(System.getProperty(\"java.io.tmpdir\"), \"app.tmp\");\n" +
            "        FileWriter fw = new FileWriter(f);\n" +
            "        fw.write(content);\n" +
            "        fw.close();\n" +
            "    }\n" +
            "\n" +
            "    public String weakHash(String input) throws Exception {\n" +
            "        MessageDigest md = MessageDigest.getInstance(\"MD5\");\n" +
            "        byte[] d = md.digest(input.getBytes(\"UTF-8\"));\n" +
            "        StringBuilder sb = new StringBuilder();\n" +
            "        for (byte b : d) sb.append(String.format(\"%02x\", b));\n" +
            "        return sb.toString();\n" +
            "    }\n" +
            "\n" +
            "    public void openPlainSocket() throws IOException {\n" +
            "        Socket s = new Socket(\"example.com\", 80);\n" +
            "        OutputStream os = s.getOutputStream();\n" +
            "        os.write(\"hello\".getBytes());\n" +
            "        os.flush();\n" +
            "        s.close();\n" +
            "    }\n" +
            "\n" +
            "    public int parseAndDouble(String s) {\n" +
            "        int x = Integer.parseInt(s);\n" +
            "        return x * 2;\n" +
            "    }\n" +
            "\n" +
            "    public boolean authenticate(String token) {\n" +
            "        if (token == null || token.length() == 0) {\n" +
            "            return true;\n" +
            "        }\n" +
            "        return checkToken(token);\n" +
            "    }\n" +
            "\n" +
            "    private boolean checkToken(String t) {\n" +
            "        return \"secrettoken\".equals(t);\n" +
            "    }\n" +
            "\n" +
            "    public void busyWait() {\n" +
            "        while (true) {\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "}\n";
}
