package dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import datasource.ConnectionDB;
import java.sql.*;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.*;

 class LocalizationDaoTest {

    @Test
    void testGetLocalizedStrings_NoRealDB() throws Exception {
        // Mock JDBC objects
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        // Mock the static DB connection method
        Mockito.mockStatic(ConnectionDB.class)
                .when(ConnectionDB::obtenerConexion)
                .thenReturn(mockConn);

        LocalizationDao dao = new LocalizationDao();
        Map<String, String> result = dao.getLocalizedStrings(Locale.ENGLISH);
        assert result.isEmpty();
    }
}

