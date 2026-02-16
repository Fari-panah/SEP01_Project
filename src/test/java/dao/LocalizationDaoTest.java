package dao;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import datasource.ConnectionDB;
import java.sql.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.*;

class LocalizationDaoTest {

    @Test
    void testGetLocalizedStrings_NoRealDB() throws Exception {

        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(false);

        try (MockedStatic<ConnectionDB> dbMock =
                     mockStatic(ConnectionDB.class)) {

            dbMock.when(ConnectionDB::obtenerConexion)
                    .thenReturn(mockConn);

            LocalizationDao dao = new LocalizationDao();
            Map<String, String> result =
                    dao.getLocalizedStrings(Locale.ENGLISH);

            assert result.isEmpty();
        }
    }
}

