package it.unict.davidemilazzo.claire.ai;

public final class ExampleCodeSnippets {
    public static final String Java_SQLInjection = "" +
            "public class UserRepository {\n" +
            "    public List<String> findUsers(String filter) throws Exception {\n" +
            "        java.sql.Connection c = DriverManager.getConnection(DB_URL, USER, PASS);\n" +
            "        java.sql.Statement s = c.createStatement();\n" +
            "        String q = \"SELECT * FROM users WHERE username LIKE '%\" + filter + \"%'\";\n" +
            "        java.sql.ResultSet rs = s.executeQuery(q);\n" +
            "        List<String> results = new ArrayList<>();\n" +
            "        while(rs.next()) {\n" +
            "            results.add(rs.getString(\"username\"));\n" +
            "        }\n" +
            "        rs.close(); s.close(); c.close();\n" +
            "        return results;\n" +
            "    }\n" +
            "}";

    public static final String Java_ResourceLeak = "" +
            "import java.io.*;\n" +
            "public class FileReaderUtil {\n" +
            "    public String read(String path) throws IOException {\n" +
            "        FileInputStream fis = new FileInputStream(path);\n" +
            "        byte[] buf = new byte[4096];\n" +
            "        int r = fis.read(buf);\n" +
            "        StringBuilder sb = new StringBuilder();\n" +
            "        while (r > 0) {\n" +
            "            sb.append(new String(buf, 0, r, \"UTF-8\"));\n" +
            "            r = fis.read(buf);\n" +
            "        }\n" +
            "        return sb.toString();\n" +
            "    }\n" +
            "}";

    public static final String Java_RaceCondition = "" +
            "public class Counter {\n" +
            "    private int count = 0;\n" +
            "    public void increment() {\n" +
            "        count++;\n" +
            "    }\n" +
            "    public void bulkIncrement(int times) {\n" +
            "        for (int i = 0; i < times; i++) increment();\n    }\n" +
            "    public int get() { return count; }\n" +
            "}";

    public static final String Java_PerformanceQuadratic = "" +
            "import java.util.*;\n" +
            "public class NumberPairs {\n" +
            "    public List<int[]> findPairs(int[] arr) {\n" +
            "        List<int[]> pairs = new ArrayList<>();\n" +
            "        for(int i=0;i<arr.length;i++) {\n" +
            "            for(int j=i+1;j<arr.length;j++) {\n" +
            "                if(arr[i] + arr[j] == 0) {\n" +
            "                    pairs.add(new int[]{arr[i], arr[j]});\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        return pairs;\n" +
            "    }\n" +
            "}";

    public static final String Java_InefficientAlgo = "" +
            "import java.util.*;\n" +
            "public class DuplicateFinder {\n" +
            "    public List<Integer> findDuplicates(List<Integer> numbers) {\n" +
            "        List<Integer> duplicates = new ArrayList<>();\n" +
            "        for (int i = 0; i < numbers.size(); i++) {\n" +
            "            for (int j = i + 1; j < numbers.size(); j++) {\n" +
            "                if (numbers.get(i).equals(numbers.get(j))) {\n" +
            "                    duplicates.add(numbers.get(i));\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        return duplicates;\n" +
            "    }\n" +
            "}";

    public static final String Java_BufferOverflowLike = "" +
            "public class ArrayCopyUtil {\n" +
            "    public void copyArray(int[] src) {\n" +
            "        int[] dest = new int[5];\n" +
            "        for (int i = 0; i <= src.length; i++) {\n" +
            "            dest[i] = src[i];\n" +
            "        }\n" +
            "    }\n" +
            "}";

    public static final String Java_UnencryptedCommunication = "" +
            "import java.net.*;\n" +
            "import java.io.*;\n" +
            "public class PlainSender {\n" +
            "    public void sendData(String data) throws IOException {\n" +
            "        Socket socket = new Socket(\"example.com\", 80);\n" +
            "        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);\n" +
            "        out.println(data);\n" +
            "        out.flush();\n" +
            "        out.close();\n" +
            "        socket.close();\n" +
            "    }\n" +
            "}";

