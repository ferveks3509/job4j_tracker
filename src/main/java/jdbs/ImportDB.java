package jdbs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImportDB {
    private Properties cfg;
    private String dump;

    public ImportDB(Properties cfg, String dump) {
        this.cfg = cfg;
        this.dump = dump;
    }

    public List<User> load() throws Exception {
        List<User> users = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new FileReader(dump))) {
            for (String line = rd.readLine(); line != null; line = rd.readLine()) {
                String [] userData = line.split(" ");
                users.add(new User(userData[0], userData[1]));
            }
        }
        return users;
    }

    public void save(List<User> users) throws Exception {
        Class.forName(cfg.getProperty("jdbc.driver"));
        try (Connection cnt = DriverManager.getConnection(
                cfg.getProperty("jdbc.url"),
                cfg.getProperty("jdbc.username"),
                cfg.getProperty("jdbc.password")
        )) {
            for (User user : users) {
                try (PreparedStatement ps = cnt.prepareStatement("insert into users(name, email) values (?, ?)")) {
                    ps.setString(1, user.name);
                    ps.setString(2, user.email);
                    ps.execute();
                }
            }
        }
    }

    private static class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }


    public static void main(String[] args) throws Exception {
        Properties cfg = new Properties();
        try (FileInputStream in = new FileInputStream("./src/main/java/jdbs/app.properties")) {
            cfg.load(in);
        }
        ImportDB db = new ImportDB(cfg, "./src/main/java/jdbs/dump.txt");
        db.save(db.load());
    }
}

