package edu.cms.util;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CrudUtil {

    private final DataSource dataSource;

    public CrudUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> Optional<T> executeQuery(String sql, Function<ResultSet, T> mapper, Object... params) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapper.apply(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + sql + " - " + e.getMessage());
        }
        return Optional.empty();
    }

    public <T> List<T> executeQueryList(String sql, Function<ResultSet, T> mapper, Object... params) {
        List<T> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(mapper.apply(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error executing query list: " + sql + " - " + e.getMessage());
        }
        return results;
    }

    public int executeUpdate(String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            return stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error executing update: " + sql + " - " + e.getMessage());
            return 0;
        }
    }

    public Optional<Integer> executeInsert(String sql, Object... params) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(stmt, params);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return Optional.of(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing insert: " + sql + " - " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean executeTransaction(List<SqlOperation> operations) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            for (SqlOperation operation : operations) {
                try (PreparedStatement stmt = conn.prepareStatement(operation.getSql())) {
                    setParameters(stmt, operation.getParams());
                    stmt.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error executing transaction: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] == null) {
                    stmt.setNull(i + 1, Types.NULL);
                } else {
                    stmt.setObject(i + 1, params[i]);
                }
            }
        }
    }

    public static class SqlOperation {
        private final String sql;
        private final Object[] params;

        public SqlOperation(String sql, Object... params) {
            this.sql = sql;
            this.params = params;
        }

        public String getSql() {
            return sql;
        }

        public Object[] getParams() {
            return params;
        }
    }
}