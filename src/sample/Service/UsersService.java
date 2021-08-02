package sample.Service;

import sample.Entity.User;
import java.sql.*;
import java.util.ArrayList;

public class UsersService {
    private Connection con;

    public UsersService(Connection con) {
        this.con = con;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                String login = rs.getString("login");
                String password = rs.getString("password");

                users.add(new User(userId, login, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting users list!", e);
        }
        return users;
    }

    public User getUserById(int idUser) {
        User user = new User();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");

                return new User(idUser, login, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user by ID!", e);
        }
        return user;
    }

    public User getUserByLogin(String login) {
        User user = new User();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT login, password, id FROM users WHERE login = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String password = rs.getString(2);
                int id = rs.getInt(3);

                user = new User(id, login, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user by login!", e);
        }
        return user;
    }

    public void addUser(User user) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (id, login, password) VALUES (?, ?, ?)");
            ps.setInt(1, user.getId());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user!", e);
        }
    }

    public void updateUser(User newUser, User oldUser) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET login = ?, password = ? WHERE id = ?");
            ps.setString(1, newUser.getLogin());
            ps.setString(2, newUser.getPassword());
            ps.setInt(3, oldUser.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user!", e);
        }
    }

    public void deleteUser(User user) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setDouble(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user!", e);
        }
    }
}