    public static final String Java_SwallowedException = "" +
            "public class Processor {\n" +
            "    public void processData() {\n" +
            "        try {\n" +
            "            int a = 5 / 0;\n" +
            "        } catch (Exception e) {\n" +
            "        }\n" +
            "    }\n" +
            "}";

    public static final String Java_InsecureSerialization = "" +
            "import java.io.*;\n" +
            "public class User implements Serializable {\n" +
            "    private String name;\n" +
            "    public void saveUser() throws IOException {\n" +
            "        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(\"user.dat\"));\n" +
            "        out.writeObject(this);\n" +
            "        out.close();\n" +
            "    }\n" +
            "}";

    public static final String Java_NPlusOne = "" +
            "import java.sql.*;\n" +
            "import java.util.*;\n" +
            "public class OrderService {\n" +
            "    public List<String> getCustomerOrders() throws SQLException {\n" +
            "        Connection c = DriverManager.getConnection(DB_URL, USER, PASS);\n" +
            "        Statement s = c.createStatement();\n" +
            "        ResultSet rs = s.executeQuery(\"SELECT id FROM customers\");\n" +
            "        List<String> out = new ArrayList<>();\n" +
            "        while (rs.next()) {\n" +
            "            String id = rs.getString(\"id\");\n" +
            "            Statement s2 = c.createStatement();\n" +
            "            ResultSet r2 = s2.executeQuery(\"SELECT * FROM orders WHERE customer_id='\" + id + \"'\");\n" +
            "            while (r2.next()) out.add(r2.getString(\"order_no\"));\n" +
            "            r2.close();\n" +
            "            s2.close();\n" +
            "        }\n" +
            "        rs.close(); s.close(); c.close();\n" +
            "        return out;\n" +
            "    }\n" +
            "}";

    public static final String Java_ComplexSplitCode = "" +
            "private List<String> splitCode(String code, int lineInBlock, int overlap) {\n" +
            "    List<String> lines = IOUtils.readLines(new StringReader(code));\n" +
            "    if (lines.size() <= lineInBlock) {\n" +
            "        return List.of(code);\n" +
            "    }\n" +
            "    List<String> snippetsToAnalyze = new ArrayList<>();\n" +
            "    StringBuilder codeSnippet = new StringBuilder();\n" +
            "    for (int i=0, j=0; i<lines.size(); i++, j++) {\n" +
            "        codeSnippet.append(lines.get(i)).append(\"\\n\");\n" +
            "        if (j >= lineInBlock-1) {\n" +
            "            snippetsToAnalyze.add(codeSnippet.toString());\n" +
            "            codeSnippet = new StringBuilder();\n" +
            "            j = 0;\n" +
            "            i -= overlap;\n" +
            "        }\n" +
            "    }\n" +
            "    if (!codeSnippet.isEmpty()) {\n" +
            "        snippetsToAnalyze.add(codeSnippet.toString());\n" +
            "    }\n" +
            "    return snippetsToAnalyze;\n" +
            "}";

    public static final String Python_CommandInjection = "" +
            "import os, subprocess\n" +
            "def execute_user_command(cmd):\n" +
            "    for i in range(5):\n" +
            "        if cmd.startswith('rm'):\n" +
            "            print('Skipping risky command')\n" +
            "        else:\n" +
            "            subprocess.call(cmd, shell=True)\n" +
            "    return True\n";

    public static final String Python_UnboundedAlloc = "" +
            "def build_massive_list(n):\n" +
            "    result = []\n" +
            "    for i in range(n):\n" +
            "        result.append('x' * (10**6))\n" +
            "    return len(result)\n";

    public static final String Python_ExecEval = "" +
            "def execute(user_code):\n" +
            "    exec(user_code)\n";

    public static final String Python_RegexDoS = "" +
            "import re\n" +
            "def vulnerable(s):\n" +
            "    p = re.compile(r'^(a+)+$')\n" +
            "    return bool(p.match(s))\n";

    public static final String JS_XSS = "" +
            "function render(req, res) {\n" +
            "    const name = req.query.name;\n" +
            "    let html = '<html><body><h1>Welcome ' + name + '</h1>';\n" +
            "    html += '<p>Have a nice day!</p>';\n" +
            "    html += '</body></html>';\n" +
            "    res.send(html);\n" +
            "}\n";

