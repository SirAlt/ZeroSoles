package com.example.zerosoles.data.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zerosoles.data.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    private final Connection connection;
    private boolean includeInactive;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @NonNull
    public List<User> getAll() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT `id`, `username`, `fullname`, `email`, `gender`, `role`, `can_ship`, `created_at`, `updated_at`, `active`\n" +
                        "FROM User");
        if (!includeInactive) sb.append("\nWHERE `active` = true");
        sb.append(';');
        String query = sb.toString();

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(userFromResultSet(rs));
        }
        return users;
    }

    @Nullable
    public User getById(long id) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT `id`, `username`, `password`, `fullname`, `email`, `gender`, `role`, `can_ship`, `created_at`, `updated_at`, `active`\n" +
                        "FROM User\n" +
                        "WHERE `id` = ?");
        if (!includeInactive) sb.append("\nAND `active` = true");
        sb.append(';');
        String query = sb.toString();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return userFromResultSet(rs);
        } else return null;
    }

    @NonNull
    public List<User> getByName(String name) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT `id`, `username`, `password`, `fullname`, `email`, `gender`, `role`, `can_ship`, `created_at`, `updated_at`, `active`\n" +
                        "FROM User\n" +
                        "WHERE BINARY `username` = ?");
        if (!includeInactive) sb.append("\nAND `active` = true");
        sb.append(';');
        String query = sb.toString();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(userFromResultSet(rs));
        }
        return users;
    }

    public List<User> getByEmail(String email) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "SELECT `id`, `username`, `password`, `fullname`, `email`, `gender`, `role`, `can_ship`, `created_at`, `updated_at`, `active`\n" +
                        "FROM User\n" +
                        "WHERE BINARY `email` = ?");
        if (!includeInactive) sb.append("\nAND `active` = true");
        sb.append(';');
        String query = sb.toString();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(userFromResultSet(rs));
        }
        return users;
    }

    public int add(User... users) throws SQLException {
        String query =
                "INSERT INTO `User` (`username`, `password`, `fullname`, `email`, `gender`, `role`, `can_ship`)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);\n";

        int count = 0;
        for (User user : users) {
            PreparedStatement ps = connection.prepareStatement(query);
            setParameters(user, ps);
            count += ps.executeUpdate();
        }
        return count;
    }

    public int update(User... users) throws SQLException {
        String query =
                "UPDATE `shoeStore`.`User`\n" +
                        "SET\n" +
                        "`username` = ?,\n" +
                        "`password` = ?,\n" +
                        "`fullname` = ?,\n" +
                        "`email` = ?,\n" +
                        "`gender` = ?,\n" +
                        "`role` = ?,\n" +
                        "`can_ship` = ?\n" +
                        "WHERE `id` = ?;";

        int count = 0;
        for (User user : users) {
            PreparedStatement ps = connection.prepareStatement(query);
            setParameters(user, ps);
            ps.setLong(8, user.getId());
            count += ps.executeUpdate();
        }
        return count;
    }

    public int delete(User... users) throws SQLException {
        String query =
                "UPDATE `shoeStore`.`User`\n" +
                        "SET\n" +
                        "`active` = false\n" +
                        "WHERE `id` = ?;";

        int count = 0;
        for (User user : users) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, user.getId());
            count += ps.executeUpdate();
        }
        return count;
    }

    private static User userFromResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String username = rs.getString("username");
        String passwordHash = rs.getString("password");
        String fullname = rs.getString("fullname");
        String email = rs.getString("email");
        String gender = rs.getString("gender");
        String role = rs.getString("role");
        boolean canShip = rs.getBoolean("can_ship");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        boolean active = rs.getBoolean("active");

        return new User.Builder()
                .withId(id)
                .withUsername(username)
                .withPasswordHash(passwordHash)
                .withFullname(fullname)
                .withEmail(email)
                .withGender(User.Gender.valueOf(gender))
                .withRole(User.Role.valueOf(role))
                .withCanShip(canShip)
                .withCreatedAt(createdAt)
                .withUpdatedAt(updatedAt)
                .withActive(active)
                .build();
    }

    private static void setParameters(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPasswordHash());
        ps.setString(3, user.getFullname());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getGender().toString());
        ps.setString(6, user.getRole().toString());
        ps.setBoolean(7, user.isCanShip());
    }

    public boolean isIncludeInactive() {
        return includeInactive;
    }

    public void setIncludeInactive(boolean includeInactive) {
        this.includeInactive = includeInactive;
    }
}
