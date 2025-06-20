package com.cms.util;

import java.sql.*;
import java.util.function.Function;

public class CrudUtils {
    public static <T> T executeQuery(String sql, Function<ResultSet, T> handler, Object... params) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParams(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                return handler.apply(rs);
            }
        }
    }

    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParams(pstmt, params);
            return pstmt.executeUpdate();
        }
    }

    private static void setParams(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }
}