    public static final String JS_EvalInjection = "" +
            "function execUser(code) {\n" +
            "    return eval(code);\n" +
            "}\n";

    public static final String JS_InsecureFileWrite = "" +
            "const fs = require('fs');\n" +
            "function save(name, data){\n" +
            "    fs.writeFileSync('/tmp/' + name, data);\n" +
            "}\n";

    public static final String CSharp_HardcodedCreds = "" +
            "using System.Data.SqlClient;\n" +
            "public class DbService {\n" +
            "    private string connString = \"Server=localhost;User Id=admin;Password=SuperSecret\";\n" +
            "    public void QueryUsers() {\n" +
            "        using(var c = new SqlConnection(connString)) {\n" +
            "            c.Open();\n" +
            "            SqlCommand cmd = new SqlCommand(\"SELECT * FROM Users\", c);\n" +
            "            SqlDataReader reader = cmd.ExecuteReader();\n" +
            "            while(reader.Read()) {\n" +
            "                Console.WriteLine(reader[\"Username\"]);\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}\n";

    public static final String CSharp_ImproperExceptionHandling = "" +
            "public class Calc {\n" +
            "    public int Div(int a, int b) {\n" +
            "        try {\n" +
            "            return a / b;\n" +
            "        } catch (Exception) {\n" +
            "            return 0;\n" +
            "        }\n" +
            "    }\n" +
            "}\n";

    public static final String SQL_DynamicDDL = "" +
            "CREATE PROCEDURE drop_table(@t nvarchar(100))\n" +
            "AS\n" +
            "BEGIN\n" +
            "    DECLARE @sql nvarchar(max) = 'DROP TABLE ' + @t;\n" +
            "    EXEC(@sql);\n" +
            "END\n";

    public static final String SQL_NPlusOnePseudo = "" +
            "SELECT id FROM customers;\n" +
            "-- for each id retrieved:\n" +
            "SELECT * FROM orders WHERE customer_id = <id>;\n";

    public static final String Bash_PathTraversal = "" +
            "#!/bin/bash\n" +
            "read -p 'Enter filename: ' FILE\n" +
            "if [ -f \"/var/data/$FILE\" ]; then\n" +
            "    cat /var/data/$FILE\n" +
            "else\n" +
            "    echo 'File not found'\n" +
            "fi\n";

    public static final String Bash_InsecurePermissions = "" +
            "#!/bin/bash\n" +
            "touch /tmp/secret\n" +
            "chmod 777 /tmp/secret\n" +
            "echo \"$1\" > /tmp/secret\n";

    public static final String C_C_BufferOverflow = "" +
            "#include <string.h>\n" +
            "void copy(char *src){\n" +
            "    char buf[16];\n" +
            "    strcpy(buf, src);\n" +
            "}\n";

    public static final String C_ManualCopyOverflow = "" +
            "#include <stdio.h>\n" +
            "#include <string.h>\n" +
            "void copyInput(char *input) {\n" +
            "    char buffer[32];\n" +
            "    for(int i=0;i<strlen(input);i++) {\n" +
            "        buffer[i] = input[i];\n" +
            "    }\n" +
            "    printf(\"Copied: %s\\n\", buffer);\n" +
            "}\n";

    public static final String Pseudo_AuthBypass = "" +
            "IF token == '' THEN\n" +
            "    grant_access(user)\n" +
            "ELSE\n" +
            "    validate(user, token)\n" +
            "END IF\n";

    public static final String Pseudo_IncompleteFragment = "" +
            "FUNCTION process(items)\n" +
            "    FOR i IN 1..LENGTH(items)\n" +
            "        IF items[i] == NULL THEN\n" +
            "            \n" +
            "        END IF\n" +
            "    \n" +
            "    \n";

    public static final String XML_Config_Insecure = "" +
            "<configuration>\n" +
            "    <appSettings>\n" +
            "        <add key=\"ApiKey\" value=\"abcd-1234-SECRET\" />\n" +
            "    </appSettings>\n" +
            "</configuration>\n";

