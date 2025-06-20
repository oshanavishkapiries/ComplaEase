package edu.cms.dao;

import edu.cms.dto.UserDTO;
import edu.cms.repository.UserRepository;
import edu.cms.util.CrudUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserRepository {

    private final CrudUtil crudUtil;

    public UserDAOImpl(DataSource dataSource) {
        this.crudUtil = new CrudUtil(dataSource);
    }

    @Override
    public Optional<UserDTO> save(UserDTO userDTO) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        return crudUtil.executeInsert(sql,
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole())
                .map(id -> {
                    userDTO.setId(id);
                    return userDTO;
                });
    }

    @Override
    public Optional<UserDTO> findById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return crudUtil.executeQuery(sql, this::mapResultSetToUserDTO, id);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return crudUtil.executeQuery(sql, this::mapResultSetToUserDTO, username);
    }

    @Override
    public Optional<UserDTO> findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        return crudUtil.executeQuery(sql, this::mapResultSetToUserDTO, username, password);
    }

    @Override
    public List<UserDTO> findAll() {
        String sql = "SELECT * FROM users ORDER BY id";
        return crudUtil.executeQueryList(sql, this::mapResultSetToUserDTO);
    }

    @Override
    public boolean update(UserDTO userDTO) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
        int affectedRows = crudUtil.executeUpdate(sql,
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getId());
        return affectedRows > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int affectedRows = crudUtil.executeUpdate(sql, id);
        return affectedRows > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        return crudUtil.executeQuery(sql, rs -> {
            try {
                return rs.getLong(1) > 0;
            } catch (SQLException e) {
                System.err.println("Error checking username existence: " + e.getMessage());
                return false;
            }
        }, username).orElse(false);
    }

    private UserDTO mapResultSetToUserDTO(ResultSet rs) {
        try {
            return UserDTO.builder()
                    .id(rs.getInt("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .role(rs.getString("role"))
                    .build();
        } catch (SQLException e) {
            System.err.println("Error mapping ResultSet to UserDTO: " + e.getMessage());
            return null;
        }
    }
}