    public static final String JSON_HardcodedSecrets = "" +
            "{\n" +
            "  \"database\": {\n" +
            "    \"user\": \"admin\",\n" +
            "    \"password\": \"TopSecret\"\n" +
            "  }\n" +
            "}\n";

    public static final String Java_Spring_TransactionMisuse = "" +
            "import org.springframework.stereotype.Service;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import java.util.*;\n" +
            "@Service\n" +
            "public class OrderService {\n" +
            "    @Autowired\n" +
            "    private OrderRepository repo;\n" +
            "    public void processOrders(List<Order> orders) {\n" +
            "        for(Order o : orders) {\n" +
            "            repo.save(o);\n" +
            "        }\n" +
            "    }\n" +
            "}";

    public static final String Java_Spring_UnsynchronizedBean = "" +
            "import org.springframework.stereotype.Component;\n" +
            "@Component\n" +
            "public class CounterBean {\n" +
            "    private int count = 0;\n" +
            "    public void increment() { count++; }\n" +
            "    public int get() { return count; }\n" +
            "}";

    public static final String Python_Django_SQLInjection = "" +
            "from django.db import connection\n" +
            "def get_users(filter):\n" +
            "    cursor = connection.cursor()\n" +
            "    cursor.execute(\"SELECT * FROM auth_user WHERE username LIKE '%\" + filter + \"%'\")\n" +
            "    return cursor.fetchall()\n";

    public static final String Python_Flask_UnescapedRender = "" +
            "from flask import Flask, request, render_template_string\n" +
            "app = Flask(__name__)\n" +
            "@app.route('/welcome')\n" +
            "def welcome():\n" +
            "    name = request.args.get('name')\n" +
            "    return render_template_string('<h1>Hello ' + name + '</h1>')\n";

    public static final String JS_Express_InsecureSession = "" +
            "const express = require('express');\n" +
            "const app = express();\n" +
            "const session = require('express-session');\n" +
            "app.use(session({ secret: '1234', resave: true, saveUninitialized: true }));\n" +
            "app.get('/', (req, res) => { res.send('Hello'); });\n";

    public static final String JS_Express_UnsanitizedInput = "" +
            "const express = require('express');\n" +
            "const app = express();\n" +
            "app.get('/echo', (req, res) => {\n" +
            "    res.send('User input: ' + req.query.msg);\n" +
            "});\n";

    public static final String CSharp_ASPNet_MissingValidation = "" +
            "using Microsoft.AspNetCore.Mvc;\n" +
            "public class UserController : Controller {\n" +
            "    [HttpPost]\n" +
            "    public IActionResult Create(string username) {\n" +
            "        System.IO.File.WriteAllText($\"/tmp/{username}.txt\", username);\n" +
            "        return Ok();\n" +
            "    }\n" +
            "}";

    public static final String CSharp_ASPNet_SessionMisuse = "" +
            "public class SessionController : Controller {\n" +
            "    public void SetSession(string user) {\n" +
            "        HttpContext.Session.SetString(\"user\", user);\n" +
            "    }\n" +
            "    public string GetSession() {\n" +
            "        return HttpContext.Session.GetString(\"user\");\n" +
            "    }\n" +
            "}";

    public static final String Java_Hibernate_UnsafeQuery = "" +
            "import org.hibernate.Session;\n" +
            "public class UserDAO {\n" +
            "    public List<User> getUsers(String filter) {\n" +
            "        Session session = HibernateUtil.getSession();\n" +
            "        return session.createQuery(\"FROM User u WHERE u.name LIKE '%\" + filter + \"%'\").list();\n" +
            "    }\n" +
            "}";

    public static final String Java_Hibernate_LazyInitExceptionRisk = "" +
            "import org.hibernate.Session;\n" +
            "import org.hibernate.query.Query;\n" +
            "public class ProductDAO {\n" +
            "    public List<Product> fetchProducts() {\n" +
            "        Session session = HibernateUtil.getSession();\n" +
            "        Query<Product> q = session.createQuery(\"FROM Product\", Product.class);\n" +
            "        List<Product> products = q.list();\n" +
            "        session.close();\n" +
            "        for(Product p : products) {\n" +
            "            p.getCategory().getName();\n" +
            "        }\n" +
            "        return products;\n" +
            "    }\n" +
            "}";
}
